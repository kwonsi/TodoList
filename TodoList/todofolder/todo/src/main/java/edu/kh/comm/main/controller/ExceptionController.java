package edu.kh.comm.main.controller;

import java.sql.SQLException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {


	//스프링 예외처리 방법 2순위
	//멤버 컨트롤러에서 발생하는 모든 예외를 모아서 처리
	@ExceptionHandler(Exception.class)
	//->@ExceptionHandler(예외종류.class)
	public String exceptionHandler(Exception e, Model model) {
		e.printStackTrace();

		model.addAttribute("errorMassage", "서비스 이용 중 문제 발생");
		model.addAttribute("e", e);

		return "common/error";
	}

	@ExceptionHandler(SQLException.class)
	//->@ExceptionHandler(예외종류.class)
	public String sqlExceptionHandler(SQLException e, Model model) {
		e.printStackTrace();

		model.addAttribute("errorMassage", "서비스 이용 중 SQLException 발생");
		model.addAttribute("e", e);

		return "common/error";
	}

}

