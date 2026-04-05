import { createSelector } from '@ngrx/store';
import { productsFeature } from './products.reducer';

export const selectProductsState = productsFeature.selectProductsState;
export const selectProducts = productsFeature.selectItems;
export const selectProductsLoading = productsFeature.selectLoading;
export const selectActiveProducts = createSelector(selectProducts, products => products.filter(product => product.active));
