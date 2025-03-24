package com.cartservice.controller;
import com.cartservice.dto.CartItemRequest;
import com.cartservice.model.Cart;
import com.cartservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        super();
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Integer userId) {
        logger.info("Fetching cart for user {}", userId);
        Cart cart = cartService.getCartByUserId(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemRequest> addProductToCart(@RequestParam Integer userId, @RequestBody CartItemRequest request) {
        logger.info("Adding product to cart for user {}", userId);
         cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        logger.info("Removing product from cart for user {}", userId);
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateProductQuantity(@RequestParam Integer userId, @RequestParam Integer productId, @RequestParam Double quantity) {
        logger.info("Updating product quantity in cart for user {}", userId);
        Cart cart = cartService.updateProductQuantity(userId, productId, quantity);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam Integer userId) {
        logger.info("Clearing cart for user {}", userId);
        cartService.clearCart(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
