import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RacuniService } from '../../core/api/racuni.service';
import { AuthService } from '../../core/auth.service';
import { Racun } from '../../core/models';
import { PageHeader } from '../../shared/page-header/page-header';

@Component({
  imports: [MatButtonModule, PageHeader],
  templateUrl: './racuni.html',
})
export class Racuni {
  private racuniApi = inject(RacuniService);
  private auth = inject(AuthService);

  protected readonly jeVodja = this.auth.jeVodja;
  protected readonly racuni = this.racuniApi.seznam();

  protected oznaciPlacan(racun: Racun) {
    this.racuniApi.posodobi(racun.id, { placan: true }).subscribe(() => this.racuni.reload());
  }
}
