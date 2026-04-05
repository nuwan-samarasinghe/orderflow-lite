import { AsyncPipe, CurrencyPipe } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  computed,
  inject,
  signal,
} from "@angular/core";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Store } from "@ngrx/store";
import { debounceTime, distinctUntilChanged, startWith } from "rxjs";
import { takeUntilDestroyed, toSignal } from "@angular/core/rxjs-interop";

import { MATERIAL_IMPORTS } from "../../shared/material/material-imports";
import { ProductsActions } from "../../store/products/products.actions";
import {
  selectProducts,
  selectProductsLoading,
} from "../../store/products/products.selectors";

@Component({
  selector: "app-products",
  standalone: true,
  imports: [ReactiveFormsModule, AsyncPipe, CurrencyPipe, ...MATERIAL_IMPORTS],
  templateUrl: "./products.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductsComponent {
  private readonly store = inject(Store);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly searchControl = new FormControl("", { nonNullable: true });
  protected readonly loading$ = this.store.select(selectProductsLoading);
  private readonly productsSignal = toSignal(
    this.store.select(selectProducts),
    { initialValue: [] },
  );
  private readonly searchTerm = signal("");

  protected readonly filteredProducts = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();
    const products = this.productsSignal();

    if (!term) {
      return products;
    }

    return products.filter(
      (product) =>
        product.name.toLowerCase().includes(term) ||
        product.sku.toLowerCase().includes(term),
    );
  });

  constructor() {
    this.store.dispatch(ProductsActions.loadProducts());

    this.searchControl.valueChanges
      .pipe(
        startWith(this.searchControl.value),
        debounceTime(250),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe((value) => this.searchTerm.set(value));
  }
}
