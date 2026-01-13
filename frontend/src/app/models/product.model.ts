export interface Product {
  productId: string;
  name: string;
  description: string;
  price: number;
  quantity: number;
  userId: string;
  images?: string[];
  isProductOwner?: boolean;
}
