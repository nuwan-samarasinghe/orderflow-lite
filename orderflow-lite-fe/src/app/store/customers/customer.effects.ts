import { inject, Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, of, switchMap } from "rxjs";
import { CustomersActions } from "./customer.actions";
import { CustomersApiService } from "../../core/services/customers-api.service";

@Injectable()
export class CustomersEffects {
  private readonly actions$ = inject(Actions);
  private readonly customersApi = inject(CustomersApiService);

  readonly loadCustomers$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CustomersActions.loadCustomers),
      switchMap(() =>
        this.customersApi.getCustomers().pipe(
          map((customers) =>
            CustomersActions.loadCustomersSuccess({ customers }),
          ),
          catchError(() =>
            of(
              CustomersActions.loadCustomersFailure({
                errorMessage: "Unable to load customers.",
              }),
            ),
          ),
        ),
      ),
    ),
  );
}
