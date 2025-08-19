package com.example.demo.serviceIntefaces;

import java.util.List;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.Order;
import com.example.demo.responce.ApiResponce;

public interface OrderInterface {
	ApiResponce<Order> placeOrder(Long userId); 
	ApiResponce<Order> getOrderById(Long orderId);
    List<Order> getOrdersByUser(Long userId);
    List<Order> getAllOrders();
    ApiResponce<Order> updateStatus(Long orderId, OrderStatus status);
    ApiResponce<Order> cancelOrder(Long orderId);
    List<Order> getOrdersByStatus(OrderStatus status); 
}
