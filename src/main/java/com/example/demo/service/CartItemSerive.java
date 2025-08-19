package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.CartItemRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.CartItemInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemSerive implements CartItemInterface {
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;
	@Override
	@Transactional
	public ApiResponce<CartItem> addItemToCart(Long cartId, Long productId, int quantity) {
	    try {
	        Cart cart = cartRepository.findById(cartId)
	                .orElseThrow(() -> new RuntimeException("Cart not found with id " + cartId));
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
	        CartItem existingItem = cart.getItems().stream()
	                .filter(item -> item.getProduct().getId().equals(productId))
	                .findFirst()
	                .orElse(null);
	        CartItem savedItem;
	        if (existingItem != null) {
	            existingItem.setQuantity(existingItem.getQuantity() + quantity);
	            savedItem = cartItemRepository.save(existingItem);
	        } else {
	            CartItem newItem = CartItem.builder()
	                    .cart(cart)
	                    .product(product)
	                    .quantity(quantity)
	                    .build();
	            savedItem = cartItemRepository.save(newItem);
	            cart.getItems().add(savedItem);
	        }
	        cartRepository.save(cart);
	        return new ApiResponce<>(true, "Item added to cart successfully", savedItem);
	    } catch (Exception e) {
	        return new ApiResponce<>(false, "Error adding item to cart: " + e.getMessage(), null);
	    }
	}
	@Override
	@Transactional
	public ApiResponce<CartItem> updateCartItemQuantity(Long cartItemId, int quantity) {
	    try {
	        // 1. Find the cart item
	        CartItem cartItem = cartItemRepository.findById(cartItemId)
	                .orElseThrow(() -> new RuntimeException("CartItem not found with id " + cartItemId));

	        // 2. Update the quantity
	        cartItem.setQuantity(quantity);

	        // 3. Save updated cart item
	        CartItem updatedItem = cartItemRepository.save(cartItem);

	        return new ApiResponce<>(true, "Cart item quantity updated successfully", updatedItem);
	    } catch (Exception e) {
	        return new ApiResponce<>(false, "Error updating cart item: " + e.getMessage(), null);
	    }
	}


	@Override
	@Transactional
	public ApiResponce<String> removeCartItem(Long cartItemId) {
	    try {
	        CartItem cartItem = cartItemRepository.findById(cartItemId)
	                .orElseThrow(() -> new RuntimeException("CartItem not found with id " + cartItemId));

	        cartItemRepository.delete(cartItem);

	        return new ApiResponce<>(true, "Cart item removed successfully", null);
	    } catch (Exception e) {
	        return new ApiResponce<>(false, "Error removing cart item: " + e.getMessage(), null);
	    }
	}


	@Override
	public List<CartItem> getCartItems(Long cartId) {
	    try {
	        Cart cart = cartRepository.findById(cartId)
	                .orElseThrow(() -> new RuntimeException("Cart not found with id " + cartId));
	        return cart.getItems();  
	    } catch (Exception e) {
	        throw new RuntimeException("Error fetching cart items: " + e.getMessage());
	    }
	}


}
