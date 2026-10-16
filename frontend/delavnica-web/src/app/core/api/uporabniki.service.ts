import { Injectable } from '@angular/core';
import { Uporabnik } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class UporabnikiService extends CrudService<Uporabnik> {
  protected readonly url = '/api/uporabniki';
}
