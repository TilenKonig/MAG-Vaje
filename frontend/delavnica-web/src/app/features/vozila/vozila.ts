import { Component, TemplateRef, computed, inject, signal } from '@angular/core';
import { FormField, form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from '../../core/auth.service';
import { UporabnikiService } from '../../core/api/uporabniki.service';
import { VozilaService } from '../../core/api/vozila.service';
import { Vozilo } from '../../core/models';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { lastnikId: '', znamka: '', model: '', letnik: 2020, registrska: '' };

@Component({
  imports: [FormField, MatButtonModule, MatFormFieldModule, MatInputModule],
  templateUrl: './vozila.html',
  styleUrl: './vozila.scss',
})
export class Vozila {
  private vozilaApi = inject(VozilaService);
  private uporabnikiApi = inject(UporabnikiService);
  private dialog = inject(MatDialog);
  private auth = inject(AuthService);

  protected readonly jeVodja = this.auth.jeVodja;
  protected readonly vozila = this.vozilaApi.seznam();

  // seznam uporabnikov (za izbiro lastnika) vidi samo vodja
  private uporabniki = this.uporabnikiApi.seznam(this.jeVodja);
  protected readonly stranke = computed(() => this.uporabniki.value().filter((uporabnik) => uporabnik.vloga === 'STRANKA'));

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.lastnikId);
    required(pot.znamka);
    required(pot.model);
    min(pot.letnik, 1900);
    required(pot.registrska);
  });

  protected dodaj(predloga: TemplateRef<unknown>) {
    odpriObrazec(this.dialog, {
      naslov: 'Novo vozilo',
      predloga,
      veljaven: () => !this.obrazec().invalid(),
      shrani: () => {
        const { lastnikId, ...vozilo } = this.model();
        return this.vozilaApi.ustvari({ ...vozilo, lastnik: { id: lastnikId } });
      },
    }).subscribe((shranjeno) => {
      this.obrazec().reset({ ...prazno });
      if (shranjeno) this.vozila.reload();
    });
  }

  protected izbrisi(vozilo: Vozilo) {
    this.vozilaApi.izbrisi(vozilo.id).subscribe(() => this.vozila.reload());
  }
}
