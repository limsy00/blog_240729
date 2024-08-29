package com.blog.post.domain;

import java.util.List;

import com.blog.comment.domain.CommentView;
import com.blog.post.entity.PostEntity;
import com.blog.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostCardView { // 글 1개와 mapping, (글:댓글 = 1:N)

	private PostEntity post; // 글 1개
	private UserEntity user; // 글쓴이 정보
	
	private List<CommentView> commentList; // 댓글 N개 
	
	private int likeCount; // 공감 N개
	private boolean filledLike; // 공감 클릭 여부 → 이미 눌렀으면 홀수
}
