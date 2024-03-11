package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.PostDto;
import com.blogapplication.response.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,int userId,int categoryId); 
	PostDto updatePost(PostDto postDto,int postId);
	PostDto getPostById(int postId);
	
	
	void deletePost(int postId);
	
	
	PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	List<PostDto>getPostByCategory(int categoryId);
	
	List<PostDto>getPostByUser(int userId);
	
	
	List<PostDto>searchPosts(String keyword);
	
		
	}


