package com.blog.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.blog.comment.domain.Comment;

@Mapper
public interface CommentMapper {

	// 댓글 쓰기 > 댓글쓴이의 댓글 저장
	public int insertComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("content") String content); // 맵
	
	// 댓글 뿌리기 > 댓글들 조회
	public List<Comment> selectCommentList();
	
	// 댓글 뿌리기 > 각각의 글 번호에 해당하는 댓글들 조회
	public List<Comment> selectCommentListByPostId(int postId);
	
	// 댓글 삭제
	public void deleteCommentById(int id);
}
