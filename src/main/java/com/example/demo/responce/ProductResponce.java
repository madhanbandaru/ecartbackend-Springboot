package com.example.demo.responce;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponce {
	private Long id;
	private String name;
	private String description;
	private String category;
	private List<ImageResponce>image;
	private List<PriceResponse>price;
}