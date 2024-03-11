package com.blogapplication.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blogapplication.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message="username must be min of 4 characters !!")
	private String name;
	
	@Email(message="Email address is not valid !!")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10,message="Password must be  min of 3 characters and max of 10 chars !!")
	private String password;
	
	@NotEmpty
	private String about;
	
	
	private Set<RoleDto>roles=new HashSet<>();

}

