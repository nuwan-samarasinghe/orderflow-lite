import { createSelector } from "@ngrx/store";
import { productsFeature } from "./products.reducer";

export const selectProductsState = productsFeature.selectProductsState;
export const selectProducts = productsFeature.selectContent;
export const selectPage = productsFeature.selectPage;
export const selectPageSize = productsFeature.selectSize;
export const selectPageSort = productsFeature.selectSort;
export const selectTotalElements = productsFeature.selectTotalElements;
export const selectProductsLoading = productsFeature.selectLoading;
export const selectActiveProducts = createSelector(selectProducts, (products) =>
  products.filter((product) => product.active),
);
// The above code defines selectors for the products state in an NgRx store.
// It includes selectors for the entire products state, the list of products, the loading status, and a selector for active
// products.
// The selectActiveProducts selector filters the list of products to return only those that are marked as active.
