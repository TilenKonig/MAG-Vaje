import { firstValueFrom } from 'rxjs';
import { Component, TemplateRef, computed, inject, linkedSignal, signal } from '@angular/core';
import { FormField, FormRoot, form, min, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DeliService } from '../../core/api/deli.service';
import { NalogiService } from '../../core/api/nalogi.service';
import { UporabnikiService } from '../../core/api/uporabniki.service';
import { VozilaService } from '../../core/api/vozila.service';
import { AuthService } from '../../core/auth.service';
import { Stanje } from '../../core/models';
import { Polje } from '../../shared/polje/polje';
import { Izbira } from '../../shared/izbira/izbira';
import { PageHeader } from '../../shared/page-header/page-header';
import { StatusBadge } from '../../shared/status-badge/status-badge';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazenNalog = { voziloId: '', mehanikId: '', opis: '' };
const prazenDel = { delId: '', kolicina: 1 };

@Component({
  imports: [FormRoot, FormField, MatButtonModule, MatFormFieldModule, MatInputModule, PageHeader, StatusBadge, Polje, Izbira],
  templateUrl: './nalogi.html',
  styleUrl: './nalogi.scss',
})
export class Nalogi {
  private nalogiApi = inject(NalogiService);
  private vozilaApi = inject(VozilaService);
  private deliApi = inject(DeliService);
  private uporabnikiApi = inject(UporabnikiService);
  private dialog = inject(MatDialog);
  private auth = inject(AuthService);

  protected readonly stanja: Stanje[] = ['ODPRT', 'V_DELU', 'ZAKLJUCEN', 'OBRACUNAN'];
  protected readonly jeVodja = this.auth.jeVodja;
  protected readonly jeOsebje = this.auth.jeOsebje;

  // --- podatki iz strežnika ---
  protected readonly filter = signal({ stanje: '', registrska: '', strankaId: '' });
  protected readonly nalogi = this.nalogiApi.iskanje(this.filter);
  protected readonly izbranId = signal<string | null>(null);
  protected readonly izbran = computed(() => this.nalogi.value().find((nalog) => nalog.id === this.izbranId()));
  // vozila vidi za izbiro samo vodja, dele samo osebje (stranka za to nima pravic)
  protected readonly vozila = this.vozilaApi.seznam(this.jeVodja);
  protected readonly deli = this.deliApi.seznam(this.jeOsebje);
  // mehanike za dodelitev naloga vidi samo vodja
  private readonly uporabniki = this.uporabnikiApi.seznam(this.jeVodja);
  protected readonly stranke = computed(() => this.uporabniki.value().filter((u) => u.vloga === 'STRANKA'));
  protected readonly mehaniki = computed(() =>
    this.uporabniki.value().filter((u) => u.vloga === 'MEHANIK').map((u) => ({ vrednost: u.id, oznaka: `${u.ime} ${u.priimek}` })),
  );
  protected readonly stanjaMoznosti = this.stanja.map((s) => ({ vrednost: s, oznaka: s }));
  protected readonly voziloMoznosti = computed(() =>
    this.vozila.value().map((v) => ({ vrednost: v.id, oznaka: `${v.registrska} (${v.znamka} ${v.model})` })),
  );
  protected readonly delMoznosti = computed(() =>
    this.deli.value().map((d) => ({ vrednost: d.id, oznaka: `${d.naziv} (zaloga ${d.zaloga})` })),
  );

  // --- obrazec: nov nalog (v oknu) ---
  private modelNalog = signal({ ...prazenNalog });
  protected readonly obrazecNalog = form(this.modelNalog, (pot) => {
    required(pot.voziloId);
    required(pot.mehanikId);
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

  protected nastaviFilter(polje: 'stanje' | 'registrska' | 'strankaId', vrednost: string) {
    this.filter.update((f) => ({ ...f, [polje]: vrednost }));
  }

  protected async odpriNalog(predloga: TemplateRef<unknown>) {
    const shranjeno = await odpriObrazec(this.dialog, {
      naslov: 'Nov delovni nalog',
      predloga,
      veljaven: () => !this.obrazecNalog().invalid(),
      shrani: () => {
        const { voziloId, mehanikId, opis } = this.modelNalog();
        return this.nalogiApi.ustvari({ opis, vozilo: { id: voziloId }, mehanik: { id: mehanikId } });
      },
    });
    this.obrazecNalog().reset({ ...prazenNalog });
    if (shranjeno) this.nalogi.reload();
  }

  protected async odpriDel(predloga: TemplateRef<unknown>) {
    const shranjeno = await odpriObrazec(this.dialog, {
      naslov: 'Dodaj rezervni del na nalog',
      predloga,
      veljaven: () => !this.obrazecDel().invalid(),
      shrani: () => this.nalogiApi.dodajDel(this.izbranId()!, this.modelDel()),
    });
    this.obrazecDel().reset({ ...prazenDel });
    if (shranjeno) {
      this.nalogi.reload();
      this.deli.reload(); // zaloga se je zmanjšala
    }
  }

  private async shrani() {
    await firstValueFrom(this.nalogiApi.posodobi(this.izbranId()!, this.modelUrejanje()));
    this.nalogi.reload();
  }

  protected async izdajRacun() {
    await firstValueFrom(this.nalogiApi.izdajRacun(this.izbranId()!));
    this.nalogi.reload();
  }

  protected async izbrisi() {
    await firstValueFrom(this.nalogiApi.izbrisi(this.izbranId()!));
    this.izbranId.set(null);
    this.nalogi.reload();
  }
}
