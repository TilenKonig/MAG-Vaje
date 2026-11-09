import { Component, computed, input, model, output } from '@angular/core';
import { FormValueControl, ValidationError } from '@angular/forms/signals';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { sporociloNapake } from '../napake';

// Vnosno polje z oznako; uporaba: <app-polje label="Ime" [formField]="obrazec.ime" />
// Stanje (onemogočeno, obvezno, napake, touched) prevzame iz polja obrazca.
@Component({
  selector: 'app-polje',
  imports: [MatFormFieldModule, MatInputModule],
  template: `
    <mat-form-field>
      <mat-label>{{ label() }}</mat-label>
      <input
        matInput
        #vnos
        [type]="type()"
        [value]="value()"
        [disabled]="disabled()"
        [required]="required()"
        [errorStateMatcher]="matcher"
        (input)="value.set(type() === 'number' ? vnos.valueAsNumber : vnos.value)"
        (blur)="touch.emit()"
      />
      @if (napaka(); as n) {
        <mat-error>{{ n }}</mat-error>
      }
    </mat-form-field>
  `,
  styles: `
    :host { display: block; }
    mat-form-field { width: 100%; }
  `,
})
export class Polje implements FormValueControl<string | number> {
  readonly label = input.required<string>();
  readonly type = input<'text' | 'number' | 'password' | 'email'>('text');
  readonly value = model<string | number>('');

  // stanje polja, ki ga nastavi [formField]
  readonly disabled = input(false);
  readonly required = input(false);
  readonly invalid = input(false);
  readonly touched = input(false);
  readonly errors = input<readonly ValidationError.WithOptionalFieldTree[]>([]);
  readonly touch = output<void>();

  protected readonly napaka = computed(() => (this.touched() ? sporociloNapake(this.errors()) : ''));
  // napaka je vidna šele, ko se polja dotaknemo
  protected readonly matcher: ErrorStateMatcher = { isErrorState: () => this.touched() && this.invalid() };
}
