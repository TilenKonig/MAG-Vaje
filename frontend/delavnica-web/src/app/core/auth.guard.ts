import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Vloga } from './models';

export const authGuard: CanActivateFn = () =>
  inject(AuthService).token() ? true : inject(Router).createUrlTree(['/prijava']);

// Samo za izbrane vloge, drugi gredo na začetno stran. Prava zaščita je na backendu.
export const vlogaGuard =
  (...vloge: Vloga[]): CanActivateFn =>
  () => {
    const vloga = inject(AuthService).vloga();
    return vloga && vloge.includes(vloga) ? true : inject(Router).createUrlTree(['/']);
  };
