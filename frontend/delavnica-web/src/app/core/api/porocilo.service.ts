import { httpResource } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Porocilo } from '../models';

@Injectable({ providedIn: 'root' })
export class PorociloService {
  porocilo() {
    return httpResource<Porocilo>(() => '/api/porocilo');
  }
}
