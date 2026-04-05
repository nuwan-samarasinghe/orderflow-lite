import { AsyncPipe, CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';

import { OrdersApiService } from '../../core/services/orders-api.service';
import { MATERIAL_IMPORTS } from '../../shared/material/material-imports';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [AsyncPipe, CurrencyPipe, ...MATERIAL_IMPORTS],
  template: `
    <section>
      <div class="page-header">
        <div>
          <h1>Orders</h1>
          <p>Simple read-only table for the API integration showcase.</p>
        </div>
      </div>

      <mat-card>
        <mat-card-content>
          <table mat-table [dataSource]="orders()" class="mat-elevation-z1 full-width">
            <ng-container matColumnDef="orderNumber">
              <th mat-header-cell *matHeaderCellDef>Order</th>
              <td mat-cell *matCellDef="let order">{{ order.orderNumber }}</td>
            </ng-container>

            <ng-container matColumnDef="status">
              <th mat-header-cell *matHeaderCellDef>Status</th>
              <td mat-cell *matCellDef="let order">{{ order.status }}</td>
            </ng-container>

            <ng-container matColumnDef="totalAmount">
              <th mat-header-cell *matHeaderCellDef>Total</th>
              <td mat-cell *matCellDef="let order">{{ order.totalAmount | currency }}</td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns"></tr>
          </table>
        </mat-card-content>
      </mat-card>
    </section>
  `,
  styles: [`.full-width { width: 100%; }`],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrdersComponent {
  private readonly ordersApi = inject(OrdersApiService);

  protected readonly displayedColumns = ['orderNumber', 'status', 'totalAmount'];
  protected readonly orders = toSignal(this.ordersApi.getOrders(), { initialValue: [] });
}
