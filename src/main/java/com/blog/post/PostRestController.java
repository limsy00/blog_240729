package com.blog.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	private PostBO postBO;

	/**
	 * 글쓰기 api
	 * @param subject
	 * @param content
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content, 
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		// 세션에서 userLoginId 꺼내기
		Integer  userId = (Integer)session.getAttribute("userId"); // 로그인 풀릴 경우 에러
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		if (userId == null) {
			result.put("code", 403); // 비로그인 상태
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// db insert 
		postBO.addPost(userId, userLoginId, subject, content, file);
		
		// 응답값
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
