import { createActionGroup, props } from "@ngrx/store";
import { ProductDto } from "../../core/dtos/product.dto";
import { PagedRequestDto, PageResponseDto } from "../../core/dtos/paged.dto";

export const ProductsActions = createActionGroup({
  source: "Products",
  events: {
    "Load Products": props<{ query: PagedRequestDto }>(),
    "Load Products Success": props<{
      pageResponses: PageResponseDto<ProductDto>;
    }>(),
    "Load Products Failure": props<{ errorMessage: string }>(),
  },
});
// The above code defines a set of actions for managing products in an NgRx store. It uses the createActionGroup
// function to group related actions together under the source 'Products'. The actions include loading products,
// handling successful loading of products, and handling failure when loading products.
// Each action is defined with its respective properties, such as the list of products for a successful load or an
// error message for a failed load.
