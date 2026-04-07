interface SortDto {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

interface PageableDto {
  offset: number;
  pageNumber: number;
  pageSize: number;
  paged: boolean;
  sort: SortDto;
  unpaged: boolean;
}

export interface PageResponseDto<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: PageableDto;
  size: number;
  sort: SortDto;
  totalElements: number;
  totalPages: number;
}

export interface PagedRequestDto {
  page: number;
  size: number;
  sort?: string;
  search?: string;
}
