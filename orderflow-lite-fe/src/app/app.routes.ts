import { Routes } from "@angular/router";

export const appRoutes: Routes = [
  {
    path: "",
    pathMatch: "full",
    redirectTo: "admin/dashboard",
  },
  {
    path: "admin",
    children: [
      {
        path: "dashboard",
        loadComponent: () =>
          import("./features/admin/dashboard/dashboard.page.component").then(
            (m) => m.DashboardComponent,
          ),
        title: "Dashboard | OrderFlow Lite",
      },
      {
        path: "products",
        loadComponent: () =>
          import("./features/admin/products/products.page.component").then(
            (m) => m.ProductsComponent,
          ),
        title: "Products | OrderFlow Lite",
      },
      {
        path: "orders",
        loadComponent: () =>
          import("./features/admin/orders/orders.page.component").then(
            (m) => m.OrdersComponent,
          ),
        title: "Orders | OrderFlow Lite",
      },
      {
        path: "customers",
        loadComponent: () =>
          import("./features/admin/customers/customers.page.component").then(
            (m) => m.CustomersComponent,
          ),
        title: "Customers | OrderFlow Lite",
      },
    ],
  },
  {
    path: "**",
    loadComponent: () =>
      import("./features/not-found/not-found.page.component").then(
        (m) => m.NotFoundComponent,
      ),
    title: "Not Found | OrderFlow Lite",
  },
];
