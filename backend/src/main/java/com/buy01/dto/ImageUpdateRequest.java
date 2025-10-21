package com.buy01.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageUpdateRequest {
    private MultipartFile file; // actual file
    private String productId;

    public ImageUpdateRequest() {}
    public ImageUpdateRequest(MultipartFile file, String productId) {}

    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }

    public String getProductId() { return this.productId; }
    public void setProductId(String productId) { this.productId = productId; }
}
