package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.UserResponce;
import com.example.demo.serviceIntefaces.UserInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserInterface {
	private final UserRepository userRepository;

	@Override
	public UserResponce getUserBYEmailAndPassword(String email, String password) {
		User u = userRepository.findByEmailAndPassword(email, password);
		return UserResponce.builder().email(u.getEmail()).name(u.getName()).password(u.getPassword()).id(u.getId())
				.phone(u.getPhone()).build();
	}

	@Override
	public ApiResponce<User> addUser(UserDTO user) {
		try {
			User u = User.builder().email(user.getEmail()).name(user.getName()).phone(user.getPhone())
					.password(user.getPassword()).build();
			User savedUser = userRepository.save(u);
			return new ApiResponce<>(true, "User added successfully", savedUser);
		} catch (Exception e) {
			return new ApiResponce<>(false, "Failed to add user: " + e.getMessage(), null);
		}
	}

	@Override
	public List<UserResponce> getAllUsers() {
		List<User> user = userRepository.findAll();
		return user.stream().map(u -> UserResponce.builder().id(u.getId()).name(u.getName()).email(u.getEmail())
				.password(u.getPassword()).phone(u.getPhone()).build()).toList();
	}

	@Override
	public UserResponce getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		return UserResponce.builder().id(user.getId()).email(user.getEmail()).name(user.getName())
				.password(user.getPassword()).phone(user.getPhone()).build();
	}

	@Override
	public ApiResponce<User> updateUser(UserDTO user) {
		try {
			User u = userRepository.findByEmail(user.getEmail());
			u.setName(user.getName());
			u.setPassword(user.getPassword());
			u.setPhone(user.getPhone());
			u = userRepository.save(u);
			return new ApiResponce<User>(true, "user updated", u);
		} catch (Exception e) {
			return new ApiResponce<User>(false, "user updation failed" + e.getMessage(), null);
		}
	}

	@Override
	public ApiResponce<String> deleteUser(UserDTO user) {
		try {
			User u = userRepository.findByEmail(user.getEmail());
			userRepository.deleteById(u.getId());
			return new ApiResponce<String>(true, "user deleted", null);
		} catch (Exception e) {
			return new ApiResponce<String>(false, "delete failed", null);
		}
	}

}
