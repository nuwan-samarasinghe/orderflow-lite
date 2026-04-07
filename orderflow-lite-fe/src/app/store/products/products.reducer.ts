import { createFeature, createReducer, on } from "@ngrx/store";
import { ProductsActions } from "./products.actions";
import { ProductDto } from "../../core/dtos/product.dto";

export const productsFeatureKey = "products";

export interface ProductsState {
  content: ProductDto[];
  totalElements: number;
  totalPages: number;
  page: number;
  size: number;
  sort: string;
  loading: boolean;
  errorMessage: string | null;
}

const initialState: ProductsState = {
  content: [],
  totalElements: 0,
  totalPages: 0,
  page: 0,
  size: 10,
  sort: "name,ASC",
  loading: false,
  errorMessage: null,
};

const reducer = createReducer(
  initialState,
  on(ProductsActions.loadProducts, (state, { query }) => ({
    ...state,
    loading: true,
    errorMessage: null,
    page: query.page,
    size: query.size,
    sort: query.sort ?? state.sort,
  })),
  on(ProductsActions.loadProductsSuccess, (state, { pageResponses }) => ({
    ...state,
    loading: false,
    content: pageResponses.content,
    totalElements: pageResponses.totalElements,
    totalPages: pageResponses.totalPages,
    page: pageResponses.number,
    size: pageResponses.size,
  })),
  on(ProductsActions.loadProductsFailure, (state, { errorMessage }) => ({
    ...state,
    loading: false,
    errorMessage,
  })),
);

export const productsReducer = reducer;
export const productsFeature = createFeature({
  name: productsFeatureKey,
  reducer,
});

// The above code defines an NgRx feature for managing products in an Angular application.
// It includes the state interface, reducer, and feature creation. The state includes an array of products, a loading flag, and an error message.
// The reducer handles three actions: loading products, successfully loading products, and failing to load products.
// Each action updates the state accordingly, such as setting the loading flag or updating the list of products.
// Finally, the feature is created with a specified name and the defined reducer.
