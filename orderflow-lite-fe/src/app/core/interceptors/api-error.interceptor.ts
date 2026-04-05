import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { catchError, throwError } from 'rxjs';

export const apiErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);

  return next(req).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse) {
        const message = typeof error.error?.message === 'string'
          ? error.error.message
          : 'Something went wrong while processing your request.';
        snackBar.open(message, 'Dismiss', { duration: 4000 });
      }

      return throwError(() => error);
    })
  );
};
