package com.blog.comment.domain;

import com.blog.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CommentView {

	private Comment comment; // 댓글 1개
	private UserEntity user; // 댓글쓴이
}
