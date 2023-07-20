<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="todoList" value="${todoList}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>KH 커뮤니티</title>

    <link rel="stylesheet" href="resources/css/main-style.css">
    <link rel="stylesheet" href="resources/css/ToDoList-style.css">

    <script src="https://kit.fontawesome.com/a2e8ca0ae3.js" crossorigin="anonymous"></script>

</head>
<body>
    <main>
        <h1>ToDoList</h1>
       
        <div class="main2">



            <div class="todo">
                <form onsubmit="event.preventDefault(); todoplus();">
                    <input class="inputtext" type="text" placeholder="나의 할 일 입력" name="mywork">
                    <button id="inputbtn" type="button" class="fa-regular fa-square-plus" onclick="todoplus()"></button>
                </form>
                
                    <div id="form2">
                
                    </div>
            
                <button class="reset" type="button">전체삭제</button>
        
            
            </div>




            <%-- if - else --%>
            <c:choose>  
                <%-- choose 내부에는 jsp 주석만 사용 --%>
                
                <%-- 로그인이 되어있지 않은 경우 --%>
                   <c:when test="${ empty sessionScope.loginMember }"> 
                
                       <!-- 절대경로 : /community/member/login -->
                        <!-- 상대 경로 (index.jsp) 기준-->
                    <form class="loginSize" action="member/login" method="POST" name="login-form" onsubmit="return loginValidate()">
                       
                        <!-- 아이디(이메일)/비밀번호/로그인버튼 영역 -->
                        <fieldset id="id-pw-area">
            
                            <section>
                                <input type="text" name="memberEmail" placeholder="아이디(이메일)" value="${cookie.saveId.value}">     
                                                                                             <%-- 현재 페이지 쿠키 중 "saveId"의 내용을 출력--%>                   
                                <input type="password" name="memberPw" placeholder="비밀번호">
                            </section>
            
                            <section>
                                <!-- button의 type 기본값은 submit -->
                                <button>로그인</button>
                            </section>
                        </fieldset>
    
                        <%-- 쿠키에 saveId가 있는 경우--%>
                        <c:if test="${ !empty cookie.saveId.value}">

                            <%-- chk 변수 생성(page scope)--%>
                            <c:set var="chk" value="checked"/>

                        </c:if>

                        <label>
                            <!-- checked 속성 : radio/checkbox를 체크하는 속성 -->
                            <input type="checkbox" name="saveId" ${chk}  id="saveId"> 아이디 저장
                        </label>



            
                        <!-- 회원가입 / ID/PW 찾기 영역 -->
                        <article id="signup-find-area">


                            <a href="${contextPath}/member/signUp">회원가입</a> 



                            <span>|</span>
                            <a href="#">ID/PW 찾기</a>
                        </article>
                    </form>
                
                </c:when>
            
            
                <%-- 로그인이 되어있는 경우 --%>
                <c:otherwise>
                    
                    <article class="login-area">
                        <!-- 회원 프로필 이미지 -->
                        <a href="${contextPath}/member/myPage/profile">
                            
                            <c:if test="${empty loginMember.profileImage}">
                                <img src="${contextPath}/resources/images/user.png" id="member-profile">
                            </c:if>

                            <c:if test="${!empty loginMember.profileImage}">
                                <img src="${contextPath}${loginMember.profileImage}" id="member-profile">
                            </c:if>

                        </a>

                        <!-- 회원 정보 + 로그아웃 버튼 -->                                       
                        <div class="my-info">
                            <div>
                                <a href="${contextPath}/member/myPage/info" id="nickname">${loginMember.memberNickname}</a>

                                <a href="${contextPath}/member/logout" id="logout-btn">로그아웃</a>
                            </div>

                            <p>
                                ${loginMember.memberEmail}
                            </p>
                        </div>
                    </article>
                
                </c:otherwise>
            </c:choose>
            
       
           
       


        </div>
           
    
      
           
       

    </main>

    <!-- footer include -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp" />

    <!-- jQuery 라이브러리 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
   
    <script>
        const loginmember = "${loginMember}";
        const memberNo = "${loginMember.memberNo}";
        const todoNo = "${loginMember.todoNo}";
        const contextPath = "${contextPath}";
    </script>

    <!-- main.js 연결 -->
    <script src="${contextPath}/resources/js/main.js"></script>
    <script src="${contextPath}/resources/js/ToDoList.js"></script>


    

</body>
</html>