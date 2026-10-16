import { Injectable } from '@angular/core';
import { Vozilo } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class VozilaService extends CrudService<Vozilo> {
  protected readonly url = '/api/vozila';
}
