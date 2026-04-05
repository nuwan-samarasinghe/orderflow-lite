import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { MATERIAL_IMPORTS } from '../../shared/material/material-imports';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, ...MATERIAL_IMPORTS],
  template: `
    <mat-sidenav-container class="shell-container">
      <mat-sidenav [opened]="opened()" mode="side" class="shell-nav">
        <div class="brand">OrderFlow Lite</div>
        <mat-nav-list>
          <a mat-list-item routerLink="/dashboard" routerLinkActive="active-link">Dashboard</a>
          <a mat-list-item routerLink="/products" routerLinkActive="active-link">Products</a>
          <a mat-list-item routerLink="/orders" routerLinkActive="active-link">Orders</a>
        </mat-nav-list>
      </mat-sidenav>
      <mat-sidenav-content>
        <mat-toolbar>
          <button mat-icon-button type="button" (click)="toggleSidenav()" aria-label="Toggle navigation">
            <mat-icon>menu</mat-icon>
          </button>
          <span>OrderFlow Lite</span>
          <span class="spacer"></span>
          <button mat-stroked-button type="button">Admin Demo</button>
        </mat-toolbar>
        <main class="page-shell">
          <ng-content />
        </main>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .shell-container { height: 100vh; }
    .shell-nav { width: 240px; }
    .brand { font-weight: 700; padding: 1rem; }
    .spacer { flex: 1; }
    .active-link { border-right: 3px solid currentColor; font-weight: 600; }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppShellComponent {
  protected readonly opened = signal(true);

  protected toggleSidenav(): void {
    this.opened.update(value => !value);
  }
}
