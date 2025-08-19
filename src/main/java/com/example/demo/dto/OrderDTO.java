package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.OrderItem;
import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
	 private Long id;
	    private String status; // PENDING, CONFIRMED, SHIPPED, DELIVERED,CANCELED.
	    private LocalDateTime orderDate;
	    private User user;
	    private List<OrderItem> items;
}
