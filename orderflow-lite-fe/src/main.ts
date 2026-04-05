import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideRouter, withComponentInputBinding, withPreloading, PreloadAllModules } from '@angular/router';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { provideStoreDevtools } from '@ngrx/store-devtools';

import { AppComponent } from './app/app.component';
import { appRoutes } from './app/app.routes';
import { appReducers } from './app/store/app.reducers';
import { ProductsEffects } from './app/store/products/products.effects';
import { requestIdInterceptor } from './app/core/interceptors/request-id.interceptor';
import { apiErrorInterceptor } from './app/core/interceptors/api-error.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideAnimationsAsync(),
    provideRouter(appRoutes, withComponentInputBinding(), withPreloading(PreloadAllModules)),
    provideHttpClient(withInterceptors([requestIdInterceptor, apiErrorInterceptor])),
    provideStore(appReducers),
    provideEffects([ProductsEffects]),
    provideStoreDevtools({ maxAge: 25, logOnly: false })
  ]
}).catch((err: unknown) => console.error(err));
