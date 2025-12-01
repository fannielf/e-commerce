package com.buy01.product.controller;

import com.buy01.product.dto.ProductResponseDTO;
import com.buy01.product.exception.NotFoundException;
import com.buy01.product.model.Product;
import com.buy01.product.security.SecurityUtils;
import com.buy01.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private SecurityUtils securityUtils;

    static class TestProduct extends Product {
        public TestProduct(String productId, String name, String description, double price, int quantity, String userId) {
            super(productId, name, description, price, quantity, userId);
        }
    }

    // -- POST /api/products TESTS --
    @Test
    void testCreateProduct() throws Exception {
        ProductResponseDTO mockResponse = new ProductResponseDTO(
                "p1", "Prod", "desc", 12.0, 10, "u1", List.of(), true
        );

        when(securityUtils.getCurrentUserId(anyString())).thenReturn("u1");
        when(securityUtils.getRole(anyString())).thenReturn("SELLER");
        when(productService.createProduct(any(), anyString(), anyString()))
                .thenReturn(mockResponse);

        mockMvc.perform(
                        multipart("/api/products")
                                .header("Authorization", "token-123")
                                .param("name", "Prod")
                                .param("description", "desc")
                                .param("price", "12.0")
                                .param("quantity", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("p1"))
                .andExpect(jsonPath("$.name").value("Prod"));
    }

    @Test
    void testCreateProductInvalidInput() throws Exception {
        mockMvc.perform(
                        multipart("/api/products")
                                .header("Authorization", "token-123")
                                .param("name", "") // name cannot be empty
                                .param("description", "desc")
                                .param("price", "-5") // negative price
                                .param("quantity", "0")
                )
                .andExpect(status().isBadRequest());
    }


    // -- GET /api/products TEST --
    @Test
    void testGetAllProducts() throws Exception {
        TestProduct p = new TestProduct("p1", "Prod", "desc", 10, 3, "u1");
        when(productService.getAllProducts()).thenReturn(List.of(p));
        when(productService.getProductImageIds("p1")).thenReturn(List.of("img-1"));

        mockMvc.perform(
                        get("/api/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("p1"))
                .andExpect(jsonPath("$[0].images[0]").value("img-1"));
    }

    @Test
    void testGetAllProducts_noProducts() throws Exception {
        // No products
        when(productService.getAllProducts()).thenReturn(List.of());

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // -- GET /api/products/{productId} TEST --
    @Test
    void testGetProductById() throws Exception {
        TestProduct p = new TestProduct("p1", "Prod", "desc", 10, 3, "u1");
        when(productService.getProductById("p1")).thenReturn(p);
        when(productService.getProductImageIds("p1")).thenReturn(List.of());

        mockMvc.perform(
                        get("/api/products/p1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("p1"))
                .andExpect(jsonPath("$.name").value("Prod"));
    }

    // GET /api/products/my-products TEST --
    @Test
    void testGetMyProducts() throws Exception {
        String authHeader = "Bearer fake-token";
        String userId = "user123";
        String role  = "SELLER";

        // mock current user id retrieval
        when(securityUtils.getCurrentUserId(authHeader)).thenReturn(userId);
        when(securityUtils.getRole(authHeader)).thenReturn(role);

        // mock products from service
        TestProduct p1 = new TestProduct("p1", "Product 1", "Desc 1", 10.0, 5, userId); // own product
        TestProduct p2 = new TestProduct("p2", "Product 2", "Desc 2", 20.0, 3, "otherUser"); // someone else's product

        // Service mocks
        when(productService.getAllProductsByUserId(userId, role, userId))
                .thenReturn(List.of(
                        new ProductResponseDTO("p1", "Product 1", "Desc 1", 10.0, 5, userId, null, true)
                ));

        mockMvc.perform(get("/api/products/my-products")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].productId").value("p1"))
                .andExpect(jsonPath("$[0].isProductOwner").value(true));

        // verify service calls
        verify(productService).getAllProductsByUserId(userId, role, userId);

    }

    // -- PUT /api/products/{productId} TESTS --
    @Test
    void testUpdateProduct() throws Exception {
        ProductResponseDTO updated = new ProductResponseDTO(
                "p1", "Updated", "desc2", 20, 5, "u1", List.of(), true
        );

        when(securityUtils.getCurrentUserId(anyString())).thenReturn("u1");
        when(securityUtils.getRole(anyString())).thenReturn("SELLER");
        when(productService.updateProduct(eq("p1"), any(), eq("u1"), eq("SELLER")))
                .thenReturn(updated);

        mockMvc.perform(
                        multipart("/api/products/p1")
                                .with(request -> { request.setMethod("PUT"); return request; })
                                .header("Authorization", "token-123")
                                .param("name", "Updated")
                                .param("description", "desc2")
                                .param("price", "20")
                                .param("quantity", "5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }


    // -- DELETE /api/products/{productId} TESTS --
    @Test
    void testDeleteProduct() throws Exception {
        when(securityUtils.getCurrentUserId("token-123")).thenReturn("u1");
        when(securityUtils.getRole("u1")).thenReturn("SELLER");

        mockMvc.perform(
                        delete("/api/products/p1")
                                .header("Authorization", "token-123")
                )
                .andExpect(status().isOk());
    }
}
