package com.example.demo.dto;

import com.example.demo.model.Order;
import com.example.demo.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
	private Long id;
	private int quantity;
	private double priceAtPurchase;
	private Order order;
	private Product product;
}
