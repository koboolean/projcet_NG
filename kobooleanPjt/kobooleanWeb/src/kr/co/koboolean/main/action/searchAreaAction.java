package kr.co.koboolean.main.action;

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
		System.out.println(success.getArea_name());
		ActionForward forward = null;
		if(success.getArea_id().equals(user_id)) {
			request.setAttribute("area", success);
			forward = new ActionForward();
			request.setAttribute("form_menu", "searchAreaSuccess.jsp");
			forward.setUrl("layout.jsp");
		}
		
		return forward;
	}

}
