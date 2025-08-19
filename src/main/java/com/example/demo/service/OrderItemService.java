package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Product;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.OrderItemInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService implements OrderItemInterface {
	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	@Override
	public List<OrderItem> getItemsByOrder(Long orderId) {
		return orderItemRepository.findByOrderId(orderId);
	}
	@Override
	@Transactional
	public ApiResponce<OrderItem> addOrderItem(Long orderId, Long productId, Integer quantity, Double price) {
		try {
			Order order = orderRepository.findById(orderId)
					.orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
			Product product = productRepository.findById(productId)
					.orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
			OrderItem orderItem = OrderItem.builder().order(order).product(product).quantity(quantity)
					.priceAtPurchase(price).build();
			OrderItem savedItem = orderItemRepository.save(orderItem);
			double total = order.getItems().stream().mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
					.sum();
			order.setTotalPrice(total);
			orderRepository.save(order);
			return new ApiResponce<>(true, "Order item added successfully", savedItem);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Error adding order item: " + e.getMessage(), null);
		}
	}
	@Override
	@Transactional
	public ApiResponce<String> removeOrderItem(Long orderItemId) {
		try {
			OrderItem orderItem = orderItemRepository.findById(orderItemId)
					.orElseThrow(() -> new RuntimeException("OrderItem not found with id " + orderItemId));
			Order order = orderItem.getOrder();
			order.getItems().remove(orderItem);
			orderItemRepository.delete(orderItem);
			if (order.getItems() != null && !order.getItems().isEmpty()) {
				double total = order.getItems().stream()
						.mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity()).sum();
				order.setTotalPrice(total);
			} else {
				order.setTotalPrice(0.0);
			}
			orderRepository.save(order);
			return new ApiResponce<>(true, "Order item removed successfully", null);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Error removing order item: " + e.getMessage(), null);
		}
	}
	@Override
	@Transactional
	public ApiResponce<OrderItem> updateOrderItemQuantity(Long orderItemId, int quantity) {
		try {
			OrderItem orderItem = orderItemRepository.findById(orderItemId)
					.orElseThrow(() -> new RuntimeException("OrderItem not found with id " + orderItemId));
			orderItem.setQuantity(quantity);
			OrderItem updatedItem = orderItemRepository.save(orderItem);
			Order order = orderItem.getOrder();
			if (order != null && order.getItems() != null) {
				double total = order.getItems().stream()
						.mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity()).sum();
				order.setTotalPrice(total);
				orderRepository.save(order);
			}
			return new ApiResponce<>(true, "Order item quantity updated successfully", updatedItem);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Error updating order item quantity: " + e.getMessage(), null);
		}
	}
}
