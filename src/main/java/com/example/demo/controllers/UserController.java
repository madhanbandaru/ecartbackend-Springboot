package com.example.demo.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.responce.ApiResponce;
import com.example.demo.responce.UserResponce;
import com.example.demo.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public ResponseEntity<ApiResponce<User>> addUser(@RequestBody UserDTO user) {
        ApiResponce<User> response = userService.addUser(user);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponce> login(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) {  // Inject response

        UserResponce user = userService.getUserBYEmailAndPassword(email, password);

        if (user != null) {
            // 🔹 Create a cookie with userId or token
            Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
            cookie.setHttpOnly(true);   // not accessible by JavaScript
            cookie.setSecure(false);    // set true if using HTTPS
            cookie.setPath("/");        // available for all endpoints
            cookie.setMaxAge(60 * 60);  // 1 hour expiry

            // 🔹 Add cookie to response
            response.addCookie(cookie);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserResponce>> getAllUser(){
    	List<UserResponce> user= userService.getAllUsers();
    	if(user!=null) {
    		return ResponseEntity.ok(user);
    	}
    	else {
    		return ResponseEntity.badRequest().body(user);
    	}
    }
    @PostMapping("/{id}")
    public ResponseEntity<UserResponce> UserById(@PathVariable Long id){
    	UserResponce user= userService.getUserById(id);
    	if (user!=null) {
    		return ResponseEntity.ok(user);
    	}
    	else {
    		return ResponseEntity.badRequest().body(user);
    	}
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponce<User>>  userUpdate(@RequestBody UserDTO user){
    	 ApiResponce<User> use=userService.updateUser(user);
    	 if (use!=null){
    		 return ResponseEntity.ok(use);
    	 }
    	 else {
    		 return ResponseEntity.badRequest().body(use);
    	 }
    	
    }
    @PostMapping("/delete")
    public  ResponseEntity<ApiResponce<String>> deleteUser(UserDTO user){
    	ApiResponce<String> result=userService.deleteUser(user);
    	return ResponseEntity.ok(result);
    }


    
}
