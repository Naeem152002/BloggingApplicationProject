package com.blogapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.User;
import com.blogapplication.exceptions.ReourceNotFoundException;
import com.blogapplication.repositories.UserRepo;



@Service
public class CustomUserDetailsService implements UserDetailsService {
    
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userRepo.findByEmail(username).orElseThrow(()-> new ReourceNotFoundException("User ","email: "+username,0));
		return user;
	}
	
}
