package kr.co.koboolean.log.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

public class SigninPageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setUrl("signInPage.jsp");
		return forward;
	}

}
