package com.example.demo.serviceIntefaces;

import java.util.List;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.UserResponce;

public interface UserInterface {
	ApiResponce<User> addUser(UserDTO user);
    List<UserResponce> getAllUsers();
    UserResponce getUserById(Long id);
    ApiResponce<User> updateUser(UserDTO user);
    ApiResponce<String> deleteUser(UserDTO user);
	UserResponce getUserBYEmailAndPassword(String email, String password);
}
