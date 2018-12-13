package kr.co.koboolean.log.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.log.svc.SigninProcessService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Users;

public class SigninProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = new Users();
		user.setUser_id(request.getParameter("user_id"));
		user.setUser_pw(request.getParameter("user_pw"));
		user.setUser_name(request.getParameter("user_name"));
		user.setUser_phone(request.getParameter("user_phone"));

		System.out.println(user.getUser_name());

		SigninProcessService signinService = new SigninProcessService();
		boolean success = signinService.createUserService(user);
		ActionForward forward = null;

		if (success == true) {
			forward = new ActionForward();
			forward.setUrl("loginForm.main");
			forward.setRedirect(true);
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정보입력이 누락되었거나 현재 존재하는 아이디입니다.')");
			out.println("history.back()");
			out.println("</script>");
		}

		return forward;
	}

}
