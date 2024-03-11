package com.blogapplication.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.Comment;
import com.blogapplication.entities.Post;
import com.blogapplication.exceptions.ReourceNotFoundException;
import com.blogapplication.payloads.CommentDto;
import com.blogapplication.repositories.CommentRepo;
import com.blogapplication.repositories.PostRepo;
@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private  ModelMapper modelMapper;
	
	

	@Override
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ReourceNotFoundException("Post", "Id", postId));
		
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ReourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(com);
	}

}
