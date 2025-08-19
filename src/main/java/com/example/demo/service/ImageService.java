package com.example.demo.service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.model.Image;
import com.example.demo.responce.ApiResponce;
import com.example.demo.serviceIntefaces.ImageInterface;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageInterface {
	private final ImageRepository imageRepository;

	@Override
	public ApiResponce<Image> updateImagelink(String oldUrl, String newUrl) {
		try {
			Image image = imageRepository.findImageByUrl(oldUrl);
			if (image != null) {
				image.setUrl(newUrl);
				imageRepository.save(image);
				return new ApiResponce<Image>(true, "image is updated", image);
			}
			return new ApiResponce<Image>(false, "image url not found", null);
		} catch (Exception e) {
			return new ApiResponce<Image>(false, "error" + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<Image> deleteImage(String url) {
		try {
			Image image = imageRepository.findImageByUrl(url);
			if (image != null) {
				imageRepository.deleteById(image.getId());
				return new ApiResponce<Image>(true, "image is deleted", image);
			}
			return new ApiResponce<Image>(false, "image is not found", null);
		} catch (Exception e) {
			return new ApiResponce<Image>(false, "delete failed due to " + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<Image> updateImageById(Long id, String url) {
		try {
			Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("img not found"));
			image.setUrl(url);
			image = imageRepository.save(image);
			return new ApiResponce<Image>(true, "updated", image);
		} catch (Exception e) {
			return new ApiResponce<Image>(false, "updated failed", null);
		}
	}
}
