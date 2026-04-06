import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { CustomerDto } from "../dtos/customer.dto";
import { environment } from "../../../environments/environment";

@Injectable({ providedIn: "root" })
export class CustomersApiService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiDomain}/api/v1/customers`;

  getCustomers(): Observable<CustomerDto[]> {
    console.log("API Call: Fetching customers from", this.apiUrl);
    return this.http.get<CustomerDto[]>(this.apiUrl);
  }
}
