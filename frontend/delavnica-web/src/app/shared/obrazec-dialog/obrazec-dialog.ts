import { NgTemplateOutlet } from '@angular/common';
import { Component, TemplateRef, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Observable } from 'rxjs';

export interface ObrazecDialogData {
  naslov: string;
  predloga: TemplateRef<unknown>; // polja obrazca (<ng-template> iz strani, ki okno odpre)
  veljaven: () => boolean;
  shrani: () => Observable<unknown>; // klic strežnika; okno se zapre šele po uspehu
}

// Okno z naslovom, polji obrazca ter gumbi Prekliči in Shrani. Zapre se z `true`, če je bilo shranjeno.
@Component({
  imports: [NgTemplateOutlet, MatDialogModule, MatButtonModule],
  templateUrl: './obrazec-dialog.html',
  styleUrl: './obrazec-dialog.scss',
})
export class ObrazecDialog {
  protected readonly podatki = inject<ObrazecDialogData>(MAT_DIALOG_DATA);
  private okno = inject(MatDialogRef<ObrazecDialog>);

  protected shrani() {
    this.podatki.shrani().subscribe(() => this.okno.close(true));
  }
}

export function odpriObrazec(dialog: MatDialog, podatki: ObrazecDialogData) {
  return dialog.open(ObrazecDialog, { data: podatki, width: '480px' }).afterClosed();
}
