package com.example.treinos.controller;

import org.springframework.web.bind.annotation.*;

import com.example.treinos.model.Treino;
import com.example.treinos.service.TreinoService;

import java.util.List;

@RestController
@RequestMapping("/treinos")
public class TreinoController {

	private final TreinoService treinoService;

	public TreinoController(TreinoService treinoService) {
		this.treinoService = treinoService;
	}

	@GetMapping
	public List<Treino> listar() {
		return treinoService.listar();
	}

	@GetMapping("/{id}")
	public Treino buscarPorId(@PathVariable int id) {
		return treinoService.buscarPorId(id);
	}

	@PostMapping
	public void adicionar(@RequestBody Treino treino) {
		treinoService.adicionar(treino);
	}

	@PutMapping("/{id}")
	public void atualizar(@RequestBody Treino treino) {
		treinoService.atualizar(treino);
	}

	@DeleteMapping("/{id}")
	public void excluir(@PathVariable int id) {
		treinoService.excluir(id);
	}
	
	@GetMapping("/filtrar")
	public List<Treino> filtrar(@RequestParam(required = false, defaultValue = "") String nomeTreino,
	                            @RequestParam(required = false, defaultValue = "") String nomeExercicio,
	                            @RequestParam(required = false) Integer repeticoes,
	                            @RequestParam(required = false) Integer series,
	                            @RequestParam(required = false, defaultValue = "") String dataHoraCriacao) {
	    return treinoService.filtrar(nomeTreino, nomeExercicio, repeticoes, series, dataHoraCriacao);
	}



}
