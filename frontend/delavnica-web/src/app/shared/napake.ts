import { ValidationError } from '@angular/forms/signals';

const besedila: Record<string, string> = {
  required: 'Obvezno polje',
  min: 'Vrednost je premajhna',
  email: 'Neveljaven email',
};

// Prvo napako polja pretvori v besedilo; validator lahko poda tudi svoje sporočilo ({ message }).
export const sporociloNapake = (napake: readonly ValidationError.WithOptionalFieldTree[]) =>
  napake.length ? (napake[0].message ?? besedila[napake[0].kind] ?? 'Neveljaven vnos') : '';
