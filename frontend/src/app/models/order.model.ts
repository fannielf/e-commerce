export interface ItemDTO {
  productId: string;
  productName: string;
  quantity: number;
  price: number;
  subtotal: number;
  sellerId: string;
}

export interface OrderResponseDTO {
  orderId: string;
  items: ItemDTO[];
  totalPrice: number;
  status: Status;
  shippingAddress: ShippingAddress;
  paid: boolean;
  deliveryDate: Date | null;
  trackingNumber: string | null;
  createdAt: Date;
  updatedAt: Date | null; }

export interface ShippingAddress {
  fullName: string;
  street: string;
  city: string;
  postalCode: string;
  country: string;
}

export interface OrderDashboardDTO {
  orders: OrderResponseDTO[];
  topItems: ItemDTO[];
  total: number;
}

export enum Status {
  CREATED = 'CREATED',
  CONFIRMED = 'CONFIRMED',
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELED = 'CANCELED'
}

export const OrderStatusList = Object.values(Status);