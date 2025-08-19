package com.example.demo.responce;

import com.example.demo.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponce {
	private Long id;
	private String url;
	
	private Product product;
}
