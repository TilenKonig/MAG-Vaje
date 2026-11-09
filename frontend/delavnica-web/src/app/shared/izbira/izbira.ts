import { Component, computed, input, model, output } from '@angular/core';
import { FormValueControl, ValidationError } from '@angular/forms/signals';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { sporociloNapake } from '../napake';

export interface Moznost {
  vrednost: string;
  oznaka: string;
}

// Spustni seznam z oznako; uporaba: <app-izbira label="Vozilo" [moznosti]="vozila()" [formField]="obrazec.voziloId" />
// Če je podan `placeholder`, je prva (neizbirna) možnost prazna izbira.
@Component({
  selector: 'app-izbira',
  imports: [MatFormFieldModule, MatInputModule],
  template: `
    <mat-form-field>
      <mat-label>{{ label() }}</mat-label>
      <select
        matNativeControl
        #izbira
        [disabled]="disabled()"
        [required]="required()"
        [errorStateMatcher]="matcher"
        (change)="value.set(izbira.value)"
        (blur)="touch.emit()"
      >
        @if (placeholder(); as p) {
          <option value="" disabled [selected]="!value()">{{ p }}</option>
        }
        @for (m of moznosti(); track m.vrednost) {
          <option [value]="m.vrednost" [selected]="m.vrednost === value()">{{ m.oznaka }}</option>
        }
      </select>
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
export class Izbira implements FormValueControl<string> {
  readonly label = input.required<string>();
  readonly moznosti = input.required<Moznost[]>();
  readonly placeholder = input('');
  readonly value = model('');

  // stanje polja, ki ga nastavi [formField]
  readonly disabled = input(false);
  readonly required = input(false);
  readonly invalid = input(false);
  readonly touched = input(false);
  readonly errors = input<readonly ValidationError.WithOptionalFieldTree[]>([]);
  readonly touch = output<void>();

  protected readonly napaka = computed(() => (this.touched() ? sporociloNapake(this.errors()) : ''));
  protected readonly matcher: ErrorStateMatcher = { isErrorState: () => this.touched() && this.invalid() };
}
