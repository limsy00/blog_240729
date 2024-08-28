package com.blog.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {

	public static final String FILE_UPLOAD_PATH = "C:\\Users\\82102\\Desktop\\megaIT\\seoyoung_project\\blog_240729\\blog_workspace\\images/";
	
	// 글 쓰기 > 사진 업로드 및 DB에 저장
	// input: MultipartFile, userLoginId(사용자 로그인 아이디)	output: String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// 폴더 생성
		String directoryName = loginId + "_" + System.currentTimeMillis(); // 폴더명 선언
		String filePath = FILE_UPLOAD_PATH + directoryName + "/"; // 파일 경로
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { // 폴더 생성 실패 → 경로 null
			return null;
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 실패 → 경로 null
		}
		
		// 파일 업로드가 성공 → 이미지 url path 리턴
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
		
	}
	
}
