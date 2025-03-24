package com.cartservice.dto;

import lombok.Data;

@Data
public class CartItemResponse {

    private Long id;
    private Long productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
