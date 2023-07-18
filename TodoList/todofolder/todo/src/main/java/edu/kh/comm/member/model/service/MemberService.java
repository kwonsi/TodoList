package edu.kh.comm.member.model.service;

import java.util.List;

import edu.kh.comm.member.model.vo.Member;

/*MemeberService를 interface로 만든 이유
 * (Service Interface를 사용하는 이유)
 * 1.프로젝트에 규칙성을 부여하기 위해
 * 2.Spring AOP(트랜잭션, 로깅, 보안등 여러 모듈, 여러계층에서 공통으로 필요로 하는
 * 	기능의 경우 해당 기능들을 모듈화해서(따로 분리해) 관리한다)를 위해
 * 3.클래스간의 결합도를 약화시키기 위해
 * ->객체의 교환성을 높여주기 때문에 다형성을 구현하는 매우 중요한 역할을 한다
 * 	 즉, 개발코드를 수정하지 않고 사용하는 객체를 변경할 수 있도록 해준다
 */
public interface MemberService {
	//모든 메서드가 추상 메서드(public abstract)
	//모든 필드는 상수 (public static final)



	/**로그인 서비스
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);
	//-> public abstract 생략

	//이메일 중복검사
	public int emailDupCheck(String memberEmail);

	//닉네임 중복검사
	public int nickDupCheck(String memberNickname);

	//회원가입
	//*****방법1*****
	//public int signUp(Member member);
	//*****방법2*****
	public int signUp(Member inputMember);


	//회원정보 조회
	public Member selectOne(String memberEmail);

	//회원전체 정보 조회
	public List<Member> selectAll();

	//투두리스트 db삽입
	int todoInsert(Member loginMember);

	//투두리스트 조회
	List<Member> todoSelect(int memberNo);

	//투두리스트 전체 삭제
	int todoAllDelete(int memberNo);

	//투두리스트 선택 삭제
	int todoDelete(int todoNo);

	



}//end of interface
