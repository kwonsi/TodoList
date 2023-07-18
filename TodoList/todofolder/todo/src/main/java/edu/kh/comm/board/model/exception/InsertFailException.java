package edu.kh.comm.board.model.exception;


//사용자 정의 예외 발생
//1.기존 존재하는 예외클래스 하나 상속 받음

//2.생성자 작성시 super() 생성자 이용해 코드 구현


public class InsertFailException extends RuntimeException {

	public InsertFailException() {
		super("게시글 삽입 실패");
	}

	public InsertFailException(String message) {
		super(message);

	}



}
