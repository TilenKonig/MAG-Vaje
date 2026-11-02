import { httpResource } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Nalog } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class NalogiService extends CrudService<Nalog> {
  protected readonly url = '/api/nalogi';

  iskanje(filter: () => Record<string, string>) {
    return httpResource<Nalog[]>(
      () => ({ url: this.url, params: Object.fromEntries(Object.entries(filter()).filter(([, v]) => v)) }),
      { defaultValue: [] },
    );
  }

  dodajDel(nalogId: string, telo: object) {
    return this.http.post(`${this.url}/${nalogId}/postavke`, telo);
  }

  izdajRacun(nalogId: string) {
    return this.http.post(`${this.url}/${nalogId}/racun`, null);
  }
}
