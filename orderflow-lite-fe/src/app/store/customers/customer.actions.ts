import { createActionGroup, emptyProps, props } from "@ngrx/store";
import { CustomerDto } from "../../core/dtos/customer.dto";

export const CustomersActions = createActionGroup({
  source: "Customers",
  events: {
    "Load Customers": emptyProps(),
    "Load Customers Success": props<{ customers: CustomerDto[] }>(),
    "Load Customers Failure": props<{ errorMessage: string }>(),
  },
});
