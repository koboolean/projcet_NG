/**
 * 
 */
package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

/**
 * @author user
 *
 */
public class nfcStoreAction implements Action {

	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ActionForward forward = new ActionForward();
		if(user_id != null) {
			request.setAttribute("form_menu", "nfcStore.jsp");
			forward.setUrl("layout.jsp");	
		}
		else {
			response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>");
	        out.println("alert('로그인을 먼저 해주세요')");
	        out.println("location.href='loginForm.main'");
	        out.println("</script>");
		}
		
		return forward;
	}

}
