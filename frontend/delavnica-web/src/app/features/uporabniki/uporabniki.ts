import { firstValueFrom } from 'rxjs';
import { Component, inject, signal } from '@angular/core';
import { email, form, required } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { UporabnikiService } from '../../core/api/uporabniki.service';
import { Uporabnik } from '../../core/models';
import { PageHeader } from '../../shared/page-header/page-header';
import { odpriObrazec } from '../../shared/obrazec-dialog/obrazec-dialog';

const prazno = { ime: '', priimek: '', email: '', geslo: '', telefon: '', vloga: 'STRANKA' };

// +386 41 000 000 / 00386... -> 041000000; tuje številke dobijo samo odstranjene presledke/vezaje
const slovenskiTelefon = (t: string) => t.replace(/[\s\-/]/g, '').replace(/^(\+|00)386/, '0');

@Component({
  imports: [MatButtonModule, PageHeader],
  templateUrl: './uporabniki.html',
})
export class Uporabniki {
  private uporabnikiApi = inject(UporabnikiService);
  private dialog = inject(MatDialog);

  protected readonly vloge = ['STRANKA', 'MEHANIK', 'VODJA'].map((v) => ({ vrednost: v, oznaka: v }));
  protected readonly uporabniki = this.uporabnikiApi.seznam();

  private model = signal({ ...prazno });
  protected readonly obrazec = form(this.model, (pot) => {
    required(pot.ime);
    required(pot.priimek);
    required(pot.email);
    email(pot.email);
    required(pot.geslo, { when: () => !this.urejanjeId() }); // pri urejanju prazno geslo ohrani staro
  });
  protected readonly urejanjeId = signal<string | null>(null);

  protected async odpri(u?: Uporabnik) {
    this.urejanjeId.set(u?.id ?? null);
    this.obrazec().reset(u ? { ...prazno, ...u, telefon: u.telefon ?? '' } : { ...prazno });
    const shranjeno = await odpriObrazec(this.dialog, {
      naslov: u ? 'Urejanje uporabnika' : 'Nov uporabnik',
      obrazec: this.obrazec,
      polja: [
        { kljuc: 'ime', oznaka: 'Ime' },
        { kljuc: 'priimek', oznaka: 'Priimek' },
        { kljuc: 'email', oznaka: 'Email', tip: 'email' },
        { kljuc: 'geslo', oznaka: u ? 'Geslo (prazno = nespremenjeno)' : 'Geslo', tip: 'password' },
        { kljuc: 'telefon', oznaka: 'Telefon' },
        { kljuc: 'vloga', oznaka: 'Vloga', moznosti: () => this.vloge },
      ],
      shrani: () => {
        const { geslo, ...telo } = this.model();
        const osnova = { ...telo, telefon: slovenskiTelefon(telo.telefon) };
        const podatki = geslo ? { ...osnova, geslo } : osnova;
        return u ? this.uporabnikiApi.posodobi(u.id, podatki) : this.uporabnikiApi.ustvari(podatki);
      },
    });
    this.obrazec().reset({ ...prazno });
    if (shranjeno) this.uporabniki.reload();
  }

  protected async izbrisi(uporabnik: Uporabnik) {
    await firstValueFrom(this.uporabnikiApi.izbrisi(uporabnik.id));
    this.uporabniki.reload();
  }
}
