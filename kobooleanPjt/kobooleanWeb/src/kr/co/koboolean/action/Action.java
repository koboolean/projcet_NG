package kr.co.koboolean.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.vo.ActionForward;


public interface Action {
	// action �������̽� ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
