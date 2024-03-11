package com.blogapplication.services;

import com.blogapplication.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,int postId);
	void deleteComment(int commentId);

}
