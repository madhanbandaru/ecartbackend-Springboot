package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.enums.OrderStatus;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.OrderInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderInterface {
	private final CartRepository cartRepository;
	private final OrderRepository orderRepository;

	@Override
	@Transactional
	public ApiResponce<Order> placeOrder(Long userId) {
		try {
			Cart cart = cartRepository.findByUserId(userId);
			if (cart == null || cart.getItems().isEmpty()) {
				return new ApiResponce<>(false, "Cart is empty. Cannot place order.", null);
			}
			Order order = Order.builder().user(cart.getUser()).status(OrderStatus.PENDING).build();
			for (CartItem cartItem : cart.getItems()) {
				OrderItem orderItem = OrderItem.builder().product(cartItem.getProduct())
						.quantity(cartItem.getQuantity()).order(order).build();
				order.getItems().add(orderItem);
			}
			Order savedOrder = orderRepository.save(order);
			cart.getItems().clear();
			cartRepository.save(cart);
			return new ApiResponce<>(true, "Order placed successfully", savedOrder);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Error placing order: " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<Order> getOrderById(Long orderId) {
		try {
			Order order = orderRepository.findById(orderId)
					.orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

			return new ApiResponce<>(true, "Order found successfully", order);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Error: " + e.getMessage(), null);
		}
	}

	@Override
	public List<Order> getOrdersByUser(Long userId) {
		try {
			List<Order> orders = orderRepository.findByUserId(userId);
			return orders;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Order> getAllOrders() {
		try {
			return orderRepository.findAll();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	@Override
	public ApiResponce<Order> updateStatus(Long orderId, OrderStatus status) {
		try {
			Order order = orderRepository.findById(orderId)
					.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
			order.setStatus(status);
			Order updatedOrder = orderRepository.save(order);
			return new ApiResponce<>(true, "Order status updated successfully", updatedOrder);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Failed to update order status: " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<Order> cancelOrder(Long orderId) {
		try {
			Order order = orderRepository.findById(orderId).orElse(null);

			if (order == null) {
				return new ApiResponce<>(false, "Order not found with id: " + orderId, null);
			}
			if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.SHIPPED) {
				return new ApiResponce<>(false, "Cannot cancel order once shipped or delivered", order);
			}

			order.setStatus(OrderStatus.CANCELLED);
			Order updatedOrder = orderRepository.save(order);

			return new ApiResponce<>(true, "Order cancelled successfully", updatedOrder);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Failed to cancel order: " + e.getMessage(), null);
		}
	}

	@Override
	public List<Order> getOrdersByStatus(OrderStatus status) {
		try {
			return orderRepository.findByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

}