package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.OrderStatus;
import com.example.demo.model.Order;
import com.example.demo.responce.ApiResponce;
import com.example.demo.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place/{userId}")
    public ApiResponce<Order> placeOrder(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }
    @GetMapping("/{orderId}")
    public ApiResponce<Order> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @PutMapping("/status/{orderId}")
    public ApiResponce<Order> updateStatus(@PathVariable Long orderId, 
                                           @RequestParam OrderStatus status) {
        return orderService.updateStatus(orderId, status);
    }
    @PutMapping("/cancel/{orderId}")
    public ApiResponce<Order> cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }
    @GetMapping("/status/{status}")
    public List<Order> getOrdersByStatus(@PathVariable OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }
}
