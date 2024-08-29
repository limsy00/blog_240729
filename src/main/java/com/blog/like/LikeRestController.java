package com.blog.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;

	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "postId") int postId,
			HttpSession session) {
		
		// 로그인 여부 확인
		Map<String, Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			// 비로그인
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요.");
			return result;
		}
		
		// likeToggle BO 요청
		likeBO.likeToggle(postId, userId);
		
		// 성공 응답
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
