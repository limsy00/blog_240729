package com.blog.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

	// 공감 toggle > 표시 했는지 조회
	public int selectLikeCountByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	// 공감 뿌리기 > 공감 갯수 조회
	public int selectLikeCountByPostId(int postId);
	
	// 공강 toggle > 추가
	public void insertLike(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	// 공강 toggle > 삭제
	public void deleteLikeByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
}
