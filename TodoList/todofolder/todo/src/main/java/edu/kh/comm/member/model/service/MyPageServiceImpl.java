package edu.kh.comm.member.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.comm.common.Util;
import edu.kh.comm.member.model.dao.MyPageDAO;
import edu.kh.comm.member.model.vo.Member;

@Service
public class MyPageServiceImpl implements MyPageService {


	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private MyPageDAO dao;

	//내정보 변경
	@Override
	public int updateInfo(Map<String, Object> paramMap) {


		return dao.updateInfo(paramMap);
	}

	//비밀번호 변경
	@Override
	public int changePw(Map<String, Object> paramMap) {
		//bcrypt

		//1)DB에서 현재 회원의 비밀번호를 조회한다
		String encPw = dao.checkPw((int)paramMap.get("memberNo"));
							//->Map상태라서 int로 다운캐스팅

		//2)입력된 현재 비밀번호(평문)와 DB조회된 비밀번호(암호화) 비교(bcrypt.matches() 이용)

		//3)비교결과 일치하면 받아온 새로운 비밀번호 암호화해서 update구문 실행
		if(bcrypt.matches( (String)paramMap.get("currentPw"), encPw) ) {
						//Map상태라서 String으로 다운캐스팅

			paramMap.put("newPw", bcrypt.encode( (String)paramMap.get("newPw")) );

			return dao.updatePw(paramMap);
		}

		//비밀번호가 일치하지 않으면 0 반환
		return 0;
	}


	//회원탈퇴
	@Override
	public int secession(Member loginMember) {

		//1. DB에서 현재회원의 비밀번호 조회
		String encPw = dao.checkPw( loginMember.getMemberNo());

		if(bcrypt.matches( loginMember.getMemberPw(), encPw) ) {
			//2. 비밀번호가 일치하면 회원번호를 이용해 탈퇴 진행
			return dao.secession(loginMember.getMemberNo());
		}

		//3.비밀번호가 일치하지 않으면 0 반환
		return 0;
	}


	//프로필이미지 수정 서비스
	@Override
	public int updateProfile(Map<String, Object> map) throws IOException {
							//webPath, folderPath, uploadImage, delete(String), memberNo

		//자주쓰는 값 변수에 저장
		MultipartFile uploadImage = (MultipartFile)map.get("uploadImage");
		String delete = (String)map.get("delete");	// 0이면 변경, 1이면 삭제

		//프로필 이미지 삭제여부를 확인해서
		//삭제가 아닌 경우(==새이미지로 변경) -> 업로드된 파일명 변경
		//삭제된경우 -> NULL값을 준비

		String renameImage = null; 	//변경된 파일명 저장할 변수

		if(delete.equals("0")) {	//이미지가 변경된 경우
			//파일명 변경
			//uploadImage.getOriginalFilename() :원본 파일명
			renameImage = Util.fileRename(uploadImage.getOriginalFilename());

			map.put("profileImage", map.get("webPath") + renameImage);
								// /resources/images/memberProfile/202206241222315.png

		}else {
			map.put("profileImage", null);
		}

		//DAO 호출해서 프로필 이미지 수정
		int result = dao.updateProfile(map);


		//DB 수정 성공 시 메모르에 임시 저장되있는 파일을 서버에 저장
		if(result>0 && map.get("profileImage") != null) {
			uploadImage.transferTo(new File(map.get("folderPath") + renameImage) );
		}


		return result;
	}









}
