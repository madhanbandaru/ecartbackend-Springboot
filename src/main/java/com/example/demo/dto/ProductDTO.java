package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Image;
import com.example.demo.model.Price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private String category;
	private List<Image>Image;
	private List<Price>price;
}	
