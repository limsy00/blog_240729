package com.blog.comment.doamain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Comment {
	private int id;
	private int postId;
	private int userId;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}