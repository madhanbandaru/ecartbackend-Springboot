package com.example.demo.serviceIntefaces;

import java.util.List;

import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.ProductResponce;

public interface ProductInteface {
	public List<ProductResponce> findAllProducts();
	public ApiResponce<Product> addProducts(ProductDTO product);
	public ApiResponce<Product> updateProduct(ProductDTO product);
	public ApiResponce<Product> deleteProduct(ProductDTO product);
	public ApiResponce<ProductResponce> getProductById(Long id);
	public ApiResponce<ProductResponce> getProductCompletedetailsById(Long id);
}
