package com.blogapplication.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapplication.config.AppConstants;
import com.blogapplication.entities.Role;
import com.blogapplication.entities.User;
import com.blogapplication.exceptions.ReourceNotFoundException;
import com.blogapplication.mappers.UserMapper;
import com.blogapplication.payloads.UserDto;
import com.blogapplication.repositories.RoleRepo;
import com.blogapplication.repositories.UserRepo;
@Service

public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	public RoleRepo roleRepo;
	
	ModelMapper modelMapper = UserMapper.modelMapper();

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Override
	public UserDto createUser(UserDto userDto) {
		User userEntintity = modelMapper.map(userDto, User.class);
		userEntintity.setPassword(this.passwordEncoder.encode(userEntintity.getPassword()));
		
		Role role=this.roleRepo.findById(AppConstants.ADMIN_USER).get();
		userEntintity.getRoles().add(role);
		User save = userRepo.save(userEntintity);
		UserDto dto = modelMapper.map(save, UserDto.class);
		return dto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ReourceNotFoundException("User", "Id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());

		User updateUser = this.userRepo.save(user);
		UserDto dto = modelMapper.map(updateUser, UserDto.class);
		return dto;
	}

	@Override
	public UserDto getUserById(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ReourceNotFoundException("User", "Id", userId));
		UserDto dto = modelMapper.map(user, UserDto.class);
		return dto;
	}

	@Override
	public void deleteUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ReourceNotFoundException("User", "Id", userId));
this.userRepo.delete(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());

		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role=this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User newUser=this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
