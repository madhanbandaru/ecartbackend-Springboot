package com.example.demo.serviceIntefaces;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.model.Category;
import com.example.demo.responce.ApiResponce;

public interface CategoryInterface {
	public ApiResponce<Category> addCategory(CategoryDTO categroy);

	ApiResponce<Category> deleteCategory(String name);
	ApiResponce<Category> updateCategory(CategoryDTO category);
}
