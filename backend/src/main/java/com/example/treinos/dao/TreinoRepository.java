package com.example.treinos.dao;

import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.example.treinos.model.Treino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TreinoRepository {

    private final DataSource dataSource;

    public TreinoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Treino> listar() {
        List<Treino> treinos = new ArrayList<>();
        String sql = "SELECT * FROM treinos_exercicios";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Treino treino = mapRowToTreino(resultSet);
                treinos.add(treino);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treinos;
    }

    public Treino buscarPorId(int id) {
        Treino treino = null;
        String sql = "SELECT * FROM treinos_exercicios WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    treino = mapRowToTreino(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treino;
    }

    public void adicionar(Treino treino) {
        String sql = "INSERT INTO treinos_exercicios (nome_treino, nome_exercicio, repeticoes, series, data_hora_criacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, treino.getNomeTreino());
            statement.setString(2, treino.getNomeExercicio());
            statement.setInt(3, treino.getRepeticoes());
            statement.setInt(4, treino.getSeries());
            statement.setObject(5, treino.getDataHoraCriacao());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Treino treino) {
        String sql = "UPDATE treinos_exercicios SET nome_treino = ?, nome_exercicio = ?, repeticoes = ?, series = ?, data_hora_criacao = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, treino.getNomeTreino());
            statement.setString(2, treino.getNomeExercicio());
            statement.setInt(3, treino.getRepeticoes());
            statement.setInt(4, treino.getSeries());
            statement.setObject(5, treino.getDataHoraCriacao());
            statement.setInt(6, treino.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM treinos_exercicios WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Treino mapRowToTreino(ResultSet resultSet) throws SQLException {
        Treino treino = new Treino();
        treino.setId(resultSet.getInt("id"));
        treino.setNomeTreino(resultSet.getString("nome_treino"));
        treino.setNomeExercicio(resultSet.getString("nome_exercicio"));
        treino.setRepeticoes(resultSet.getInt("repeticoes"));
        treino.setSeries(resultSet.getInt("series"));
        treino.setDataHoraCriacao(resultSet.getTimestamp("data_hora_criacao").toLocalDateTime());
        return treino;
    }
    
    public List<Treino> filtrar(String nomeTreino, String nomeExercicio, Integer repeticoes, Integer series, String dataHoraCriacao) {
        List<Treino> treinos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM treinos_exercicios WHERE 1=1");

        if (nomeTreino != null && !nomeTreino.isEmpty()) {
            sql.append(" AND nome_treino LIKE ?");
        }
        if (nomeExercicio != null && !nomeExercicio.isEmpty()) {
            sql.append(" AND nome_exercicio LIKE ?");
        }
        if (repeticoes != null) {
            sql.append(" AND repeticoes = ?");
        }
        if (series != null) {
            sql.append(" AND series = ?");
        }
        if (dataHoraCriacao != null && !dataHoraCriacao.isEmpty()) {
            sql.append(" AND data_hora_criacao = ?");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (nomeTreino != null && !nomeTreino.isEmpty()) {
                statement.setString(paramIndex++, "%" + nomeTreino + "%");
            }
            if (nomeExercicio != null && !nomeExercicio.isEmpty()) {
                statement.setString(paramIndex++, "%" + nomeExercicio + "%");
            }
            if (repeticoes != null) {
                statement.setInt(paramIndex++, repeticoes);
            }
            if (series != null) {
                statement.setInt(paramIndex++, series);
            }
            if (dataHoraCriacao != null && !dataHoraCriacao.isEmpty()) {
                statement.setString(paramIndex, dataHoraCriacao);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Treino treino = mapRowToTreino(resultSet);
                    treinos.add(treino);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return treinos;
    }
}
