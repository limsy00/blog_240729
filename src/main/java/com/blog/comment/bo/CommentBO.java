package com.blog.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.comment.domain.Comment;
import com.blog.comment.domain.CommentView;
import com.blog.comment.mapper.CommentMapper;
import com.blog.user.bo.UserBO;
import com.blog.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;

	// 댓글 쓰기 > 댓글 추가(저장)하기
	// input: 글번호, 댓글쓴이, 댓글 	output:X
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}
	
	// 댓글 뿌리기 > 댓글, 댓글쓴이 불러오기
	public List<CommentView> generateCommentViewListByPostId(int postId) {
		List<CommentView> commentViewList = new ArrayList<>();
		
		// 댓글들 가져오기
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
		
		// 반복문 순회 : Comment → CommentView → list에 넣기
		for (Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);
			
			// 댓글쓴이
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			commentViewList.add(commentView); // commentList에 넣기
		}
		
		return commentViewList;
	}
}
