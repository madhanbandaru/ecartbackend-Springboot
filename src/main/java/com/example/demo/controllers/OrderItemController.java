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

import com.example.demo.model.OrderItem;
import com.example.demo.responce.ApiResponce;
import com.example.demo.service.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {
    private OrderItemService orderItemService;
    @PostMapping("/add")
    public ApiResponce<OrderItem> addOrderItem(
            @RequestParam Long orderId,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam double price) {
        return orderItemService.addOrderItem(orderId, productId, quantity, price);
    }
    @GetMapping("/order/{orderId}")
    public List<OrderItem> getItemsByOrder(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrder(orderId);
    }
    @PutMapping("/update-quantity/{orderItemId}")
    public ApiResponce<OrderItem> updateOrderItemQuantity(
            @PathVariable Long orderItemId,
            @RequestParam int quantity) {
        return orderItemService.updateOrderItemQuantity(orderItemId, quantity);
    }
    @DeleteMapping("/remove/{orderItemId}")
    public ApiResponce<String> removeOrderItem(@PathVariable Long orderItemId) {
        return orderItemService.removeOrderItem(orderItemId);
    }
}
