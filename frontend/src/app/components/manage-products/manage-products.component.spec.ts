import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ManageProductsComponent } from './manage-products.component';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { of, throwError } from 'rxjs';
import { Product, Category } from '../../models/product.model';
import { User } from '../../models/user.model';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('ManageProductsComponent', () => {
  let component: ManageProductsComponent;
  let fixture: ComponentFixture<ManageProductsComponent>;
  let productServiceSpy: jasmine.SpyObj<ProductService>;
  let userServiceSpy: jasmine.SpyObj<UserService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let dialogSpy: jasmine.SpyObj<MatDialog>;

  const mockProduct: Product = {
    productId: 'prod-123',
    name: 'Test Product',
    description: 'A test description',
    price: 10,
    quantity: 5,
    category: Category.OTHER,
    userId: 'user-1',
    images: ['image1.jpg'],
  };

  const mockUser = {
    name: 'tester',
    email: 'test@example.com',
    role: 'user',
    ownProfile: true,
    products: [mockProduct],
    avatar: 'avatar.jpg'
  };

  // Helper function to configure TestBed with different route params
  const setupTestBed = async (productId: string | null) => {
    productServiceSpy = jasmine.createSpyObj('ProductService', [
      'getProductById',
      'createProduct',
      'updateProduct',
      'deleteProduct',
    ]);
    userServiceSpy = jasmine.createSpyObj('UserService', ['getMe']);
    
    // Mock Router with properties needed by RouterLink
    routerSpy = jasmine.createSpyObj('Router', ['navigate', 'createUrlTree', 'serializeUrl']);
    routerSpy.createUrlTree.and.returnValue({} as any);
    routerSpy.serializeUrl.and.returnValue('mock-url');
    Object.defineProperty(routerSpy, 'events', { get: () => of(null) }); // Mock events observable
    Object.defineProperty(routerSpy, 'url', { get: () => '/mock-url' }); // Mock url property

    dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);

    await TestBed.configureTestingModule({
      imports: [ManageProductsComponent, NoopAnimationsModule],
      providers: [
        { provide: ProductService, useValue: productServiceSpy },
        { provide: UserService, useValue: userServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: MatDialog, useValue: dialogSpy },
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap(productId ? { id: productId } : {})),
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ManageProductsComponent);
    component = fixture.componentInstance;
  };

  describe('in Create Mode', () => {
    beforeEach(async () => {
      await setupTestBed(null);
      // Mock getMe to return user so loadMyProducts works
      userServiceSpy.getMe.and.returnValue(of(mockUser));
    });

    it('should initialize successfully in create mode', fakeAsync(() => {
      fixture.detectChanges(); // triggers ngOnInit
      tick();

      expect(component.mode).toBe('create');
      expect(component.productId).toBe('');
      expect(userServiceSpy.getMe).toHaveBeenCalled();
      expect(productServiceSpy.getProductById).not.toHaveBeenCalled();
      expect(component.productForm).toBeDefined();
    }));

    it('should submit valid form data to createProduct', () => {
      fixture.detectChanges();
      productServiceSpy.createProduct.and.returnValue(of(mockProduct));
      
      component.productForm.setValue({
        name: 'New Product',
        description: 'Description',
        price: 100,
        quantity: 10,
        category: Category.ELECTRONICS 
      });
      
      component.submit();

      expect(productServiceSpy.createProduct).toHaveBeenCalled();
    });

    it('should not submit if form is invalid', () => {
       fixture.detectChanges();
       component.productForm.setValue({
         name: '', // Invalid
         description: '',
         price: null,
         quantity: -1,
         category: null
       });
       
       component.submit();
       
       expect(productServiceSpy.createProduct).not.toHaveBeenCalled();
    });
  });

  describe('in Update Mode', () => {
    beforeEach(async () => {
      await setupTestBed('prod-123');
      userServiceSpy.getMe.and.returnValue(of(mockUser));
      productServiceSpy.getProductById.and.returnValue(of(mockProduct));
    });

    it('should initialize in update mode and load product data', fakeAsync(() => {
      fixture.detectChanges(); // triggers ngOnInit
      tick();

      expect(component.mode).toBe('update');
      expect(component.productId).toBe('prod-123');
      expect(productServiceSpy.getProductById).toHaveBeenCalledWith('prod-123');
      expect(component.productForm.value.name).toBe(mockProduct.name);
    }));

    it('should submit valid form data to updateProduct', () => {
      fixture.detectChanges();
      productServiceSpy.updateProduct.and.returnValue(of(mockProduct));
      
      component.productForm.patchValue({
         name: 'Updated Name'
      });
      
      component.submit();

      expect(productServiceSpy.updateProduct).toHaveBeenCalledWith('prod-123', jasmine.any(FormData));
    });

    it('should call deleteProduct when onDelete is confirmed', () => {
      fixture.detectChanges();
      
      // Mock dialog open to return { afterClosed: () => of(true) }
      const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed: of(true), close: null });
      dialogSpy.open.and.returnValue(dialogRefSpyObj);
      
      productServiceSpy.deleteProduct.and.returnValue(of(void 0));
      
      component.onDelete();
      
      expect(dialogSpy.open).toHaveBeenCalled();
      expect(productServiceSpy.deleteProduct).toHaveBeenCalledWith('prod-123');
    });

     it('should NOT call deleteProduct when onDelete is cancelled', () => {
      fixture.detectChanges();
      
      // Mock dialog open to return { afterClosed: () => of(false) }
      const dialogRefSpyObj = jasmine.createSpyObj({ afterClosed: of(false), close: null });
      dialogSpy.open.and.returnValue(dialogRefSpyObj);
      
      component.onDelete();
      
      expect(dialogSpy.open).toHaveBeenCalled();
      expect(productServiceSpy.deleteProduct).not.toHaveBeenCalled();
    });
  });
});
