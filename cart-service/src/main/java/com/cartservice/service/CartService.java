package com.cartservice.service;
import com.cartservice.client.ProductClient;
import com.cartservice.client.UserClient;
import com.cartservice.dto.CartItemRequest;
import com.cartservice.exception.ProductNotFoundException;
import com.cartservice.model.Cart;
import com.cartservice.model.CartItem;
import com.cartservice.dto.Product;
import com.cartservice.repository.CartItemRepository;
import com.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final ProductClient productClient;
    @Autowired
    private final UserClient userClient;


    public CartService(CartRepository cartRepository , CartItemRepository cartItemRepository, ProductClient productClient, UserClient userClient ) {
        super();
        this.cartRepository=cartRepository;
        this.cartItemRepository=cartItemRepository;
        this.productClient=productClient;
        this.userClient=userClient;
    }

    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart=new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
    }

    public void addItemToCart(Integer userId, CartItemRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart=new Cart();
                    newCart.setUserId(userId);
                    return newCart;
                });
        Product product = productClient.getProductById(request.getProductId());
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            item.setSubTotal(item.getQuantity() * product.getPrice());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            newItem.setUnitPrice(product.getPrice());
            newItem.setSubTotal(request.getQuantity() * product.getPrice());
            newItem.setCart(cart);
            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);

        }
        cart.setTotalPrice(cart.getItems().stream().mapToDouble(item -> item.getSubTotal()).sum());
        cartRepository.save(cart);
    }


    public Cart removeProductFromCart(Integer userId, Integer productId) {

        Cart cart = getCartByUserId(userId);

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        cart.setTotalPrice(cart.getItems().stream().mapToDouble(item -> item.getSubTotal()).sum());
        return cartRepository.save(cart);

    }

    public Cart updateProductQuantity(Integer userId, Integer productId, Double quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not in cart"));

        cartItem.setQuantity(quantity);
        cart.setTotalPrice(cart.getItems().stream().mapToDouble(item -> item.getSubTotal()).sum());
        cartRepository.save(cart);
        return cart;
    }

    public void clearCart(Integer userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}
