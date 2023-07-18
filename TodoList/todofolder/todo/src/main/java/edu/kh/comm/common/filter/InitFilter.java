package edu.kh.comm.common.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "initFilter", urlPatterns = "/*")
// -> /* :모든경로를 의미
public class InitFilter extends HttpFilter implements Filter {
	//Logger / Debug Mode 사용하기

	//Logger객체 생성(해당클래스에 대한 log를 출력하는 객체)
	private Logger logger = LoggerFactory.getLogger(InitFilter.class);
	// LoggerFactory.getLogger(클래스이름);

	//필터가 생성될 때 생성
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		logger.info("초기화 필터 생성");
		//logger를 이용해 출력하는 방법
		//trace-debug-info-warn-error
		//-> 주로 debug, info, error 사용

		//debug :개발의 흐름을 파악할 때(해당코드가 실행됬는지, 파라미터가 현재 무슨값인지 확인할 때)
		//info :메소드 실행 파악

	}

	//필터가 파괴될 때 실행(서버는 켜져있는데 백엔드 코드 수정됬을 때)
	@Override
	public void destroy() {

		logger.info("초기화 필터 파괴");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// application 내장 객체 얻어오기
				ServletContext application = request.getServletContext();

				// 최상위 주소 얻어오기
				String contextPath =  ( (HttpServletRequest)request ).getContextPath();
											// 다운캐스팅
				// 세팅
				application.setAttribute("contextPath", contextPath);

		chain.doFilter(request, response);
	}

}


















