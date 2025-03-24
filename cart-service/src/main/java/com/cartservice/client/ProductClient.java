package com.cartservice.client;

import com.cartservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service",url = "http://localhost:8087/product")
public interface ProductClient {
    @GetMapping("/get/{id}")
    Product getProductById(@PathVariable Integer id);
}

