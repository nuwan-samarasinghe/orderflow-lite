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
