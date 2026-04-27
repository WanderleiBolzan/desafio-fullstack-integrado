import { Component, OnInit, inject, signal, computed } from '@angular/core';
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
  // Injetando o serviço corretamente
  private service = inject(BeneficioService);

  // Sinais de Estado
  beneficios = signal<any[]>([]);
  loading = signal<boolean>(false);

  // Sinais para os campos do formulário (ligados ao HTML)
  idDeInput = signal<string>('');
  idParaInput = signal<string>('');
  valorInput = signal<string>('');

  // Validação reativa usando Computed
  formularioValido = computed(() => {
    const de = Number(this.idDeInput());
    const para = Number(this.idParaInput());
    const valor = Number(this.valorInput());

    // Regras: IDs preenchidos, valor positivo e IDs diferentes
    return de > 0 && para > 0 && valor > 0 && de !== para;
  });

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.service.listarTodos().subscribe({
      next: (dados: any[]) => this.beneficios.set(dados),
      error: (err: any) => console.error('Erro ao carregar:', err)
    });
  }

  transferir() {
    if (!this.formularioValido()) return;

    this.loading.set(true);

    const idDe = Number(this.idDeInput());
    const idPara = Number(this.idParaInput());
    const valor = Number(this.valorInput());

    this.service.transferir(idDe, idPara, valor).subscribe({
      next: (res: any) => {
        alert(res.message);
        this.carregar(); // Atualiza a lista
        this.limparCampos();
      },
      error: (err: any) => {
        alert('Erro: ' + (err.error?.message || 'Falha na transferência'));
        this.loading.set(false);
      }
    });
  }

  private limparCampos() {
    this.idDeInput.set('');
    this.idParaInput.set('');
    this.valorInput.set('');
    this.loading.set(false);
  }
}
