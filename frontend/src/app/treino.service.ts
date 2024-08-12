import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Treino } from './treino.model';

@Injectable({
  providedIn: 'root',
})
export class TreinoService {
  private apiUrl = 'http://localhost:8080/treinos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Treino[]> {
    return this.http.get<Treino[]>(this.apiUrl);
  }

  adicionar(treino: Treino): Observable<void> {
    if (treino.dataHoraCriacao) {
      const data = new Date(treino.dataHoraCriacao);

      // Obter componentes da data
      const ano = data.getFullYear();
      const mes = String(data.getMonth() + 1).padStart(2, '0');
      const dia = String(data.getDate()).padStart(2, '0');
      const hora = String(data.getHours()).padStart(2, '0');
      const minuto = String(data.getMinutes()).padStart(2, '0');
      const segundo = String(data.getSeconds()).padStart(2, '0');

      // Formatar a data no formato local
      treino.dataHoraCriacao = `${ano}-${mes}-${dia}T${hora}:${minuto}:${segundo}`;
    } else {
      treino.dataHoraCriacao = '';
    }

    return this.http.post<void>(`${this.apiUrl}`, treino);
  }


  editar(treino: Treino): Observable<void> {
    if (treino.dataHoraCriacao) {
      const data = new Date(treino.dataHoraCriacao);

      // Obter componentes da data
      const ano = data.getFullYear();
      const mes = String(data.getMonth() + 1).padStart(2, '0');
      const dia = String(data.getDate()).padStart(2, '0');
      const hora = String(data.getHours()).padStart(2, '0');
      const minuto = String(data.getMinutes()).padStart(2, '0');
      const segundo = String(data.getSeconds()).padStart(2, '0');

      // Formatar a data no formato local "yyyy-MM-ddTHH:mm:ss"
      treino.dataHoraCriacao = `${ano}-${mes}-${dia}T${hora}:${minuto}:${segundo}`;
    } else {
      treino.dataHoraCriacao = '';
    }

    return this.http.put<void>(`${this.apiUrl}/${treino.id}`, treino);
  }


  remover(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);  // Use crases aqui
  }


  filtrar(nomeTreino: string, nomeExercicio: string, repeticoes?: number, series?: number, dataHoraCriacao?: string): Observable<Treino[]> {
    let params = new HttpParams();
    if (nomeTreino) params = params.set('nomeTreino', nomeTreino);
    if (nomeExercicio) params = params.set('nomeExercicio', nomeExercicio);
    if (repeticoes) params = params.set('repeticoes', repeticoes.toString());
    if (series) params = params.set('series', series.toString());
    if (dataHoraCriacao) params = params.set('dataHoraCriacao', dataHoraCriacao);

    return this.http.get<Treino[]>(`${this.apiUrl}/filtrar`, { params });
  }


}
