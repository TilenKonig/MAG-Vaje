import { Component, input, model } from '@angular/core';
import { FormValueControl } from '@angular/forms/signals';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

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
      <select matNativeControl #izbira (change)="value.set(izbira.value)">
        @if (placeholder(); as p) {
          <option value="" disabled [selected]="!value()">{{ p }}</option>
        }
        @for (m of moznosti(); track m.vrednost) {
          <option [value]="m.vrednost" [selected]="m.vrednost === value()">{{ m.oznaka }}</option>
        }
      </select>
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
}
