import { AsyncPipe } from "@angular/common";
import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { Store } from "@ngrx/store";
import { toSignal } from "@angular/core/rxjs-interop";

import { MATERIAL_IMPORTS } from "../../../shared/material/material-imports";
import { CustomersActions } from "../../../store/customers/customer.actions";
import {
  selectCustomers,
  selectCustomersLoading,
} from "../../../store/customers/customer.selectors";

@Component({
  selector: "app-customers",
  standalone: true,
  imports: [ReactiveFormsModule, AsyncPipe, ...MATERIAL_IMPORTS],
  templateUrl: "./customers.page.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CustomersComponent {
  private readonly store = inject(Store);

  loading$ = this.store.select(selectCustomersLoading); // asyncpipe is used to subscribe to this observable in the template loading$ | async is doing that

  protected readonly customers = toSignal(this.store.select(selectCustomers), {
    initialValue: [],
  });

  constructor() {
    this.store.dispatch(CustomersActions.loadCustomers());
  }
}
