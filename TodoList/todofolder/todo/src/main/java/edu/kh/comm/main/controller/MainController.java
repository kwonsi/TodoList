package edu.kh.comm.main.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;

import edu.kh.comm.member.model.service.MemberService;
import edu.kh.comm.member.model.vo.Member;
@Controller
public class MainController {

	/*index.jsp(http://localhost:8080/comm/main) 에서 요청보냄
	 * ->@RequestMapping에 /main 만 작성
	 */
	@RequestMapping("/main")
	public String mainForward() {

		return "common/main";
		//-> WEB-INF / views / common / main.jsp

		//controller -> return 구문 받아 dispatcher
		//-> ViewReserve -> servlet-context.xml (prefix, suffix)로 인해
		//common/main만 적어줘도 된다
	}
	
	@Autowired
	private MemberService service;

	//todo db 삽입
	@ResponseBody
	@PostMapping("/todoInsert")
	public int todoInsert(@ModelAttribute("loginMember") Member loginMember,
						 String inputtext, int memberNo) {
		int result = 0;
		loginMember.setMemberNo(memberNo);
		loginMember.setInputtext(inputtext);
		result = service.todoInsert(loginMember);
		System.out.println(loginMember.getMemberNo());
		return result;
	}

	//todo 조회
	@ResponseBody
	@GetMapping("/main/todoList")
	public String todoSelect(@ModelAttribute("loginMember") Member loginMember,
							Model model, int memberNo) {
			
		List<Member> todoList = service.todoSelect(memberNo);
		
		System.out.println("todoList" + todoList);
		
		model.addAttribute("todoList", todoList);
			
		return new Gson().toJson(todoList);
	}
	
	
	//todo 전체 삭제
	@ResponseBody
	@PostMapping("todoAllDelete")
	public int todoAllDelete(int memberNo) {
		int result = 0;
		
		result = service.todoAllDelete(memberNo);
		
		return result; 
	}
	
	
	//todo 선택 삭제
	@ResponseBody
	@PostMapping("/todoDelete")
	public int todoDelete(int todoNo) {
		
		System.out.println("todoNo : " + todoNo);
		
		int result = service.todoDelete(todoNo);
		
		return result;
	}
	
	
	
}
