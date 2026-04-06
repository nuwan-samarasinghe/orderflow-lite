import { AsyncPipe } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  computed,
} from "@angular/core";
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
  private readonly destroyRef = inject(DestroyRef);

  protected readonly loading$ = this.store.select(selectCustomersLoading);
  private readonly customerSignal = toSignal(
    this.store.select(selectCustomers),
    { initialValue: [] },
  );

  protected readonly filteredCustomers = computed(() => {
    const customers = this.customerSignal();
    // Add filtering logic here if needed
    return customers;
  });

  constructor() {
    this.store.dispatch(CustomersActions.loadCustomers());
  }
}
