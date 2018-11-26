package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.main.svc.AreaService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Areas;

public class insertFoodAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Areas area = new Areas();
		area.setArea_id((String) request.getParameter("area_id"));
		area.setArea_name((String) request.getParameter("area_name"));
		area.setArea_address((String) request.getParameter("area_address"));
		area.setArea_num((String) request.getParameter("area_num"));
		area.setArea_intro((String) request.getParameter("area_intro"));
		
		
		
		AreaService areaService = new AreaService();
		boolean success = areaService.insertArea(area);
		
		ActionForward forward = null;
		
		if(success == true) {
			forward = new ActionForward();
			HttpSession session = request.getSession();
			session.setAttribute("area_name", area.getArea_name());
			forward.setUrl("main.jsp");
			forward.setRedirect(true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>");
	        out.println("alert('상호 정보를 정확하게 입력해 주십시오.')");
	        out.println("history.back()");
	        out.println("</script>");
		}
		
		return forward;
	}

}
