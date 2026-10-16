import { Injectable } from '@angular/core';
import { Racun } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class RacuniService extends CrudService<Racun> {
  protected readonly url = '/api/racuni';
}
