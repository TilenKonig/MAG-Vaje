import { Component, inject } from '@angular/core';
import { PorociloService } from '../../core/api/porocilo.service';
import { PageHeader } from '../../shared/page-header/page-header';

@Component({
  imports: [PageHeader],
  templateUrl: './porocilo.html',
})
export class Porocilo {
  protected readonly porocilo = inject(PorociloService).porocilo();
}
