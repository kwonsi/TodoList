package edu.kh.comm.member.model.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MyPageDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	/**회원정보 수정 DAO
	 * @param paramMap
	 * @return
	 */
	public int updateInfo(Map<String, Object> paramMap) {
		return sqlSession.update("myPageMapper.updateInfo", paramMap);
	}


	/**비밀번호 변경 DAO
	 * @param paramMap
	 * @return
	 */
	public int updatePw(Map<String, Object> paramMap) {
		return sqlSession.update("myPageMapper.updatePw", paramMap);
	}


	/**회원탈퇴 DAO
	 * @param memberNo
	 * @return
	 */
	public int secession(int memberNo) {
		return sqlSession.update("myPageMapper.secession", memberNo);
	}

	/**현재 로그인한 회원 비밀번호 조회 DAO
	 * @param memberNo
	 * @return
	 */
	public String checkPw(int memberNo) {

		return sqlSession.selectOne("myPageMapper.checkPw", memberNo);
	}


	/**프로필 이미지 변경
	 * @param map
	 * @return
	 */
	public int updateProfile(Map<String, Object> map) {



		return sqlSession.update("myPageMapper.updateProfile", map);
	}










}
