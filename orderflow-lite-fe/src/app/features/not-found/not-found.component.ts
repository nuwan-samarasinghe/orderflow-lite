import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { MATERIAL_IMPORTS } from '../../shared/material/material-imports';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink, ...MATERIAL_IMPORTS],
  template: `
    <mat-card>
      <mat-card-header>
        <mat-card-title>Page not found</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <p>The page you requested does not exist.</p>
        <a mat-raised-button routerLink="/dashboard">Back to dashboard</a>
      </mat-card-content>
    </mat-card>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotFoundComponent {}
