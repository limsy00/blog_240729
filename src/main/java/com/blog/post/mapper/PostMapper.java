package com.blog.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; 

import com.blog.post.domain.Post;
import com.blog.post.entity.PostEntity;

@Mapper
public interface PostMapper {

	public List<Map<String, Object>> selectPostListTest(); // test
	
	// 글 목록 > 사용자의 글 목록 조회
	public List<Post> selectPostListByUserId(int userId);
	
	// 글 목록 > 글 목로 조회 → 댓글, 공감 뿌리기를 위함
	public List<Post> selectPostListByOrderByIdDesc();
	
	// 글 쓰기 > 글 추가
	public void insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("imagePath") String imagePath);
	
	// 글 상세 > 사용자의 글 조회
	public Post selectPostByPostIdUserId( 
			@Param("userId") int userId, 
			@Param("postId") int postId);
	
	// 댓글 뿌리기 > 모들 글 조회하는 메소드
	List<PostEntity> selectAllPosts();
}
