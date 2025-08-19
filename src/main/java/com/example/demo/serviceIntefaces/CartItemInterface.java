package com.example.demo.serviceIntefaces;

import java.util.List;

import com.example.demo.model.CartItem;
import com.example.demo.responce.ApiResponce;

public interface CartItemInterface {
	ApiResponce<CartItem> addItemToCart(Long cartId, Long productId, int quantity);

	ApiResponce<CartItem> updateCartItemQuantity(Long cartItemId, int quantity);

	ApiResponce<String> removeCartItem(Long cartItemId);

	List<CartItem> getCartItems(Long cartId);
}
