package com.blog.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.blog.post.domain.Post;

@Mapper
public interface PostMapper {

	public List<Map<String, Object>> selectPostListTest(); // test
	
	// 글 목록 > 사용자의 글 목록 조회
	public List<Post> selectPostListByUserId(int userId);
}
