import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable, of } from "rxjs";
import { environment } from "../../../environments/environment";
import { ProductDto } from "../dtos/product.dto";
import { PagedRequestDto, PageResponseDto } from "../dtos/paged.dto";

@Injectable({ providedIn: "root" })
export class ProductsApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiDomain}/api/v1/products`;

  getProducts(
    request: PagedRequestDto,
  ): Observable<PageResponseDto<ProductDto>> {
    let params = new HttpParams()
      .set("page", request.page)
      .set("size", request.size);

    if (request.sort) {
      params = params.set("sort", request.sort);
    }
    return this.http.get<PageResponseDto<ProductDto>>(this.apiUrl, { params });
  }
}
