package com.blogapplication.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.Category;
import com.blogapplication.exceptions.ReourceNotFoundException;
import com.blogapplication.mappers.UserMapper;
import com.blogapplication.payloads.CategoryDto;
import com.blogapplication.repositories.CategoryRepo;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	ModelMapper modelMapper = UserMapper.modelMapper();
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCat=categoryRepo.save(cat);
	CategoryDto dto=this.modelMapper.map(addedCat, CategoryDto.class);
		return dto;
	}
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ReourceNotFoundException("Category", "Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescripation(categoryDto.getCategoryDescripation());

		Category updateCategory = this.categoryRepo.save(cat);
		CategoryDto dto = modelMapper.map(updateCategory, CategoryDto.class);
		return dto;
	}
	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ReourceNotFoundException("Category", "Id", categoryId));
		CategoryDto dto = modelMapper.map(cat, CategoryDto.class);
		return dto;
		
	}
	@Override
	public void deleteCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ReourceNotFoundException("Category", "Id", categoryId));
this.categoryRepo.delete(cat);
		
	}
	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDto = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());

		return categoryDto;
	}

}