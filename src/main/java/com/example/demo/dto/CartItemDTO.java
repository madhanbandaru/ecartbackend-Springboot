package com.example.demo.dto;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
	private Long id;
	private Cart cart;
	private Product product;
	private int quantity;
}
