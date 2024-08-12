package com.example.treinos.service;

import org.springframework.stereotype.Service;
import com.example.treinos.dao.TreinoRepository;
import com.example.treinos.model.Treino;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;

    public TreinoService(TreinoRepository treinoRepository) {
        this.treinoRepository = treinoRepository;
    }

    public List<Treino> listar() {
        return treinoRepository.listar();
    }

    public Treino buscarPorId(int id) {
        return treinoRepository.buscarPorId(id);
    }

    public void adicionar(Treino treino) {
        treinoRepository.adicionar(treino);
    }

    public void atualizar(Treino treino) {
        treinoRepository.atualizar(treino);
    }

    public void excluir(int id) {
        treinoRepository.excluir(id);
    }
    
    public List<Treino> filtrar(String nomeTreino, String nomeExercicio, Integer repeticoes, Integer series, String dataHoraCriacao) {
        return treinoRepository.filtrar(nomeTreino, nomeExercicio, repeticoes, series, dataHoraCriacao);
    }

   
}
