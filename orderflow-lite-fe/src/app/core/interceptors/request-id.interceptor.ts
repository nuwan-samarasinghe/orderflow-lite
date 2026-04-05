import { HttpInterceptorFn } from '@angular/common/http';

export const requestIdInterceptor: HttpInterceptorFn = (req, next) => {
  const requestId = crypto.randomUUID();
  return next(req.clone({
    setHeaders: {
      'X-Correlation-Id': requestId
    }
  }));
};
