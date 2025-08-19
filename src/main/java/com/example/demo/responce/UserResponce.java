package com.example.demo.responce;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponce {
	
	private Long id;
	private String name;
	private String email;
	private Long phone;
	private String password;
}
