import { ChangeDetectionStrategy, Component, input } from '@angular/core';

// Naslov strani z gumbi za akcije na desni.
@Component({
  selector: 'app-page-header',
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <h2>{{ naslov() }}</h2>
    <div class="buttons"><ng-content /></div>
  `,
  styles: `
    :host {
      display: flex;
      flex-wrap: wrap;
      gap: var(--space);
      justify-content: space-between;
      align-items: center;
      margin-bottom: calc(var(--space) * 2);
    }

    .buttons {
      display: flex;
      gap: var(--space);
    }
  `,
})
export class PageHeader {
  readonly naslov = input.required<string>();
}
