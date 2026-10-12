import { Routes } from '@angular/router';
import { authGuard, vlogaGuard } from './core/auth.guard';

// Vsaka stran se naloži šele, ko jo uporabnik odpre (hiter začetni zagon).
export const routes: Routes = [
  { path: 'prijava', loadComponent: () => import('./features/prijava/prijava').then((m) => m.Prijava) },
  {
    path: '',
    canActivate: [authGuard],
    children: [
      { path: 'nalogi', loadComponent: () => import('./features/nalogi/nalogi').then((m) => m.Nalogi) },
      { path: 'vozila', loadComponent: () => import('./features/vozila/vozila').then((m) => m.Vozila) },
      { path: 'deli', canActivate: [vlogaGuard('MEHANIK', 'VODJA')], loadComponent: () => import('./features/deli/deli').then((m) => m.Deli) },
      { path: 'racuni', loadComponent: () => import('./features/racuni/racuni').then((m) => m.Racuni) },
      { path: 'uporabniki', canActivate: [vlogaGuard('VODJA')], loadComponent: () => import('./features/uporabniki/uporabniki').then((m) => m.Uporabniki) },
      { path: '', pathMatch: 'full', redirectTo: 'nalogi' },
    ],
  },
  { path: '**', redirectTo: '' },
];
