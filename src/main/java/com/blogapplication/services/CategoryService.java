package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto); 
	CategoryDto updateCategory(CategoryDto categoryDto,int categoryId);
	CategoryDto getCategoryById(int categoryId);
	
	
	void deleteCategory(int categoryId);
	
	
	List<CategoryDto>getAllCategories();
	
	
		
	}


