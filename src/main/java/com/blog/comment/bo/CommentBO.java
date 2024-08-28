package com.blog.comment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.comment.mapper.CommentMapper;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;

	// 댓글 쓰기 > 댓글 추가(저장)하기
	// input: 글번호, 댓글쓴이, 댓글 	output:X
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
}
