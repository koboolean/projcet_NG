package kr.co.koboolean.log.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		session.removeAttribute("user_id");
		ActionForward forward = new ActionForward();
        
        forward.setUrl("index.jsp");
		
        
		return forward;
	}

}
