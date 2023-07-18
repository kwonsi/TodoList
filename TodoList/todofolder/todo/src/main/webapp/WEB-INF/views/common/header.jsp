<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
    
<header>


	    <section></section>
	
</header>
	
<!-- 
	쿼리스트링 : 주소에 담겨져서 전달되는 파라미터를 나타내는 문자열
	주소?name속성=값&name속성=값
	/member/login  ?memberEmail=user01&memberPw=1234
 -->

<nav>
    <ul>
    
    	<%--
    	 <li><a href="${contextPath}">공지사항</a></li>
       
    	 --%>
    	
    <!-- <c:forEach var="boardType" items="${boardTypeList}">
    	
    	<li><a href="${contextPath}/board/list/${boardType.boardCode}">${boardType.boardName}</a></li>
    
    </c:forEach> -->
       <!-- ${boardType.boardCode}의 boardCode는 VO BoardType의 boardCode
       		${boardType.boardName}의 boardName는 VO BoardType의 boardName -->
       
    </ul>
</nav>



