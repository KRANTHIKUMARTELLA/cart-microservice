package com.cartservice.dto;

import lombok.Data;

@Data
public class CartItemRequest {

    private Integer productId;
    private Double quantity;

}
