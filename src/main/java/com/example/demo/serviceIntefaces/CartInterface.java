package com.example.demo.serviceIntefaces;

import com.example.demo.model.Cart;
import com.example.demo.responce.ApiResponce;
public interface CartInterface {
	ApiResponce<Cart> addToCart(Long userId, Long productId, int quantity);
	ApiResponce<Cart> removeFromCart(Long userId, Long productId);
	ApiResponce<Cart> getCartByUser(Long userId);
	ApiResponce<Cart> clearCart(Long userId);
}
