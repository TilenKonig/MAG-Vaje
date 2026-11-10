import { firstValueFrom } from 'rxjs';
import { Component, computed, inject, signal } from '@angular/core';
import { form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../core/auth.service';
import { UporabnikiService } from '../../core/api/uporabniki.service';
import { VozilaService } from '../../core/api/vozila.service';
import { Vozilo } from '../../core/models';
import { PageHeader } from '../../shared/page-header/page-header';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { lastnikId: '', znamka: '', model: '', letnik: 2020, registrska: '' };

@Component({
  imports: [MatButtonModule, PageHeader],
  templateUrl: './vozila.html',
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
  protected readonly stranke = computed(() =>
    this.uporabniki.value().filter((u) => u.vloga === 'STRANKA').map((u) => ({ vrednost: u.id, oznaka: `${u.ime} ${u.priimek}` })),
  );

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.lastnikId);
    required(pot.znamka);
    required(pot.model);
    min(pot.letnik, 1900);
    required(pot.registrska);
  });

  protected async dodaj() {
    const shranjeno = await odpriObrazec(this.dialog, {
      naslov: 'Novo vozilo',
      obrazec: this.obrazec,
      polja: [
        { kljuc: 'lastnikId', oznaka: 'Lastnik', moznosti: this.stranke, placeholder: 'Izberi stranko' },
        { kljuc: 'znamka', oznaka: 'Znamka' },
        { kljuc: 'model', oznaka: 'Model' },
        { kljuc: 'letnik', oznaka: 'Letnik', tip: 'number' },
        { kljuc: 'registrska', oznaka: 'Registrska številka' },
      ],
      shrani: () => {
        const { lastnikId, ...vozilo } = this.model();
        return this.vozilaApi.ustvari({ ...vozilo, lastnik: { id: lastnikId } });
      },
    });
    this.obrazec().reset({ ...prazno });
    if (shranjeno) this.vozila.reload();
  }

  protected async izbrisi(vozilo: Vozilo) {
    await firstValueFrom(this.vozilaApi.izbrisi(vozilo.id));
    this.vozila.reload();
  }
}
