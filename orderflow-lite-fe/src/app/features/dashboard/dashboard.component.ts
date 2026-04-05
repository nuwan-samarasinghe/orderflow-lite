import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';

import { MATERIAL_IMPORTS } from '../../shared/material/material-imports';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [...MATERIAL_IMPORTS],
  template: `
    <section>
      <div class="page-header">
        <div>
          <h1>Dashboard</h1>
          <p>Signal-driven summary cards for the interview demo.</p>
        </div>
        <button mat-raised-button type="button" (click)="refresh()">Refresh</button>
      </div>

      <div class="grid grid-3">
        @for (metric of metrics(); track metric.label) {
          <mat-card>
            <mat-card-header>
              <mat-card-title>{{ metric.label }}</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <h2>{{ metric.value }}</h2>
            </mat-card-content>
          </mat-card>
        }
      </div>
    </section>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {
  private readonly refreshCount = signal(0);

  protected readonly metrics = computed(() => {
    const offset = this.refreshCount();
    return [
      { label: 'Orders Today', value: 18 + offset },
      { label: 'Revenue', value: `$${(8450 + offset * 100).toLocaleString()}` },
      { label: 'Low Stock Alerts', value: 3 }
    ];
  });

  protected refresh(): void {
    this.refreshCount.update(value => value + 1);
  }
}
