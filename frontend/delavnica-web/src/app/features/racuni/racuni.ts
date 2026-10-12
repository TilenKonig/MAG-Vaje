import { HttpClient, httpResource } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../core/auth.service';
import { Racun } from '../../core/models';

@Component({
  imports: [MatButtonModule],
  templateUrl: './racuni.html',
  styleUrl: './racuni.scss',
})
export class Racuni {
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  protected readonly jeVodja = this.auth.jeVodja;
  protected readonly racuni = httpResource<Racun[]>(() => '/api/racuni', { defaultValue: [] });

  protected oznaciPlacan(racun: Racun) {
    this.http.put(`/api/racuni/${racun.id}`, { placan: true }).subscribe(() => this.racuni.reload());
  }
}
