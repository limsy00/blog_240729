package com.blog.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.comment.bo.CommentBO;
import com.blog.comment.domain.CommentView;
import com.blog.post.bo.PostBO;
import com.blog.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	/**
	 * 글 목록 화면
	 * @param session
	 * @param model
	 * @return
	 */
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
	
	/**
	 * 글쓰기 화면
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView() {
		return "post/postCreate";
	}
	
	/**
	 * 글 상세(타임라인) 화면
	 * @param postId
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId, 
			Model model, 
			HttpSession session) {  

		// 로그인 여부 확인 → 로그인이 풀릴 경우 접근X
		int userId = (int)session.getAttribute("userId"); // 로그인된 상태라고 인지(다운캐스팅)
		
		// db select
		Post post = postBO.getPostByPostIdUserId(userId, postId);
		
		// 댓글 뿌리기 > 댓글 목록 가져오기
		List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(postId);
		
		// model에 글 담기
		model.addAttribute("post", post);
		model.addAttribute("commentViewList", commentViewList);
		
		return "post/postDetail";
	}
	
}
