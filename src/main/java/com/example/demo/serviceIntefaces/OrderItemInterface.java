package com.example.demo.serviceIntefaces;

import java.util.List;

import com.example.demo.model.OrderItem;
import com.example.demo.responce.ApiResponce;

public interface OrderItemInterface {
	List<OrderItem> getItemsByOrder(Long orderId);
	ApiResponce<String> removeOrderItem(Long orderItemId);

	ApiResponce<OrderItem> updateOrderItemQuantity(Long orderItemId, int quantity);

	ApiResponce<OrderItem> addOrderItem(Long orderId, Long productId, Integer quantity, Double price);

}
