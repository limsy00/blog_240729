package com.blog.post.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.comment.bo.CommentBO;
import com.blog.comment.domain.CommentView;
import com.blog.common.FileManagerService;
import com.blog.like.bo.LikeBO;
import com.blog.post.domain.Post;
import com.blog.post.domain.PostCardView;
import com.blog.post.entity.PostEntity;
import com.blog.post.mapper.PostMapper;
import com.blog.user.bo.UserBO;
import com.blog.user.entity.UserEntity;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
    private UserBO userBO;
	
	@Autowired
	private CommentBO commentBO;
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// 글 목록 > 글 목록 가져오기 → 댓글 뿌리기, 공감을 위한 메소드
	// input:X	output: List<Post>
	public List<Post> getPostList() {
		return postMapper.selectPostListByOrderByIdDesc();
	}
	
	// 글 목록 > 사용자 글 목록 가져오기
	// input:(로그인 된) userId	output:글 목록 → List<Post> 
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// 글 상세 > 사용자 글 가져오기
	// input: userId, postId   output:글 → Post or null
	public Post getPostByPostIdUserId(int userId, int postId) {
		return postMapper.selectPostByPostIdUserId(userId, postId);
	}
	
	// 글 쓰기 > 글 추가하기
	// input: 사용자 id, 사용자 로그인ID, 제목, 내용, 사진		output:X
	public void addPost(int userId, String userLoginId, String subject, 
			String content, MultipartFile file) {
		
		String imagePath = null;
		if (file != null) { // 업로드할 이미지가 있을 때
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	// 댓글 뿌리기 + 공감 뿌리기 → 비로그인 허용
	// input:X	output: List<PostCardView> 
	public List<PostCardView> generateCardViewList(Integer userId) { 
		List<PostCardView> cardViewList = new ArrayList<>();
		Set<Integer> postIds = new HashSet<>(); // 중복 제거를 위한 Set

		// 글 목록 가져오기 : List<PostEntity>
		List<PostEntity> postList = postMapper.selectAllPosts();
		
		// 글 목록 반복 순회 : PostEntity → CardView → cardViewList에 담기
		for (PostEntity post : postList) {
			if (!postIds.contains(post.getId())) { // 중복체크
	            PostCardView card = new PostCardView();
	            
	            card.setPost(post); // 글 → cardViewList에 담기
	             
	            UserEntity user = userBO.getUserEntityById(post.getUserId()); // 글쓴이 정보 가져오기
	            card.setUser(user);
	            
	            // 댓글 목록 가져오기
	            List<CommentView> commentList = commentBO.generateCommentViewListByPostId(post.getId());
	            card.setCommentList(commentList); // 댓글 담기
	            
	            // 공감 갯수 가져오기
	            int likeCount = likeBO.getLikeCountByPostId(post.getId());
				card.setLikeCount(likeCount);
				
				// 공감 체크 여부
				boolean filledLike = likeBO.filledLikeByPostIdUserId(post.getId(), userId);
		        card.setFilledLike(filledLike);
		        
	            cardViewList.add(card); // 리스트에 추가
	            postIds.add(post.getId()); // 중복 방지
			}

        }
        
        return cardViewList;
	}
	
}
