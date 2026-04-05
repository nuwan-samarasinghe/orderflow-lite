import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface OrderSummary {
  id: number;
  orderNumber: string;
  status: 'PLACED' | 'CONFIRMED' | 'SHIPPED';
  totalAmount: number;
}

@Injectable({ providedIn: 'root' })
export class OrdersApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/orders';

  getOrders(): Observable<OrderSummary[]> {
    return of([
      { id: 5001, orderNumber: 'ORD-20260405-5001', status: 'PLACED', totalAmount: 149.97 },
      { id: 5002, orderNumber: 'ORD-20260405-5002', status: 'SHIPPED', totalAmount: 89.50 }
    ]);
    // return this.http.get<OrderSummary[]>(this.apiUrl);
  }
}
