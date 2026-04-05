import { createFeature, createReducer, on } from '@ngrx/store';
import { ProductVm } from '../../core/services/products-api.service';
import { ProductsActions } from './products.actions';

export const productsFeatureKey = 'products';

export interface ProductsState {
  items: ProductVm[];
  loading: boolean;
  errorMessage: string | null;
}

const initialState: ProductsState = {
  items: [],
  loading: false,
  errorMessage: null
};

const reducer = createReducer(
  initialState,
  on(ProductsActions.loadProducts, state => ({ ...state, loading: true, errorMessage: null })),
  on(ProductsActions.loadProductsSuccess, (state, { products }) => ({ ...state, items: products, loading: false })),
  on(ProductsActions.loadProductsFailure, (state, { errorMessage }) => ({ ...state, loading: false, errorMessage }))
);

export const productsReducer = reducer;
export const productsFeature = createFeature({
  name: productsFeatureKey,
  reducer
});
