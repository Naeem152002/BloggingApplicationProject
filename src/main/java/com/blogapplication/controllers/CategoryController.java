package com.blogapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.payloads.CategoryDto;
import com.blogapplication.response.ApiResponse;
import com.blogapplication.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	@PostMapping(value="/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto,HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{categoryId}")
	public ResponseEntity<CategoryDto>updateUser(@Valid @RequestBody CategoryDto categoryDto ,@PathVariable("categoryId") int Id){
CategoryDto updateCategory=this.categoryService.updateCategory(categoryDto, Id);
return ResponseEntity.ok(updateCategory);

}
	@DeleteMapping(value="/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable int categoryId){
	this.categoryService.deleteCategory(categoryId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Sucessfully",true),HttpStatus.OK);
	}
	@GetMapping(value="/")
	public ResponseEntity<List<CategoryDto>>getAllCategories(){
		return ResponseEntity.ok(this.categoryService.getAllCategories());
		
	}
	@GetMapping(value="/{categoryId}")
	public ResponseEntity<CategoryDto>getSingleCategory(@PathVariable int categoryId){
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
		
	}
	
	
	}
	
