import { Component, input, model } from '@angular/core';
import { FormValueControl } from '@angular/forms/signals';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

// Vnosno polje z oznako; uporaba: <app-polje label="Ime" [formField]="obrazec.ime" />
@Component({
  selector: 'app-polje',
  imports: [MatFormFieldModule, MatInputModule],
  template: `
    <mat-form-field>
      <mat-label>{{ label() }}</mat-label>
      <input matInput #vnos [type]="type()" [value]="value()" (input)="value.set(type() === 'number' ? vnos.valueAsNumber : vnos.value)" />
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
}
