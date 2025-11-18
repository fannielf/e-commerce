import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { Router, RouterModule } from '@angular/router';
import { ImageUrlPipe } from '../../pipes/image-url.pipe';
import { ImageCarouselComponent } from '../shared/image-carousel/image-carousel.component';
import { BASE_URL } from '../../constants/constants';

@Component({
  selector: 'app-sellerProfile',
  standalone: true,
  imports: [CommonModule, RouterModule, ImageUrlPipe, ImageCarouselComponent],
  templateUrl: './sellerProfile.component.html',
  styleUrl: './sellerProfile.component.css'
})
export class SellerProfileComponent implements OnInit {
  user: User | null = null;
  isLoggedIn = false;
  profileImageUrl: string | null = null;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.isLoggedIn = this.authService.isLoggedIn?.() ?? false;

    this.authService.getCurrentUser().subscribe(user => {
              this.profileImageUrl = user.avatar
                ? `${BASE_URL}/media-service${user.avatar}`
                : 'assets/default.jpg';
            });

    this.userService.getMe().subscribe({
      next: (data: User) => {
        if (data && data.products) {
                  data.products.reverse();
                }
        this.user = data;
      },
      error: (err: unknown) => console.error(err),
    });
  }

  goToProduct(productId: string) {
    this.router.navigate(['/products', productId]);
  }
}
