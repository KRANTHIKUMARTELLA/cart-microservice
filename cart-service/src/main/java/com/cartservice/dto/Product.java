package com.cartservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer productId;
    private String productName;
    private Double price;
    private Double stockQuantity;
    private Integer catogeryId;
}
