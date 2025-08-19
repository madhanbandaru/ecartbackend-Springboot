package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.ProductResponce;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping("/findall")
	public List<ProductResponce> getAll() {
		return productService.findAllProducts();
	}

	@PostMapping("/add")
	@JsonManagedReference
	public ApiResponce<Product> addProduct(@RequestBody ProductDTO products) {
		return productService.addProducts(products);
	}

	@PutMapping("/update")
	@JsonManagedReference
	public ApiResponce<Product> updateProduct(@RequestBody ProductDTO products) {

		return productService.updateProduct(products);
	}

	@DeleteMapping("/delete")
	@JsonManagedReference
	public ApiResponce<Product> deleteProduct(@RequestBody ProductDTO products) {
		return productService.deleteProduct(products);
	}

	@GetMapping("/{id}")
	public ApiResponce<ProductResponce> getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	@GetMapping("/category/{category}")
	public List<ProductResponce> getProductByCategory(@PathVariable String category) {
	    return productService.getProductsByCategory(category);
	}
	@GetMapping("/detailed/{id}")
	public ApiResponce<ProductResponce> getProductCompleteById(@PathVariable Long id) {
		return productService.getProductCompletedetailsById(id);
	}
}
