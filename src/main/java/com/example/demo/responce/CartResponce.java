package com.example.demo.responce;

import java.util.List;

import com.example.demo.model.CartItem;
import com.example.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponce {
	private Long id;
	private User user;
	private List<CartItem> items;
}
