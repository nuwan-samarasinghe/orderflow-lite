import { ActionReducerMap } from "@ngrx/store";

import {
  productsFeatureKey,
  productsReducer,
  ProductsState,
} from "./products/products.reducer";
import {
  customersFeatureKey,
  customersReducer,
  CustomersState,
} from "./customers/customer.reducer";

export interface AppState {
  [productsFeatureKey]: ProductsState;
  [customersFeatureKey]: CustomersState;
}

export const appReducers: ActionReducerMap<AppState> = {
  [productsFeatureKey]: productsReducer,
  [customersFeatureKey]: customersReducer,
};
