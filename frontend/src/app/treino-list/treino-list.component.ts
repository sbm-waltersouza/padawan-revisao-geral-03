import { Component, OnInit } from '@angular/core';
import { TreinoService } from '../treino.service';
import { Treino } from '../treino.model';

@Component({
  selector: 'app-treino-list',
  templateUrl: './treino-list.component.html',
  styleUrls: ['./treino-list.component.css'],
})
export class TreinoListComponent implements OnInit {
  treinos: Treino[] = [];

  filtro = {
    nomeTreino: '',
    nomeExercicio: '',
    repeticoes: null as number | null,
    series: null as number | null,
    dataHoraCriacao: '' as string,
  };

  novoTreino: Treino = {
    id: 0,
    nomeTreino: '',
    nomeExercicio: '',
    repeticoes: 0,
    series: 0,
    dataHoraCriacao: '',
  };

  treinoParaEditar: Treino | null = null;

  constructor(private treinoService: TreinoService) {}

  ngOnInit(): void {
    this.listarTreinos();
  }

  listarTreinos(): void {
    this.treinoService.listar().subscribe((treinos) => {
      this.treinos = treinos;
    });
  }

  editarTreino(treino: Treino): void {
    this.treinoParaEditar = { ...treino };
  }

  formatDateForInput(date: Date): string {
    const addZero = (n: number) => n < 10 ? '0' + n : n;

    return date.getFullYear() + '-' +
           addZero(date.getMonth() + 1) + '-' +
           addZero(date.getDate()) + 'T' +
           addZero(date.getHours()) + ':' +
           addZero(date.getMinutes());
  }

  adicionarTreino(): void {
    if (this.novoTreino.dataHoraCriacao) {
      const date = new Date(this.novoTreino.dataHoraCriacao);
      this.novoTreino.dataHoraCriacao = this.formatDateForInput(date);
    }
    this.treinoService.adicionar(this.novoTreino).subscribe(() => {
      this.listarTreinos();
      this.novoTreino = {
        id: 0,
        nomeTreino: '',
        nomeExercicio: '',
        repeticoes: 0,
        series: 0,
        dataHoraCriacao: '',
      };
    });
  }

  salvarEdicao(): void {
    if (this.treinoParaEditar && this.treinoParaEditar.dataHoraCriacao) {
      const date = new Date(this.treinoParaEditar.dataHoraCriacao);
      this.treinoParaEditar.dataHoraCriacao = this.formatDateForInput(date);
    }
    if (this.treinoParaEditar) {
      this.treinoService.editar(this.treinoParaEditar).subscribe(() => {
        this.listarTreinos();
        this.treinoParaEditar = null;
      });
    }
  }

  removerTreino(id: number): void {
    this.treinoService.remover(id).subscribe(() => {
      this.listarTreinos();
    });
  }

  cancelarEdicao(): void {
    this.treinoParaEditar = null;
  }

  buscar(): void {
    const formattedDate = this.filtro.dataHoraCriacao
      ? this.formatDateForInput(new Date(this.filtro.dataHoraCriacao))
      : '';
    this.treinoService
      .filtrar(
        this.filtro.nomeTreino,
        this.filtro.nomeExercicio,
        this.filtro.repeticoes ?? undefined,
        this.filtro.series ?? undefined,
        formattedDate
      )
      .subscribe((treinos) => {
        this.treinos = treinos;
      });
  }
}
