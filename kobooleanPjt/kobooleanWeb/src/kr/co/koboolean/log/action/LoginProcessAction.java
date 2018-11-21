package kr.co.koboolean.log.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.log.svc.SigninProcessService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Users;

public class LoginProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Users user = new Users();
		String user_id = request.getParameter("user_id");
		user.setUser_id(user_id);
		user.setUser_pw(request.getParameter("user_pw"));
		String name = "";
		SigninProcessService signinService = new SigninProcessService();
		name = signinService.loginUserService(user);
		
		ActionForward forward = null;
		
		if(name != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user_id);
			forward = new ActionForward();
			forward.setUrl("main.jsp");
			forward.setRedirect(true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>");
	        out.println("alert('아이디 혹은 비밀번호가 잘못되었습니다.')");
	        out.println("history.back()");
	        out.println("</script>");
		}
		
		
		return forward;
	}

}
