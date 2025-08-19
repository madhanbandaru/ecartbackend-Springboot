package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.model.Category;
import com.example.demo.responce.ApiResponce;
import com.example.demo.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/category")
@Controller
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;
	@PostMapping("/add")
	public ResponseEntity<ApiResponce<Category>> addCategory(@RequestBody CategoryDTO category){
		ApiResponce<Category> added=categoryService.addCategory(category);
		if (added.isSuccess()) {
			return ResponseEntity.ok(added);
		}
		return ResponseEntity.badRequest().body(added);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponce<Category>> deleteCategory(@RequestParam String name){
		ApiResponce<Category> deleted=categoryService.deleteCategory(name);
		if(deleted.isSuccess()) {
			return ResponseEntity.ok(deleted);
		}
		else {
			return ResponseEntity.badRequest().body(deleted);
		}
	}
	@PutMapping("/update")
	public ResponseEntity<ApiResponce<Category>> update(@RequestBody CategoryDTO cartegory){
		ApiResponce<Category> cate=categoryService.updateCategory(cartegory);
		if (cate!=null) {
			return ResponseEntity.ok(cate);
		}
		else {
			return ResponseEntity.badRequest().body(cate);
		}
	}
}
