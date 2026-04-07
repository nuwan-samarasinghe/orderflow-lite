import { AsyncPipe, CurrencyPipe } from "@angular/common";
import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
} from "@angular/core";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Store } from "@ngrx/store";
import { debounceTime, distinctUntilChanged } from "rxjs";
import { takeUntilDestroyed, toSignal } from "@angular/core/rxjs-interop";
import { PageEvent } from "@angular/material/paginator";
import { Sort } from "@angular/material/sort";

import { MATERIAL_IMPORTS } from "../../../shared/material/material-imports";
import { ProductsActions } from "../../../store/products/products.actions";
import {
  selectPage,
  selectPageSize,
  selectPageSort,
  selectProducts,
  selectProductsLoading,
  selectTotalElements,
} from "../../../store/products/products.selectors";

@Component({
  selector: "app-products",
  standalone: true,
  imports: [ReactiveFormsModule, AsyncPipe, CurrencyPipe, ...MATERIAL_IMPORTS],
  templateUrl: "./products.page.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProductsComponent {
  private readonly store = inject(Store);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly displayedColumns = [
    "sku",
    "name",
    "category",
    "price",
    "availableQuantity",
    "active",
    "updatedAt",
  ];

  protected readonly searchControl = new FormControl("", { nonNullable: true });

  protected readonly loading = toSignal(
    this.store.select(selectProductsLoading),
    { initialValue: false },
  );

  protected readonly products = toSignal(this.store.select(selectProducts), {
    initialValue: [],
  });

  protected readonly page = toSignal(this.store.select(selectPage), {
    initialValue: 0,
  });

  protected readonly size = toSignal(this.store.select(selectPageSize), {
    initialValue: 10,
  });

  protected readonly sort = toSignal(this.store.select(selectPageSort), {
    initialValue: "name,ASC",
  });

  protected readonly totalElements = toSignal(
    this.store.select(selectTotalElements),
    { initialValue: 0 },
  );

  constructor() {
    this.loadProducts();

    this.searchControl.valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef),
      )
      .subscribe(() => {
        this.loadProducts({
          page: 0,
        });
      });
  }

  protected onPageChange(event: PageEvent): void {
    this.loadProducts({
      page: event.pageIndex,
      size: event.pageSize,
    });
  }

  protected onSortChange(event: Sort): void {
    const sort =
      event.active && event.direction
        ? `${event.active},${event.direction.toUpperCase()}`
        : "name,ASC";

    this.loadProducts({
      page: 0,
      sort,
    });
  }

  private loadProducts(
    overrides?: Partial<{
      page: number;
      size: number;
      sort: string;
    }>,
  ): void {
    this.store.dispatch(
      ProductsActions.loadProducts({
        query: {
          page: overrides?.page ?? this.page(),
          size: overrides?.size ?? this.size(),
          sort: overrides?.sort ?? this.sort(),
          search: this.searchControl.value.trim(),
        },
      }),
    );
  }
}
