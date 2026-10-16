import { httpResource } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DelovniNalog, PostavkaNaloga } from '../models';
import { CrudService } from './crud.service';

@Injectable({ providedIn: 'root' })
export class NalogiService extends CrudService<DelovniNalog> {
  protected readonly url = '/api/nalogi';

  // postavke izbranega naloga, brez izbire se ne naložijo
  postavke(nalogId: () => string | null) {
    return httpResource<PostavkaNaloga[]>(() => (nalogId() ? `${this.url}/${nalogId()}/postavke` : undefined), { defaultValue: [] });
  }

  dodajDel(nalogId: string, telo: object) {
    return this.http.post(`${this.url}/${nalogId}/postavke`, telo);
  }

  izdajRacun(nalogId: string) {
    return this.http.post(`${this.url}/${nalogId}/racun`, null);
  }
}
