package kr.co.koboolean.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.vo.ActionForward;


public interface Action {
	// action 인터페이스 정의
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
