import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { BeneficioService } from './services/beneficio';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class AppComponent implements OnInit {
  private service = inject(BeneficioService);
  beneficios = signal<any[]>([]);

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.service.listarTodos().subscribe(dados => this.beneficios.set(dados));
  }

  transferir(idDe: string, idPara: string, valor: string) {
    this.service.transferir(Number(idDe), Number(idPara), Number(valor)).subscribe({
      next: () => {
        alert('Sucesso!');
        this.carregar(); // Isso faz a lista atualizar na tela
      },
      error: (err) => alert('Erro: ' + err.message)
    });
  }
}
