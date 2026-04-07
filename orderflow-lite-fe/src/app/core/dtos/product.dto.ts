export interface ProductDto {
  id?: string;
  sku: string;
  name: string;
  description?: string;
  price: number;
  category?: string;
  active: boolean;
  availableQuantity?: number;
  updatedAt?: string;
  createdAt?: string;
}
