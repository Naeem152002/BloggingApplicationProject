package com.blogapplication.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto
{

	private int categoryId;
	
	@NotEmpty
	@Size(min=4, message="Min size of category title is 4")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min=10, message="Min size of category desc is 10")
	private String categoryDescripation;
	
}
