import { HttpClient, httpResource } from '@angular/common/http';
import { Component, TemplateRef, computed, inject, linkedSignal, signal } from '@angular/core';
import { FormField, FormRoot, form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../core/auth.service';
import { DelovniNalog, PostavkaNaloga, RezervniDel, Stanje, Vozilo } from '../../core/models';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazenNalog = { voziloId: '', opis: '' };
const prazenDel = { delId: '', kolicina: 1 };

@Component({
  imports: [FormRoot, FormField, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './nalogi.html',
  styleUrl: './nalogi.scss',
})
export class Nalogi {
  private http = inject(HttpClient);
  private dialog = inject(MatDialog);
  private auth = inject(AuthService);

  protected readonly stanja: Stanje[] = ['ODPRT', 'V_DELU', 'ZAKLJUCEN', 'OBRACUNAN'];
  protected readonly jeVodja = this.auth.jeVodja;
  protected readonly jeOsebje = this.auth.jeOsebje;

  // --- podatki iz strežnika ---
  protected readonly nalogi = httpResource<DelovniNalog[]>(() => '/api/nalogi', { defaultValue: [] });
  protected readonly izbranId = signal<string | null>(null);
  protected readonly izbran = computed(() => this.nalogi.value().find((nalog) => nalog.id === this.izbranId()));
  protected readonly postavke = httpResource<PostavkaNaloga[]>(
    () => (this.izbranId() ? `/api/nalogi/${this.izbranId()}/postavke` : undefined),
    { defaultValue: [] },
  );
  // vozila vidi za izbiro samo vodja, dele samo osebje (stranka za to nima pravic)
  protected readonly vozila = httpResource<Vozilo[]>(() => (this.jeVodja() ? '/api/vozila' : undefined), { defaultValue: [] });
  protected readonly deli = httpResource<RezervniDel[]>(() => (this.jeOsebje() ? '/api/deli' : undefined), { defaultValue: [] });

  // --- obrazec: nov nalog (v oknu) ---
  private modelNalog = signal({ ...prazenNalog });
  protected readonly obrazecNalog = form(this.modelNalog, (pot) => {
    required(pot.voziloId);
    required(pot.opis);
  });

  // --- obrazec: urejanje izbranega naloga (se samodejno napolni ob izbiri) ---
  private modelUrejanje = linkedSignal(() => {
    const nalog = this.izbran();
    return { stanje: nalog?.stanje ?? 'ODPRT', opisDela: nalog?.opisDela ?? '', ureDela: nalog?.ureDela ?? 0 };
  });
  protected readonly obrazecUrejanje = form(this.modelUrejanje, {
    submission: { action: async () => this.shrani() },
  });

  // --- obrazec: dodaj rezervni del na nalog (v oknu) ---
  private modelDel = signal({ ...prazenDel });
  protected readonly obrazecDel = form(this.modelDel, (pot) => {
    required(pot.delId);
    min(pot.kolicina, 1);
  });

  protected odpriNalog(predloga: TemplateRef<unknown>) {
    odpriObrazec(this.dialog, {
      naslov: 'Nov delovni nalog',
      predloga,
      veljaven: () => !this.obrazecNalog().invalid(),
      shrani: () => {
        const { voziloId, opis } = this.modelNalog();
        return this.http.post('/api/nalogi', { opis, vozilo: { id: voziloId } });
      },
    }).subscribe((shranjeno) => {
      this.obrazecNalog().reset({ ...prazenNalog });
      if (shranjeno) this.nalogi.reload();
    });
  }

  protected odpriDel(predloga: TemplateRef<unknown>) {
    odpriObrazec(this.dialog, {
      naslov: 'Dodaj rezervni del na nalog',
      predloga,
      veljaven: () => !this.obrazecDel().invalid(),
      shrani: () => this.http.post(`/api/nalogi/${this.izbranId()}/postavke`, this.modelDel()),
    }).subscribe((shranjeno) => {
      this.obrazecDel().reset({ ...prazenDel });
      if (shranjeno) {
        this.postavke.reload();
        this.deli.reload(); // zaloga se je zmanjšala
      }
    });
  }

  private shrani() {
    this.http.put(`/api/nalogi/${this.izbranId()}`, this.modelUrejanje()).subscribe(() => this.nalogi.reload());
  }

  protected izdajRacun() {
    this.http.post(`/api/nalogi/${this.izbranId()}/racun`, null).subscribe(() => this.nalogi.reload());
  }

  protected izbrisi() {
    this.http.delete(`/api/nalogi/${this.izbranId()}`).subscribe(() => {
      this.izbranId.set(null);
      this.nalogi.reload();
    });
  }
}
