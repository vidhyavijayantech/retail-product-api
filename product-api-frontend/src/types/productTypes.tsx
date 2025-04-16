export interface Colour {
  id: number;
  name: string;
}

export interface ProductType {
  id: number;
  name: string;
}

export interface ProductDetail {
  id: number;
  name: string;
  productType: ProductType;
  colours: Colour[];
}
