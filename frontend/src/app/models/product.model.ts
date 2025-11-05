export interface Product {
  productId: string;
  name: string;
  description: string;
  price: number;
  quantity: number;
  ownerId: string;
  image?: string;
  isProductOwner?: boolean;
}
