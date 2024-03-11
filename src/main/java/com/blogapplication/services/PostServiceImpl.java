package com.blogapplication.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.Category;
import com.blogapplication.entities.Post;
import com.blogapplication.entities.User;
import com.blogapplication.exceptions.ReourceNotFoundException;
import com.blogapplication.payloads.PostDto;
import com.blogapplication.repositories.CategoryRepo;
import com.blogapplication.repositories.PostRepo;
import com.blogapplication.repositories.UserRepo;
import com.blogapplication.response.PostResponse;
@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;


	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto,int userId,int categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ReourceNotFoundException("User", "Id", userId));
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ReourceNotFoundException("Category", "Id", categoryId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(cat);
		
		
	Post newPost=this.postRepo.save(post);
	
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ReourceNotFoundException("Post", "Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatePost=this.postRepo.save(post);
			return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ReourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ReourceNotFoundException("Post", "Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber,int pageSize,String sortBy,String sortDir)  {
		
		
		Sort sort=null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post>pagePost=this.postRepo.findAll(p);
		List<Post>allPosts=pagePost.getContent();
		List<PostDto>postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElements(pagePost.getTotalElements());
		
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(int categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ReourceNotFoundException("Category", "Id", categoryId));
		List<Post>posts=this.postRepo.findByCategory(cat);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(int userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ReourceNotFoundException("User", "Id", userId));
		
		List<Post>posts=this.postRepo.findByUser(user);
		List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post>posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto>postDto=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDto;
	}

}
