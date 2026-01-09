export interface Order {
  orderId: string;
  items: Item[];
  totalPrice: number;
  status: Status;
  shippingAddress: ShippingAddress;
  paid: boolean;
  deliveryDate: Date | null;
  trackingNumber: string | null;
  createdAt: Date;
  updatedAt: Date | null;
}

export interface Item {
  productId: string;
  productName: string;
  quantity: number;
  price: number;
  subtotal: number;
  sellerId: string;
}

export interface ShippingAddress {
  fullName: string;
  street: string;
  city: string;
  postalCode: string;
  country: string;
}

export enum Status {
  CREATED = 'CREATED',
  CONFIRMED = 'CONFIRMED',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELED = 'CANCELED'
}
