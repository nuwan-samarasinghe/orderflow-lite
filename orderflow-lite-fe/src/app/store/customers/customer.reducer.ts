import { createFeature, createReducer, on } from "@ngrx/store";
import { CustomerDto } from "../../core/dtos/customer.dto";
import { CustomersActions } from "./customer.actions";

export const customersFeatureKey = "customers";

export interface CustomersState {
  items: CustomerDto[];
  loading: boolean;
  errorMessage: string | null;
}

const initialState: CustomersState = {
  items: [],
  loading: false,
  errorMessage: null,
};

const reducer = createReducer(
  initialState,
  on(CustomersActions.loadCustomers, (state) => ({
    ...state,
    loading: true,
    errorMessage: null,
  })),
  on(CustomersActions.loadCustomersSuccess, (state, { customers }) => ({
    ...state,
    items: customers,
    loading: false,
  })),
  on(CustomersActions.loadCustomersFailure, (state, { errorMessage }) => ({
    ...state,
    loading: false,
    errorMessage,
  })),
);

export const customersReducer = reducer;
export const customersFeature = createFeature({
  name: customersFeatureKey,
  reducer,
});
