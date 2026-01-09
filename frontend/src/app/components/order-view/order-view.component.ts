import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { OrderService } from '../../services/order.service';
import { OrderResponseDTO } from '../../models/order.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-view',
  standalone: true,
  imports: [CommonModule,],
  templateUrl: './order-view.component.html',
  styleUrl: './order-view.component.css'
})
export class OrderViewComponent implements OnInit {
  order: OrderResponseDTO | null = null;
  isLoggedIn = false;

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
      this.isLoggedIn = this.authService.isLoggedIn();
      const orderId = this.route.snapshot.paramMap.get('id');
        if (orderId) {
          this.orderService.getOrderById(orderId).subscribe({
            next: (data: OrderResponseDTO) => {
              this.order = data;
              console.log('Order details:', this.order);
            },
            error: (err: unknown) => {
                console.error('Error fetching order:', err);
            }
          });
        } else {
          this.router.navigate(['']);
        }
  }

}
