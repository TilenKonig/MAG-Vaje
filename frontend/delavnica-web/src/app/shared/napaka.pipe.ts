import { Pipe, PipeTransform } from '@angular/core';
import { ValidationError } from '@angular/forms/signals';

const besedila: Record<string, string> = {
  required: 'Obvezno polje',
  min: 'Vrednost je premajhna',
  email: 'Neveljaven email',
};

// Prvo napako polja pretvori v besedilo; validator lahko poda tudi svoje sporočilo ({ message }).
@Pipe({ name: 'napaka' })
export class NapakaPipe implements PipeTransform {
  transform(napake: readonly ValidationError.WithOptionalFieldTree[]) {
    const napaka = napake[0];
    return napaka ? (napaka.message ?? besedila[napaka.kind] ?? 'Neveljaven vnos') : '';
  }
}
