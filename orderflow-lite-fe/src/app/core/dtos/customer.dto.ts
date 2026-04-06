import { UUID } from "crypto";

export interface CustomerDto {
  id: UUID;
  fullName: string;
  email: string;
  phone: string;
  createdAt: string;
  updatedAt: string;
  addresses: AddressDto[] | null;
}

export interface AddressDto {
  id: UUID;
  line1: string;
  line2: string;
  city: string;
  state: string;
  country: string;
  zipCode: string;
  createdAt: string;
  updatedAt: string;
}
