package com.buy01.order.client;

import com.buy01.order.dto.ProductDTO;
import com.buy01.order.exception.NotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String productServiceBaseUrl;

    public ProductClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.productServiceBaseUrl = "http://product-service:8081/api/products"; // product-service URL
    }

    public ProductDTO getProductById(String productId) {
        try {
            return restTemplate.getForObject(
                    productServiceBaseUrl + "/" + productId,
                    ProductDTO.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("Product not found");
        }
    }
}
