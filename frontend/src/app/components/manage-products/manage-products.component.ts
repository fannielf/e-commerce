import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-manage-products',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './manage-products.component.html',
  styleUrls: ['./manage-products.component.css']
})
export class ManageProductsComponent implements OnInit {
    mode: 'create' | 'update' = 'create'
    product: Product = this.getInitialProductState();
    productId: string = '';
    sellerProducts: Product[] = [];
    loading = false;
    error: string | null = null;

    constructor(
      private route: ActivatedRoute,
      private productService: ProductService,
      private router: Router
    ) {}

  ngOnInit() {
        // Subscribe to route changes to handle navigation between update and create
        this.route.paramMap.subscribe(params => {
          this.productId = params.get('id') || '';
          if (this.productId) {
            this.mode = 'update';
            this.productService.getProductById(this.productId).subscribe({
              next: (data: Product) => (this.product = data),
              error: (err: any) => console.error('Failed to load product for editing', err)
            });
          } else {
            // When no ID, reset to create mode
            this.mode = 'create';
            this.product = this.getInitialProductState();
          }
        });
        this.loadMyProducts();
      }

    private getInitialProductState(): Product {
          return {
            productId: '',
            name: '',
            description: '',
            price: 0,
            quantity: 0,
            ownerId: '',
            images: []
          };
        }

      loadMyProducts() {
          this.loading = true;
          this.error = null;
          this.productService.getMyProducts().subscribe({
            next: (list: Product[]) => {
              this.sellerProducts = list;
              this.loading = false;
            },
            error: (err: HttpErrorResponse) => {
              console.error('getMyProducts: error', err);
              if (err.status === 0) {
                this.error = 'Network error — backend not reachable';
              } else if (err.status === 401 || err.status === 403) {
                this.error = 'Not authorized — token missing or expired';
              } else {
                this.error = err.error?.message || 'Could not load your listings';
              }
              this.loading = false;
            }
          });
        }

      submit() {
           const action = this.mode === 'create'
             ? this.productService.createProduct(this.product)
             : this.productService.updateProduct(this.productId, this.product);

           action.subscribe({
             next: (createdOrUpdatedProduct) => {
               this.loadMyProducts(); // Refresh the list
               // After submit, reset to a clean create form
               this.router.navigate(['/products/manage']);
             },
             error: (err) => console.error('Submit failed', err)
           });
         }

    edit(p: Product) {
       this.router.navigate(['/products/update', p.productId]);
     }

    switchToCreateMode() {
          this.router.navigate(['/products/update']);
        }
}
