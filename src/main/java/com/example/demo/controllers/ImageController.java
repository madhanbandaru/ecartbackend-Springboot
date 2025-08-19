package com.example.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ImageDTO;
import com.example.demo.model.Image;
import com.example.demo.responce.ApiResponce;
import com.example.demo.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
	private final ImageService imageService;

	@PostMapping("/update")
	public ResponseEntity<ApiResponce<Image>> updateImage(@RequestBody ImageDTO im) {
		ApiResponce<Image> img = imageService.updateImageById(im.getId(), im.getUrl());
		if (img != null) {
			return ResponseEntity.ok(img);
		} else {
			return ResponseEntity.badRequest().body(img);
		}
	}

	@PostMapping("/delete/{url}")
	public ResponseEntity<ApiResponce<Image>> deleteimg(@PathVariable String url) {
		ApiResponce<Image> img = imageService.deleteImage(url);
		if (img != null) {
			return ResponseEntity.ok(img);
		} else {
			return ResponseEntity.badRequest().body(img);
		}
	}
	@PutMapping("/updateimg/{oldurl}&{newUrl}")
	public ResponseEntity<ApiResponce<Image>> updateImagelink(@PathVariable String oldurl,@PathVariable String newUrl ) {
		ApiResponce<Image> img = imageService.updateImagelink(oldurl,newUrl);
		if (img != null) {
			return ResponseEntity.ok(img);
		} else {
			return ResponseEntity.badRequest().body(img);
		}
	}

}
