package edu.kh.comm.member.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.comm.member.model.service.MyPageService;
import edu.kh.comm.member.model.vo.Member;


// /member/myPage/profile
// /member/myPage/info
// /member/myPage/changePw
// /member/myPage/secession

@Controller //bean으로 등록
@RequestMapping("/member/myPage")
@SessionAttributes({"loginMember"})	//session scope에서 loginMember를 얻어옴
public class MyPageController {

	@Autowired
	private MyPageService service;	//Service 연결

	//회원 정보 조회 이동
	@GetMapping("/info")
	public String info() {
		return "member/myPage-info";
		// WEB-INF/views/member/myPage-info
	}

	//비밀번호 변경 이동
	@GetMapping("/changePw")
	public String changePw() {
		return "member/myPage-changePw";
		// WEB-INF/views/member/myPage-info
	}

	//회원탈퇴 이동
	@GetMapping("/secession")
	public String secession() {
		return "member/myPage-secession";
		// WEB-INF/views/member/myPage-info
	}

	//프로필 변경 이동
	@GetMapping("/profile")
	public String profile() {
		return "member/myPage-profile";
		// WEB-INF/views/member/myPage-info
	}


	//회원정보수정
	@PostMapping("/info")
	public String updateInfo(@ModelAttribute("loginMember") Member loginMember,
							@RequestParam Map<String, Object> paramMap,
							//->요청시 전달된 파라미터를 구분하지 않고 모두 Map에 담아서 얻어옴)
							String[] updateAddress, RedirectAttributes ra){


		//필요한 값

		//-닉네임
		//-전화번호
		//-주소
		//-회원번호(Session -> 로그인한 회원정보를 통해서 얻어옴)
		// ->@SessionAttribute, @ModelAttribute 필요

		//@SessionAttribute 의 역할
		// 1.Model에 세팅된 데이터의 key와
		//	@SessionAttribute에 작성된 key 같으면
		// Model에 세팅된 데이터를 request -> session scope로 이동
		// 2.기존에 session scope 에 이동시킨 값을 얻어오는 역할

		/*@SessionAttribute 의 역할
		 1.Model에 세팅된 데이터의 key와
			@SessionAttribute에 작성된 key 같으면
		 Model에 세팅된 데이터를 request -> session scope로 이동
		 2.기존에 session scope 에 이동시킨 값을 얻어오는 역할
		   @ModelAttrbute("loginMember") Member loginMember
		   				  session key값
		   			->@SessionAttribute를 통해 session scope에 등록된 값을 얻어와
		   			  오른쪽 작성된 Member loginMember 변수에 대입
		   			  단, 클래스위에 @SessionAttribute({"loginMember"})가 작성되어 있어야 가능
		 */

		//***매개변수를 이용해서 session, 파라미터 데이터를 동시에 얻어올 때 문제점*****
		//session에서 객체를 얻어오기 위해 매개변수에 작성한 상태에서
		//->@ModelAttribute("loginMember") Member loginMember

		//파라미터의 name속성값이 매개변수에 작성된 객체(loginMember)의 필드와 일치하면
		//-> ex) name="memberNickname"

		//session에서 얻어온 객체의 필드에 덮어쓰기가 된다

		//해결방법 파라미터의 namet속성을 변경해서 얻어오면 문제 해결할 수 있다
		//->필드명이 겹쳐서 문제니깐 겹치지 않게 해준다는 의미

		//파라미터를 저장한 paramMap에 회원번호, 주소를 추가
		String memberAddress = String.join(",," , updateAddress);

		//주소가 미입력 일때
		if(memberAddress.equals(",,,,"))	memberAddress = null;

		paramMap.put("memberNo", loginMember.getMemberNo());
		paramMap.put("memberAddress", memberAddress);
		//-> paramMap.put("mapper의 sql문에서 #{memberAddress}", memberAddress);

		//회원정보 수정 서비스 호출
		int result = service.updateInfo(paramMap);

		String message = null;

		if(result > 0) {
			message = "회원정보가 수정되었습니다";

			//DB-Session의 회원정보 동기화
			loginMember.setMemberNickname((String)paramMap.get("updateNickname"));
			loginMember.setMemberTel((String)paramMap.get("updateTel"));
			loginMember.setMemberAddress((String)paramMap.get("updateAddress"));

		}else {
			message = "회원정보 수정 실패";
		}

		ra.addFlashAttribute("message", message);

		return "redirect:info";


	}


	//비밀번호 변경(암호화)
	@PostMapping("/changePw")
	public String changePw(@RequestParam Map<String, Object> paramMap,
							@ModelAttribute("loginMember") Member loginMember,
							RedirectAttributes ra) {

		//로그인된 회원의 번호를 paramMap에 추가
		paramMap.put("memberNo", loginMember.getMemberNo());

		//비밀번호 변경 서비스 호출
		int result = service.changePw(paramMap);


		String message = null;
		String path = null;

		if(result>0) {
			message = "비밀번호 변경 성공";
			path = "info";

		}else {
			message = "현재 비밀번호가 일치하지 않습니다";
			path = "changePw";
		}
		ra.addFlashAttribute("message", message);

		return "redirect:" + path;

	}



	//회원탈퇴(세션, 쿠키 무효화)
	@PostMapping("/secession")
	public String secession(@ModelAttribute("loginMember") Member loginMember,
							SessionStatus status,
							HttpServletRequest req, HttpServletResponse resp,
							RedirectAttributes ra) {

		//회원탈퇴 서비스 호출
		int result = service.secession(loginMember);

		String message = null;
		String path = null;

		if(result>0) {

			if(result>0) {
				message = "회원탈퇴 되었습니다";
				path = "/";

				//탈퇴 후 세션 없애기
				status.setComplete();

				//탈퇴 후 쿠키 없애기
				//쿠키생성
				Cookie cookie = new Cookie("saveId", "");
				cookie.setMaxAge(0);
				cookie.setPath(req.getContextPath());
				resp.addCookie(cookie);

			}else {
				message = "비밀번호가 일치하지 않습니다";
				path = "secession";
			}

		}
		ra.addFlashAttribute("message", message);

		return "redirect:" + path;

	}


	//프로필 수정
	@PostMapping("/profile")
	public String updateProfile(@ModelAttribute("loginMember") Member loginMember,
								@RequestParam("uploadImage") MultipartFile uploadImage, //업로드파일 자체
								@RequestParam Map<String, Object> map, //myPage-profile에서 삭제버튼 delete만 담겨있다
								HttpServletRequest req, //파일 저장 경로 탐색용
								RedirectAttributes ra) throws IOException {
		//경로 작성하기


		//1)웹 접근 경로(/comm/resources/images/memberProfile)
		String webPath = "/resources/images/memberProfile";

		//2)서버 저장 폴더 경로(컴퓨터에서의 경로
		//C:\workspace\7_Framework\comm\src\main\webapp\WEB-INF\views\member\myPage-profile.jsp)
		String folderPath = req.getSession().getServletContext().getRealPath(webPath);

		//map에 경로2개, 이미지, delete, 회원번호 담기
		map.put("webPath", webPath);
		map.put("folderPath", folderPath);
		map.put("uploadImage", uploadImage);
		map.put("memberNo", loginMember.getMemberNo());

		int result = service.updateProfile(map);

		String message = null;

		if(result>0) {
			message = "프로필 이미지가 변경됬습니다";

			loginMember.setProfileImage((String)map.get("profileImage"));

		}else {
			message = "프로필 이미지 변경 실패";
		}

		ra.addFlashAttribute("message", message);

		return "redirect:profile";

	}



}
