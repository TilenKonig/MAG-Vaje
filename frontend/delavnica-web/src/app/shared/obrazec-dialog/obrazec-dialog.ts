import { Component, Signal, inject } from '@angular/core';
import { FormField } from '@angular/forms/signals';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Observable, firstValueFrom } from 'rxjs';
import { NapakaPipe } from '../napaka.pipe';

export interface Moznost {
  vrednost: string;
  oznaka: string;
}

// Opis enega polja: okno ga izriše samo. `moznosti` naredi spustni seznam (lahko je signal, ki se naloži kasneje).
export interface PoljeObrazca {
  kljuc: string; // ime polja v obrazcu
  oznaka: string;
  tip?: 'number' | 'email' | 'password';
  moznosti?: () => Moznost[];
  placeholder?: string; // prazna izbira na vrhu spustnega seznama
}

export interface ObrazecDialogData {
  naslov: string;
  // polja se izberejo po imenu (obrazec[kljuc]), zato je tip ohlapen; Shrani je onemogočen, dokler je obrazec neveljaven
  obrazec: (() => { invalid: Signal<boolean>; markAsTouched(): void }) & Record<string, any>;
  polja: PoljeObrazca[];
  shrani: () => Observable<unknown>; // klic strežnika; okno se zapre šele po uspehu
}

// Okno z naslovom, polji obrazca ter gumbi Prekliči in Shrani. Zapre se z `true`, če je bilo shranjeno.
@Component({
  imports: [FormField, MatDialogModule, MatButtonModule, MatFormFieldModule, MatInputModule, NapakaPipe],
  templateUrl: './obrazec-dialog.html',
  styleUrl: './obrazec-dialog.scss',
})
export class ObrazecDialog {
  protected readonly podatki = inject<ObrazecDialogData>(MAT_DIALOG_DATA);
  private okno = inject(MatDialogRef<ObrazecDialog>);

  protected async shrani() {
    try {
      await firstValueFrom(this.podatki.shrani());
      this.okno.close(true);
    } catch {
      // napako je že prikazal interceptor, okno ostane odprto
    }
  }
}

export function odpriObrazec(dialog: MatDialog, podatki: ObrazecDialogData) {
  return firstValueFrom(dialog.open(ObrazecDialog, { data: podatki, width: '480px' }).afterClosed());
}
