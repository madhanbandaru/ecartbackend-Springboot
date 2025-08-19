package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.CartInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements CartInterface {
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	@Override
	@Transactional
	public ApiResponce<Cart> addToCart(Long userId, Long productId, int quantity) {
		try {
			User user = userRepository.findById(productId).orElseThrow(() -> new RuntimeException("user not found "));
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new RuntimeException("produt  not found "));
			Cart cart = cartRepository.findByUserId(userId);
			if (cart == null) {
				cart.setItems(new ArrayList()) ;
			}
			CartItem existingItem = null;
			for (CartItem item : cart.getItems()) {
				if (item.getProduct().getId().equals(productId)) {
					existingItem = item;
					break;
				}
			}
			if (existingItem != null) {
				existingItem.setQuantity(existingItem.getQuantity() + quantity);
			} else {
				CartItem newItem = CartItem.builder().product(product).quantity(quantity).cart(cart).build();

				cart.getItems().add(newItem);
			}
			Cart savedCart = cartRepository.save(cart);

			return new ApiResponce<Cart>(true, "Product added to cart", savedCart);

		} catch (Exception e) {
			return new ApiResponce<Cart>(false, "Failed to add product: " + e.getMessage(), null);
		}
	}

	@Override
	@Transactional
	public ApiResponce<Cart> removeFromCart(Long userId, Long productId) {
		try {
	        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	        Cart cart = cartRepository.findByUserId(userId);
	        if (cart == null) {
	            return new ApiResponce<>(false, "Cart not found", null);
	        }
	        CartItem itemToRemove = null;
	        for (CartItem item : cart.getItems()) {
	            if (item.getProduct().getId().equals(productId)) {
	                itemToRemove = item;
	                break;
	            }
	        }

	        if (itemToRemove == null) {
	            return new ApiResponce<>(false, "Product not found in cart", null);
	        }
	        cart.getItems().remove(itemToRemove);
	        Cart updatedCart = cartRepository.save(cart);
	        return new ApiResponce<>(true, "Product removed from cart", updatedCart);
	    } catch (Exception e) {
	        return new ApiResponce<>(false, "Failed to remove from cart: " + e.getMessage(), null);
	    }
	}
	@Override
	@Transactional(readOnly = true)
	public ApiResponce<Cart> getCartByUser(Long userId) {
	    try {
	        // 1. Find cart by userId
	        Cart cart = cartRepository.findByUserId(userId);

	        // 2. If cart not found, throw exception (instead of Optional)
	        if (cart == null) {
	            throw new RuntimeException("Cart not found for userId: " + userId);
	        }

	        // 3. Return successful response
	        return new ApiResponce<>(true, "Cart found", cart);

	    } catch (Exception e) {
	        // 4. Handle exception
	        return new ApiResponce<>(false, "Error: " + e.getMessage(), null);
	    }
	}
	@Override
	@Transactional
	public ApiResponce<Cart> clearCart(Long userId) {
	    try {
	        Cart cart = cartRepository.findByUserId(userId);
	        if (cart == null) {
	            throw new RuntimeException("Cart not found for userId: " + userId);
	        }
	        cart.getItems().clear();
	        Cart updatedCart = cartRepository.save(cart);
	        return new ApiResponce<>(true, "Cart cleared successfully", updatedCart);
	    } catch (Exception e) {
	        return new ApiResponce<>(false, "Error clearing cart: " + e.getMessage(), null);
	    }
	}


}
