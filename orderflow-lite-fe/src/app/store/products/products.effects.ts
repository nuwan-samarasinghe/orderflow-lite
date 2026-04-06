import { inject, Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, of, switchMap } from "rxjs";

import { ProductsApiService } from "../../core/services/products-api.service";
import { ProductsActions } from "./products.actions";

@Injectable()
export class ProductsEffects {
  private readonly actions$ = inject(Actions);
  private readonly productsApi = inject(ProductsApiService);

  readonly loadProducts$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ProductsActions.loadProducts), // Listen for the 'Load Products' action
      switchMap(() =>
        // switchMap handles previous request cancellation logic, so we don't need to manually cancel previous requests
        this.productsApi.getProducts().pipe(
          // pipe transforms the Observable returned by getProducts
            map((products) => ProductsActions.loadProductsSuccess({ products })), // map the API response to a success action
          catchError(
            () =>
              of(
                ProductsActions.loadProductsFailure({
                  errorMessage: "Unable to load products.",
                }),
              ), // catchError maps any errors to a failure action with an error message
          ),
        ),
      ),
    ),
  );
}
// The above code defines an NgRx effect for loading products. It listens for the 'Load Products' action and,
// when triggered, fetches the products from the API and dispatches the appropriate success or failure actions.
