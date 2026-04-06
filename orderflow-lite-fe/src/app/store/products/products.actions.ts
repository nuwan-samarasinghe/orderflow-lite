import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { ProductVm } from '../../core/services/products-api.service';

export const ProductsActions = createActionGroup({
  source: 'Products',
  events: {
    'Load Products': emptyProps(),
    'Load Products Success': props<{ products: ProductVm[] }>(),
    'Load Products Failure': props<{ errorMessage: string }>()
  }
});
// The above code defines a set of actions for managing products in an NgRx store. It uses the createActionGroup 
// function to group related actions together under the source 'Products'. The actions include loading products, 
// handling successful loading of products, and handling failure when loading products. 
// Each action is defined with its respective properties, such as the list of products for a successful load or an 
// error message for a failed load.