package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto userDto);

	UserDto updateUser(UserDto userDto, int userId);

	UserDto getUserById(int userId);

	void deleteUser(int userId);

	List<UserDto> getAllUsers();

}
