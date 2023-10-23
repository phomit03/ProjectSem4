package com.example.eproject4.Service;

import com.example.eproject4.Entity.cart_order.Cart;
import com.example.eproject4.Repository.cart_order.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void saveCarts(List<Cart> carts) {
        cartRepository.saveAll(carts);
    }
}