import { Injectable } from '@angular/core';
import { httpResource } from '@angular/common/http';
import { PredlogNarocila, RezervniDel } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class DeliService extends CrudService<RezervniDel> {
  protected readonly url = '/api/deli';

  predlog(aktiven: () => boolean) {
    return httpResource<PredlogNarocila[]>(() => (aktiven() ? `${this.url}/predlog-narocila` : undefined), {
      defaultValue: [],
    });
  }

  naroci(id: string, kolicina: number) {
    return this.http.post(`${this.url}/${id}/narocilo`, { kolicina });
  }
}
