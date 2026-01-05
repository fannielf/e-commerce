import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartResponseDTO, CartItemRequestDTO, CartItemUpdateDTO } from '../models/cart.model';
import { BASE_URL } from '../constants/constants';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = `${BASE_URL}/api/cart`;

  constructor(private http: HttpClient) {}

  addToCart(newItem: CartItemRequestDTO): Observable<CartResponseDTO> {
    return this.http.post<CartResponseDTO>(this.apiUrl, newItem);
  }

  getCart(): Observable<CartResponseDTO> {
    return this.http.get<CartResponseDTO>(this.apiUrl);
  }

  updateCartItem(productId: string, update: CartItemUpdateDTO): Observable<CartResponseDTO> {
    return this.http.put<CartResponseDTO>(`${this.apiUrl}/${productId}`, update);
  }

  deleteItem(productId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${productId}`);
  }

  clearCart(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/all`);
  }
}
