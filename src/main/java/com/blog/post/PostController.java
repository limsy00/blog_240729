package com.blog.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.post.bo.PostBO;
import com.blog.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String postListView(HttpSession session, Model model) { 
		
		// 로그인 여부 확인 → session
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) { // 비로그인
			return "redirect:/user/sign-in-view";
		}
		
		// select db
		List<Post> postList = postBO.getPostListByUserId(userId);
		// model에 글 목록 담기
		model.addAttribute("postList", postList);
		
		return "post/postList";
	}
}
