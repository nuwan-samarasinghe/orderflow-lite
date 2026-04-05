import { Routes } from '@angular/router';

export const appRoutes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'dashboard'
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
    title: 'Dashboard | OrderFlow Lite'
  },
  {
    path: 'products',
    loadComponent: () => import('./features/products/products.component').then(m => m.ProductsComponent),
    title: 'Products | OrderFlow Lite'
  },
  {
    path: 'orders',
    loadComponent: () => import('./features/orders/orders.component').then(m => m.OrdersComponent),
    title: 'Orders | OrderFlow Lite'
  },
  {
    path: '**',
    loadComponent: () => import('./features/not-found/not-found.component').then(m => m.NotFoundComponent),
    title: 'Not Found | OrderFlow Lite'
  }
];
