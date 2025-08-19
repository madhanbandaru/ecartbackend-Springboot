package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.responce.ApiResponce;
import com.example.demo.service.CartItemSerive;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	private CartService cartService;
	private CartItemSerive cartItemService;

	@PostMapping("/add")
	public ApiResponce<CartItem> addItemToCart(@RequestParam Long cartId, @RequestParam Long productId,
			@RequestParam int quantity) {
		return cartItemService.addItemToCart(cartId, productId, quantity);
	}

	@PutMapping("/update-quantity/{cartItemId}")
	public ApiResponce<CartItem> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
		return cartItemService.updateCartItemQuantity(cartItemId, quantity);
	}

	@DeleteMapping("/remove/{cartItemId}")
	public ApiResponce<String> removeCartItem(@PathVariable Long cartItemId) {
		return cartItemService.removeCartItem(cartItemId);
	}

	@GetMapping("/{cartId}/items")
	public List<CartItem> getCartItems(@PathVariable Long cartId) {
		return cartItemService.getCartItems(cartId);
	}

	@GetMapping("/user/{userId}")
	public ApiResponce<Cart> getCartByUser(@PathVariable Long userId) {
		return cartService.getCartByUser(userId);
	}

	@DeleteMapping("/clear/{userId}")
	public ApiResponce<Cart> clearCart(@PathVariable Long userId) {
		return cartService.clearCart(userId);
	}
}
