package com.blog.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.post.domain.Post;
import com.blog.post.mapper.PostMapper;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	
	// 글 목록 > 사용자 글 목록 불러오기
	// input:(로그인 된) userId	output:글 목록 → List<Post> 
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
}
