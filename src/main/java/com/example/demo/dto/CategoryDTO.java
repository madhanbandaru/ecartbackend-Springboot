package com.example.demo.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
	private Long id;
	private String name;
	private MultipartFile image;
	private List<Product> products;
}
