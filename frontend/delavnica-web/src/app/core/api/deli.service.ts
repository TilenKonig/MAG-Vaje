import { Injectable } from '@angular/core';
import { RezervniDel } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class DeliService extends CrudService<RezervniDel> {
  protected readonly url = '/api/deli';

  naroci(id: string, kolicina: number) {
    return this.http.post(`${this.url}/${id}/narocilo`, { kolicina });
  }
}
