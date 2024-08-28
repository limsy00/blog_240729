package com.blog.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

	// 댓글 쓰기 > 댓글쓴이의 댓글 저장하기
	public int insertComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("content") String content); // 맵
	
}
