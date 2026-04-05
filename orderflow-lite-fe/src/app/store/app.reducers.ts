import { ActionReducerMap } from '@ngrx/store';

import { productsFeatureKey, productsReducer, ProductsState } from './products/products.reducer';

export interface AppState {
  [productsFeatureKey]: ProductsState;
}

export const appReducers: ActionReducerMap<AppState> = {
  [productsFeatureKey]: productsReducer
};
