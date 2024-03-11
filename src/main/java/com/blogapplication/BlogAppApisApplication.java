package com.blogapplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blogapplication.config.AppConstants;
import com.blogapplication.entities.Role;
import com.blogapplication.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{
	
	@Autowired
	public RoleRepo roleRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role>roles=List.of(role,role1);
			
			List<Role>result=this.roleRepo.saveAll(roles);
			
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}



}
