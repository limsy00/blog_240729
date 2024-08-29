package com.blog.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;

	// 공감 > 삭제/추가 Toggle
	// input: postId, userId	 output:X
	public void likeToggle(int postId, int userId) {
		// 조회
		int count = likeMapper.selectLikeCountByPostIdUserId(postId, userId);
		
		// 삭제/추가 여부
		if (count > 0) { // 채워져 있으면 삭제
			likeMapper.deleteLikeByPostIdUserId(postId, userId);
		} else { // 비워져 있으면 추가
			likeMapper.insertLike(postId, userId);
		}
	}
	
	// 공감 > 화면에 공감 갯수 뿌리기
	// input: postId	output: 갯수(int)
	public int getLikeCountByPostId(int postId) { // 가공 필요없이 행 갯수 리턴
		return likeMapper.selectLikeCountByPostId(postId);
	}
	
	// 공감 뿌리기 > 공감 체크 여부
	// input: postId, userId	★output: int
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdUserId(postId, userId);
	}
	
	// input: postId(필수), userId(로그인/비로그인)	 ★output: boolean(체크 여부)
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		// 비로그인 → DB 조회 없이 체크 불가
		if (userId == null) {
			return false;
		}
		// 로그인 → 행(1):true,  행(0)false
		return likeMapper.selectLikeCountByPostIdUserId(postId, userId) == 1 ? true : false;
	}
	
}
