package com.blog.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.user.entity.UserEntity;
import com.blog.user.repository.UserRepository;

@Service
public class UserBO {
	@Autowired
	private UserRepository userRepository;

	// 회원가입 > 아이디 중복 확인
	// input: loginId	 output: 채워진 UserEntity or null 
	public UserEntity getUserEntityByLoginId(String loginId) { 
		return userRepository.findByLoginId(loginId);
	}
	
	// 로그인 > 데이터 조회
	// input: loginId, 해싱된 password	output: 채워진 UserEntity or null 
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) { 
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
	
	// 회원가입 > 데이터 저장 (CRUD → repository 메소드 필요X)
	// input: loginId, password, name, email	 output: UserEntity
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	} 
}
