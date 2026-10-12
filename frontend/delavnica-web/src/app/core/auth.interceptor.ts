import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { EMPTY, catchError } from 'rxjs';
import { AuthService } from './auth.service';

// Doda žeton v vsako zahtevo in na enem mestu prikaže napake strežnika.
export const authInterceptor: HttpInterceptorFn = (zahteva, naprej) => {
  const auth = inject(AuthService);
  const snackBar = inject(MatSnackBar);
  const token = auth.token();
  const zZetonom = token ? zahteva.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : zahteva;

  return naprej(zZetonom).pipe(
    catchError((napaka) => {
      if (napaka.status === 401 && zahteva.url !== '/api/prijava') {
        auth.odjava(); // žeton je potekel
      } else {
        const sporocilo = napaka.status === 401 ? 'Napačen email ali geslo' : (napaka.error?.message ?? 'Napaka strežnika');
        snackBar.open(sporocilo, 'V redu', { duration: 4000 });
      }
      return EMPTY; // napaka je že prikazana, uspešni del (subscribe) se ne izvede
    }),
  );
};
