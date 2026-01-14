import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product.model';
import { Router } from '@angular/router';
import { ImageUrlPipe } from '../../pipes/image-url.pipe';
import { ImageCarouselComponent } from '../shared/image-carousel/image-carousel.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, ImageUrlPipe, ImageCarouselComponent, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  products: Product[] = [];
  isLoggedIn = false;

  searchTerm: string = '';
  sortBy: string = 'latest';

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.isLoggedIn = this.authService.isLoggedIn?.() ?? false;
    this.loadProducts();
  }

  goToProduct(productId: string) {
    this.router.navigate(['/products', productId]);
  }

  onSearch() {
    this.loadProducts();
  }

  onSortChange() {
    this.loadProducts();
  }

  private loadProducts() {
    let sortParam = 'createdAt,desc';

    switch (this.sortBy) {
      case 'price_asc':
        sortParam = 'price,asc';
        break;
      case 'price_desc':
        sortParam = 'price,desc';
        break;
      case 'alpha_asc':
        sortParam = 'name,asc';
        break;
      case 'alpha_desc':
        sortParam = 'name,desc';
        break;
      case 'latest':
      default:
        sortParam = 'createdAt,desc';
        break;
    }

   // undefined for filters we are not using here, need to implement price sliders later
    this.productService.getAllProducts(
      this.searchTerm, // 1. name/search keyword
      undefined,       // 2. minPrice
      undefined,       // 3. maxPrice
      undefined,       // 4. category
      sortParam,       // 5. sort
      0,               // 6. page
      10               // 7. size
    ).subscribe({
      next: (data: { products: Product[]; total: number }) => {
        this.products = data.products;
      },
      error: (err: unknown) => console.error(err)
    });
  }
}
