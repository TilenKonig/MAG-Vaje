import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';
import { Stanje } from '../../core/models';

const oznake: Record<Stanje, string> = {
  ODPRT: 'Odprt',
  V_DELU: 'V delu',
  ZAKLJUCEN: 'Zaključen',
  OBRACUNAN: 'Obračunan',
};

// Stanje naloga kot barvna oznaka; besedilo je vedno prisotno, barva je le dodatek.
@Component({
  selector: 'app-status-badge',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `<span class="status" [class]="'status--' + stanje().toLowerCase()">{{ oznaka() }}</span>`,
  styles: `
    .status {
      display: inline-block;
      padding: 2px 10px;
      border-radius: 999px;
      border: 1px solid transparent;
      font-weight: 600;
      white-space: nowrap;
    }

    .status--odprt {
      background: var(--mat-sys-surface-container-high);
      color: var(--mat-sys-on-surface);
    }

    .status--v_delu {
      background: var(--mat-sys-primary-container);
      color: var(--mat-sys-on-primary-container);
    }

    .status--zakljucen {
      background: var(--mat-sys-primary);
      color: var(--mat-sys-on-primary);
    }

    .status--obracunan {
      border-color: var(--mat-sys-outline);
      color: var(--mat-sys-on-surface-variant);
    }
  `,
})
export class StatusBadge {
  readonly stanje = input.required<Stanje>();
  protected readonly oznaka = computed(() => oznake[this.stanje()]);
}
