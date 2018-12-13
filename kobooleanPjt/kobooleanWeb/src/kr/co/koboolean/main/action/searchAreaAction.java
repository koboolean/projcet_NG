package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.main.svc.AreaService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Areas;

public class searchAreaAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		AreaService areaService = new AreaService();
		Areas success = areaService.searchArea(user_id);
		
		ActionForward forward = null;
	
		if(user_id != null) {
			if(success.getArea_id().equals(user_id)) {
				request.setAttribute("area", success);
				forward = new ActionForward();
				request.setAttribute("form_menu", "searchAreaSuccess.jsp");
				forward.setUrl("layout.jsp");
			}else {
				response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        out.println("<script>");
		        out.println("alert('현재 저장해놓은 정보가 없습니다.')");
		        out.println("history.back()");
		        out.println("</script>");
			}
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
