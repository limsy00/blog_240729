package com.blog.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// 회원가입 > 아이디 중복 확인 > 아이디 조회
	public UserEntity findByLoginId(String loginId); 
	// 로그인 > 아이디, 해싱된 비번 조회
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
