package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.PriceRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Image;
import com.example.demo.model.Price;
import com.example.demo.model.Product;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.ImageResponce;
import com.example.demo.responce.PriceResponse;
import com.example.demo.responce.ProductResponce;
import com.example.demo.serviceIntefaces.ProductInteface;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService implements ProductInteface {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final PriceRepository priceRepository;
	private final ImageRepository imageRepository;

	@Override
	public List<ProductResponce> findAllProducts() {
		return productRepository.findAll().stream()
				.map(prod -> ProductResponce.builder().id(prod.getId()).name(prod.getName())
						.description(prod.getDescription()).category(prod.getCategory().getName())
						.price(prod.getPrice().stream()
								.map(pri -> PriceResponse.builder().price(pri.getPrice())
										.description(pri.getDescription()).id(pri.getId()).build())
								.toList())
						.image(prod.getImages().stream()
								.map(im -> ImageResponce.builder().url(im.getUrl()).id(im.getId()).build()).toList())
						.build())
				.toList();
	}

	@Override
	@Transactional
	public ApiResponce<Product> addProducts(ProductDTO product) {
		try {
			Category category = categoryRepository.findByName(product.getCategory());
			if (category == null) {
				category = Category.builder().name(product.getCategory()).build();
				categoryRepository.save(category);
			}

			Product prod = Product.builder().category(category).description(product.getDescription())
					.name(product.getName()).build();
			List<Image> img = product.getImage();
			if (img != null) {
				img.forEach(i -> i.setProduct(prod)); // set product reference
				prod.setImages(img);
			}
			List<Price> pri = product.getPrice();
			if (pri != null) {
				pri.forEach(i -> i.setProduct(prod));
				prod.setPrice(pri);
			}
			Product saved = productRepository.save(prod);
			return new ApiResponce<>(true, "product is added", saved);
		} catch (Exception e) {
			return new ApiResponce<>(false, "failed to add product " + e.getMessage(), null);
		}
	}

	@Override
	@Transactional
	public ApiResponce<Product> updateProduct(ProductDTO product) {
		Product prod = productRepository.findById(product.getId())
				.orElseThrow(() -> new RuntimeException("there is no product"));
		prod.setDescription(product.getDescription());
		prod.setName(product.getName());

		if (product.getCategory() != null) {
			Category category = categoryRepository.findByName(product.getCategory());
			if (category == null) {
				category = categoryRepository.save(Category.builder().name(product.getCategory()).build());
			}
			prod.setCategory(category);
		}
		if (product.getImage() != null) {
			imageRepository.deleteAllByProductId(prod.getId());
			prod.getImages().clear();
			for (Image img : product.getImage()) {
				Image ima = new Image();
				ima.setUrl(img.getUrl());
				ima.setProduct(prod);
				prod.getImages().add(ima);
			}
		}
		if (product.getPrice() != null) {
			priceRepository.deleteAllByProductId(prod.getId());
			prod.getPrice().clear();
			for (Price priceDto : product.getPrice()) {
				Price price = new Price();
				price.setDescription(priceDto.getDescription());
				price.setPrice(priceDto.getPrice());
				price.setProduct(prod);
				prod.getPrice().add(price);
			}
		}
		Product saved = productRepository.save(prod);
		return new ApiResponce<>(true, "Product updated successfully", saved);
	}

	@Override
	@Transactional
	public ApiResponce<Product> deleteProduct(ProductDTO prod) {
		try {

			productRepository.deleteById(prod.getId());
			return new ApiResponce<>(true, "product is deleted", null);
		} catch (Exception e) {
			return new ApiResponce<>(false, "product is not deleted", null);
		}

	}

	@Override
	public ApiResponce<ProductResponce> getProductById(Long id) {
		try {
			Product prod = productRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("there is not product by this id:" + id));
			ProductResponce responce = ProductResponce.builder().id(prod.getId()).name(prod.getName())
					.description(prod.getDescription()).category(prod.getCategory().getName())
					.price(prod.getPrice().stream()
							.map(pri -> PriceResponse.builder().price(pri.getPrice()).description(pri.getDescription())
									.id(pri.getId()).build())
							.toList())
					.image(prod.getImages().stream()
							.map(im -> ImageResponce.builder().url(im.getUrl()).id(im.getId()).build()).toList())
					.build();

			return new ApiResponce<>(true, "product with id " + id, responce);
		} catch (Exception e) {
			return new ApiResponce<>(false, "error: " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<ProductResponce> getProductCompletedetailsById(Long id) {
		try {
			Product prod = productRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("there is not product by this id:" + id));
			ProductResponce responce = ProductResponce.builder().id(prod.getId()).name(prod.getName())
					.description(prod.getDescription()).category(prod.getCategory().getName()).price(
							prod.getPrice().stream()
									.map(pri -> PriceResponse.builder().price(pri.getPrice()).id(pri.getId())
											.description(pri.getDescription()).id(pri.getId()).build())
									.toList())
					.image(prod.getImages().stream().map(im -> ImageResponce.builder().url(im.getUrl()).id(im.getId())
							.product(im.getProduct()).build()).toList())
					.build();

			return new ApiResponce<>(true, "product with id " + id, responce);
		} catch (Exception e) {
			return new ApiResponce<>(false, "error: " + e.getMessage(), null);
		}
	}

	public List<ProductResponce> getProductsByCategory(String category) {
		Category cat = categoryRepository.findByName(category);
		List<ProductResponce> prod = productRepository.findByCategory(cat).stream().map(p -> ProductResponce.builder()
				.category(p.getCategory().getName()).id(p.getId()).description(p.getDescription())
				.price(p.getPrice().stream()
						.map(pri -> PriceResponse.builder().price(pri.getPrice()).description(pri.getDescription())
								.build())
						.toList())
				.image(p.getImages().stream().map(i -> ImageResponce.builder().url(i.getUrl()).build()).toList())
				.name(p.getName()).build()).toList();
		return prod;

	}
}
