//package com.buy01.product.service;
//
//import com.buy01.product.client.MediaClient;
//import com.buy01.product.client.UserClient;
//import com.buy01.product.dto.ProductCreateDTO;
//import com.buy01.product.dto.ProductResponseDTO;
//import com.buy01.product.dto.ProductUpdateRequest;
//import com.buy01.product.exception.ForbiddenException;
//import com.buy01.product.exception.NotFoundException;
//import com.buy01.product.model.Product;
//import com.buy01.product.repository.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private MediaClient mediaClient;
//
//    @Mock
//    private UserClient userClient;
//
//    @Mock
//    private ProductEventService productEventService;
//
//    @InjectMocks
//    private ProductService productService;
//
//    static class TestProduct extends Product {
//        TestProduct(String productId, String name, String description, double price, int quantity, String userId) {
//            super(productId, name, description, price, quantity, userId);
//        }
//    }
//
//    @BeforeEach
//    void setUp() {
//        // productService is created by @InjectMocks with all mocks injected
//    }
//
//    @Test
//    void createProduct_validRequest_returnsProductResponseDTO() throws IOException {
//        // Arrange
//        ProductCreateDTO request = mock(ProductCreateDTO.class);
//        when(request.getName()).thenReturn("Valid Name");
//        when(request.getDescription()).thenReturn("A valid description");
//        when(request.getPrice()).thenReturn(9.99);
//        when(request.getQuantity()).thenReturn(5);
//        when(request.getUserId()).thenReturn(""); // use currentUserId
//        when(request.getImagesList()).thenReturn(null);
//
//        // mock repository save to return product with id
//        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
//            Product p = invocation.getArgument(0);
//            return new TestProduct("prod-1", p.getName(), p.getDescription(), p.getPrice(), p.getQuantity(), p.getUserId());
//        });
//
//        // Act
//        ProductResponseDTO resp = productService.createProduct(request, "SELLER", "current-user-1");
//
//        // Assert
//        assertNotNull(resp);
//        assertEquals("prod-1", resp.getProductId());
//        assertEquals("Valid Name", resp.getName());
//        // use existing backend DTO accessor
//        assertEquals("current-user-1", resp.getOwnerId());
//    }
//
//    @Test
//    void createProduct_forbiddenRole_throwsForbiddenException() {
//        ProductCreateDTO request = mock(ProductCreateDTO.class);
//        when(request.getName()).thenReturn("Valid Name");
//        when(request.getDescription()).thenReturn("desc");
//        when(request.getPrice()).thenReturn(1.0);
//        when(request.getQuantity()).thenReturn(1);
//        when(request.getUserId()).thenReturn("");
//
//        assertThrows(ForbiddenException.class, () ->
//                productService.createProduct(request, "BUYER", "some-user")
//        );
//    }
//
//    @Test
//    void getProductById_notFound_throwsNotFoundException() {
//        when(productRepository.findById("missing")).thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> productService.getProductById("missing"));
//    }
//
//    @Test
//    void updateProduct_ownerUpdates_success() throws IOException {
//        // Arrange
//        String productId = "prod-1";
//        Product existing = new TestProduct(productId, "Old", "old desc", 5.0, 2, "owner-1");
//        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
//
//        ProductUpdateRequest request = mock(ProductUpdateRequest.class);
//        when(request.getName()).thenReturn("New Name");
//        when(request.getDescription()).thenReturn("New desc");
//        when(request.getPrice()).thenReturn(10.0);
//        when(request.getQuantity()).thenReturn(3);
//        when(request.getDeletedImageIds()).thenReturn(List.of());
//        when(request.getImages()).thenReturn(List.of());
//
//        when(mediaClient.updateProductImages(eq(productId), anyList(), anyList())).thenReturn(List.of());
//        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
//            Product p = invocation.getArgument(0);
//            return new TestProduct(productId, p.getName(), p.getDescription(), p.getPrice(), p.getQuantity(), p.getUserId());
//        });
//
//        // Act
//        ProductResponseDTO resp = productService.updateProduct(productId, request, "owner-1", "SELLER");
//
//        // Assert
//        assertNotNull(resp);
//        assertEquals("New Name", resp.getName());
//        assertEquals(10.0, resp.getPrice());
//        // use existing backend boolean accessor
//        assertTrue(Boolean.TRUE.equals(resp.getIsProductOwner()));
//    }
//
//    @Test
//    void deleteProduct_ownerDeletes_callsRepositoryAndPublishesEvent() {
//        String productId = "prod-1";
//        Product existing = new TestProduct(productId, "Name", "desc", 1.0, 1, "owner-1");
//        when(productRepository.findById(productId)).thenReturn(Optional.of(existing));
//
//        productService.deleteProduct(productId, "owner-1", "SELLER");
//
//        verify(productRepository).deleteById(productId);
//        verify(productEventService).publishProductDeletedEvent(productId);
//    }
//}
