import { firstValueFrom, of } from 'rxjs';
import { Component, TemplateRef, inject, signal } from '@angular/core';
import { FormField, form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DeliService } from '../../core/api/deli.service';
import { AuthService } from '../../core/auth.service';
import { RezervniDel } from '../../core/models';
import { PageHeader } from '../../shared/page-header/page-header';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const ZAMIK_DOSTAVE_MS = 5000; // simulirana dobava

const prazno = { sifra: '', naziv: '', cena: 0, zaloga: 0, minZaloga: 0 };

@Component({
  imports: [FormField, MatButtonModule, MatFormFieldModule, MatInputModule, PageHeader],
  templateUrl: './deli.html',
})
export class Deli {
  private deliApi = inject(DeliService);
  private dialog = inject(MatDialog);

  protected readonly jeVodja = inject(AuthService).jeVodja;
  protected readonly deli = this.deliApi.seznam();

  protected readonly vDostavi = signal(new Set<string>());
  protected readonly kolicina = signal({ kolicina: 1 });
  protected readonly narociloObrazec = form(this.kolicina, (pot) => min(pot.kolicina, 1));

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.sifra);
    required(pot.naziv);
    min(pot.cena, 0);
    min(pot.zaloga, 0);
    min(pot.minZaloga, 0);
  });

  protected async dodaj(predloga: TemplateRef<unknown>) {
    const shranjeno = await odpriObrazec(this.dialog, {
      naslov: 'Nov rezervni del',
      predloga,
      veljaven: () => !this.obrazec().invalid(),
      shrani: () => this.deliApi.ustvari(this.model()),
    });
    this.obrazec().reset({ ...prazno });
    if (shranjeno) this.deli.reload();
  }

  protected async izbrisi(del: RezervniDel) {
    await firstValueFrom(this.deliApi.izbrisi(del.id));
    this.deli.reload();
  }

  protected async naroci(del: RezervniDel, predloga: TemplateRef<unknown>) {
    const potrjeno = await odpriObrazec(this.dialog, {
      naslov: `Naroči: ${del.naziv}`,
      predloga,
      veljaven: () => !this.narociloObrazec().invalid(),
      shrani: () => of(null),
    });
    if (!potrjeno) return;

    this.vDostavi.update((s) => new Set(s).add(del.id));
    await new Promise((resolve) => setTimeout(resolve, ZAMIK_DOSTAVE_MS));
    try {
      await firstValueFrom(this.deliApi.naroci(del.id, this.kolicina().kolicina));
      this.deli.reload();
    } finally {
      this.vDostavi.update((s) => {
        const novo = new Set(s);
        novo.delete(del.id);
        return novo;
      });
    }
  }
}
