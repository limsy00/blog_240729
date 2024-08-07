package com.blog.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.common.EncryptUtils;
import com.blog.user.bo.UserBO;
import com.blog.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;

	/**
	 * 아이디 중복 확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) { // json → map
		
		// select db
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200); // 성공
		
		if (user != null) { 
			result.put("is_duplicated_id", true); // 중복
		} else { 
			result.put("is_duplicated_id", false); // 실패
		}
		return result;
	}
	
	/**
	 * 회원가입
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// md5 → hashing
		String hashedPassword = EncryptUtils.md5(password);
		
		// insert db
		UserEntity user = userBO.addUser(loginId, password, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		return result;
	}
	
	/**
	 * 로그인
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
		@RequestParam("loginId") String loginId,
		@RequestParam("password") String password,
		HttpServletRequest request) {
				
		String haschedPassword = EncryptUtils.md5(password); // pw hashing	
		
		// select db
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, haschedPassword);
		 
		// 응답값 
		Map<String, Object> result = new HashMap<>();
		if (user != null) { // 성공
			HttpSession session = request.getSession(); // 세션에 사용자의
			session.setAttribute("userId", user.getId());  // -고유번호 저장
			session.setAttribute("userLoginId", user.getLoginId()); // -로그인 아이디 저장
			session.setAttribute("userName", user.getName()); // -이름 저장
		
			result.put("code", 200);
			result.put("result", "성공");
		} else { // 실패
			result.put("code", "404");
			result.put("error_message", "존재하지 않는 사용자 입니다.");
		}
		return result;
	
	}
}
