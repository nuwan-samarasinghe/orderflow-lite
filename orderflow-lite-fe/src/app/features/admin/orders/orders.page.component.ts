import { AsyncPipe, CurrencyPipe } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { toSignal } from "@angular/core/rxjs-interop";

import { OrdersApiService } from "../../../core/services/orders-api.service";
import { MATERIAL_IMPORTS } from "../../../shared/material/material-imports";

@Component({
  selector: "app-orders",
  standalone: true,
  imports: [AsyncPipe, CurrencyPipe, ...MATERIAL_IMPORTS],
  templateUrl: "./orders.page.component.html",
  styles: [
    `
      .full-width {
        width: 100%;
      }
    `,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OrdersComponent {
  private readonly ordersApi = inject(OrdersApiService);

  protected readonly displayedColumns = [
    "orderNumber",
    "status",
    "totalAmount",
  ];
  protected readonly orders = toSignal(this.ordersApi.getOrders(), {
    initialValue: [],
  });
}
