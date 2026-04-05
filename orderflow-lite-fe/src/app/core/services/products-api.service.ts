import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface ProductVm {
  id: number;
  sku: string;
  name: string;
  category: string;
  price: number;
  active: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProductsApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/products';

  getProducts(): Observable<ProductVm[]> {
    return of([
      { id: 101, sku: 'SKU-1001', name: 'Ergonomic Chair', category: 'Furniture', price: 199.99, active: true },
      { id: 102, sku: 'SKU-1002', name: 'Standing Desk', category: 'Furniture', price: 499.00, active: true },
      { id: 103, sku: 'SKU-1003', name: 'Noise Cancelling Headset', category: 'Electronics', price: 149.50, active: false }
    ]);
    // return this.http.get<ProductVm[]>(this.apiUrl);
  }
}
