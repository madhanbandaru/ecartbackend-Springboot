package com.example.demo.serviceIntefaces;

import com.example.demo.model.Image;
import com.example.demo.responce.ApiResponce;

public interface ImageInterface {
	ApiResponce<Image> updateImagelink(String oldUrl,String newUrl);
	ApiResponce<Image> deleteImage(String url);
	ApiResponce<Image> updateImageById(Long id, String url);
	
}
