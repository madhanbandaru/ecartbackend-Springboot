package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.model.Category;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.CategoryInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryInterface {
	private final CategoryRepository categoryRepository;

	@Override
	public ApiResponce<Category> addCategory(CategoryDTO categroy) {
		try {
			byte[] imageBytes = null;
			if (categroy.getImage() != null && !categroy.getImage().isEmpty()) {
				imageBytes = categroy.getImage().getBytes();
			}
			Category added = categoryRepository
					.save(Category.builder().image(imageBytes).name(categroy.getName()).build());
			return new ApiResponce<>(true, "category is added", added);
		} catch (Exception e) {
			return new ApiResponce<>(false, "adding category is failed due to " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<Category> deleteCategory(String name) {
		try {
			Category category = categoryRepository.findByName(name);
			categoryRepository.deleteById(category.getId());
			return new ApiResponce<>(true, "deleted", category);
		} catch (Exception e) {
			return new ApiResponce<>(false, "category delete failed", null);
		}
	}

	@Override
	public ApiResponce<Category> updateCategory(CategoryDTO category) {
		try {
			Category cat = categoryRepository.findById(category.getId())
					.orElseThrow(() -> new RuntimeException("category not found"));
			cat.setName(category.getName());
			cat.setProducts(category.getProducts());
			if (category.getImage() != null) {
				cat.setImage(category.getImage().getBytes());
			}
			cat = categoryRepository.save(cat);
			return new ApiResponce<Category>(true, "updated category ", cat);
		} catch (Exception e) {
			return new ApiResponce<>(false, "update failed " + e.getMessage(), null);
		}
	}
}
