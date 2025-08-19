package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	private Category category;
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private List<Image> images;
	@OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private List<Price> price;
	@OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private List<CartItem> cartItem;
	@OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private List<OrderItem> orderItem;
}
