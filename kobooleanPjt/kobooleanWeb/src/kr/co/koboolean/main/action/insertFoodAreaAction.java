package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.javafx.geom.Area;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.main.svc.AreaService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Areas;

public class insertFoodAreaAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ActionForward forward = null;

		AreaService service = new AreaService();
		Areas area = service.searchArea(user_id);

		System.out.println(area.getArea_id());
		if (user_id != null) {
			if (area.getArea_id() != null) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('상호정보 수정을 선택하여 수정해 주세요')");
				out.println("location.href='searchArea.main'");
				out.println("</script>");
			} else {
				System.out.println("2");
				forward = new ActionForward();
				request.setAttribute("form_menu", "insertFoodArea.jsp");
				forward.setUrl("layout.jsp");
			}
		} else {
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
