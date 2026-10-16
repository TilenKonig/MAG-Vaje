import { Injectable } from '@angular/core';
import { RezervniDel } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class DeliService extends CrudService<RezervniDel> {
  protected readonly url = '/api/deli';
}
