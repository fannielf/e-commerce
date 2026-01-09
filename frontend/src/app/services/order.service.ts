import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Order, Status } from '../models/order.model';
import { ORDER_BASE_URL } from '../constants/constants';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = `${ORDER_BASE_URL}`;

  constructor(private http: HttpClient) {}

  private mapOrder(item: any): Order {
    return {
      orderId: item.orderId,
      items: item.items.map((item: any) => ({
        productId: item.productId,
        productName: item.productName,
        quantity: item.quantity,
        price: item.price,
        subtotal: item.subtotal,
        sellerId: item.sellerId
      })),
      totalPrice: item.totalPrice,
      status: item.status as Status,
      shippingAddress: {
        fullName: item.shippingAddress.fullName,
        street: item.shippingAddress.street,
        city: item.shippingAddress.city,
        postalCode: item.shippingAddress.postalCode,
        country: item.shippingAddress.country
      },
      paid: item.paid,
      deliveryDate: new Date(item.deliveryDate) || null,
      trackingNumber: item.trackingNumber || null,
      createdAt: new Date(item.createdAt),
      updatedAt: new Date(item.updatedAt) || null
    };
  }

  getOrderById(orderId: string): Observable<Order> {
    return this.http.get<any>(`${this.apiUrl}/${orderId}`).pipe(
      map(order => this.mapOrder(order))
    );
  }

}
