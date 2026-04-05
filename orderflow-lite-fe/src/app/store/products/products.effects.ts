import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap } from 'rxjs';

import { ProductsApiService } from '../../core/services/products-api.service';
import { ProductsActions } from './products.actions';

@Injectable()
export class ProductsEffects {
  private readonly actions$ = inject(Actions);
  private readonly productsApi = inject(ProductsApiService);

  readonly loadProducts$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ProductsActions.loadProducts),
      switchMap(() =>
        this.productsApi.getProducts().pipe(
          map(products => ProductsActions.loadProductsSuccess({ products })),
          catchError(() => of(ProductsActions.loadProductsFailure({ errorMessage: 'Unable to load products.' })))
        )
      )
    )
  );
}
