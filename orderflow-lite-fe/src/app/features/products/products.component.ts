import { AsyncPipe, CurrencyPipe } from '@angular/common';
import { ChangeDetectionStrategy, Component, DestroyRef, computed, inject, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Store } from '@ngrx/store';
import { debounceTime, distinctUntilChanged, startWith } from 'rxjs';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';

import { MATERIAL_IMPORTS } from '../../shared/material/material-imports';
import { ProductsActions } from '../../store/products/products.actions';
import { selectProducts, selectProductsLoading } from '../../store/products/products.selectors';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [ReactiveFormsModule, AsyncPipe, CurrencyPipe, ...MATERIAL_IMPORTS],
  template: `
    <section>
      <div class="page-header">
        <div>
          <h1>Products</h1>
          <p>NgRx for shared state, RxJS for search, and signals for derived UI state.</p>
        </div>
      </div>

      <mat-form-field appearance="outline" subscriptSizing="dynamic">
        <mat-label>Search products</mat-label>
        <input matInput [formControl]="searchControl" placeholder="Search by name or SKU">
      </mat-form-field>

      @if (loading$ | async) {
        <mat-progress-spinner mode="indeterminate" diameter="36" />
      } @else {
        <div class="grid grid-3">
          @for (product of filteredProducts(); track product.id) {
            <mat-card>
              <mat-card-header>
                <mat-card-title>{{ product.name }}</mat-card-title>
                <mat-card-subtitle>{{ product.sku }} • {{ product.category }}</mat-card-subtitle>
              </mat-card-header>
              <mat-card-content>
                <p>{{ product.price | currency }}</p>
                <mat-chip-set>
                  <mat-chip>{{ product.active ? 'Active' : 'Inactive' }}</mat-chip>
                </mat-chip-set>
              </mat-card-content>
            </mat-card>
          }
        </div>
      }
    </section>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductsComponent {
  private readonly store = inject(Store);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly searchControl = new FormControl('', { nonNullable: true });
  protected readonly loading$ = this.store.select(selectProductsLoading);
  private readonly productsSignal = toSignal(this.store.select(selectProducts), { initialValue: [] });
  private readonly searchTerm = signal('');

  protected readonly filteredProducts = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();
    const products = this.productsSignal();

    if (!term) {
      return products;
    }

    return products.filter(product =>
      product.name.toLowerCase().includes(term) || product.sku.toLowerCase().includes(term)
    );
  });

  constructor() {
    this.store.dispatch(ProductsActions.loadProducts());

    this.searchControl.valueChanges
      .pipe(
        startWith(this.searchControl.value),
        debounceTime(250),
        distinctUntilChanged(),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(value => this.searchTerm.set(value));
  }
}
