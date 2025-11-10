package com.buy01.product.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MediaClient {

    private final RestTemplate restTemplate;
    private final String mediaServiceBaseUrl;

    public MediaClient() {
        this.restTemplate = new RestTemplate();
        // Local dev URL; will switch to container name in Docker
        this.mediaServiceBaseUrl = "http://localhost:8082/api/media";
    }

    public List<String> getProductImageIds(String productId) {
        String url = mediaServiceBaseUrl + "/internal/images/productId/" + productId;

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // no headers needed for internal calls
                List.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to get images: " + response.getStatusCode());
        }

        return response.getBody();
    }
}
