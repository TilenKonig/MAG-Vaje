import { HttpClient, httpResource } from '@angular/common/http';
import { inject } from '@angular/core';

// Skupni klici REST vira. Komponente ne poznajo URL-jev ali HttpClient, kličejo samo servis.
export abstract class CrudService<T> {
  protected readonly http = inject(HttpClient);
  protected abstract readonly url: string;

  // Kliče se v komponenti (v polju razreda). Če je `aktiven` false, se seznam ne naloži.
  seznam(aktiven: () => boolean = () => true) {
    return httpResource<T[]>(() => (aktiven() ? this.url : undefined), { defaultValue: [] });
  }

  ustvari(telo: object) {
    return this.http.post(this.url, telo);
  }

  posodobi(id: string, telo: object) {
    return this.http.put(`${this.url}/${id}`, telo);
  }

  izbrisi(id: string) {
    return this.http.delete(`${this.url}/${id}`);
  }
}
