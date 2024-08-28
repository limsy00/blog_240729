package com.blog.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.common.FileManagerService;
import com.blog.post.domain.Post;
import com.blog.post.mapper.PostMapper;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 글 목록 > 사용자 글 목록 가져오기
	// input:(로그인 된) userId	output:글 목록 → List<Post> 
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// 글 상세 > 사용자 글 가져오기
	// input: userId, postId   output:글 → Post or null
	public Post getPostByPostIdUserId(int userId, int postId) {
		return postMapper.selectPostByPostIdUserId(userId, postId);
	}
	
	// 글 쓰기 > 글 추가하기
	// input: 사용자 id, 사용자 로그인ID, 제목, 내용, 사진		output:X
	public void addPost(int userId, String userLoginId, String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		if (file != null) { // 업로드할 이미지가 있을 때
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
		
	}
}
