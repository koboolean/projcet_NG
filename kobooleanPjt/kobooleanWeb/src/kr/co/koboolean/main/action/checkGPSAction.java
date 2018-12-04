package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.main.svc.NFCCreateService;
import kr.co.koboolean.vo.ActionForward;

public class checkGPSAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		ActionForward forward = new ActionForward();
		
		NFCCreateService nfcCreateService = new NFCCreateService();
		boolean success = nfcCreateService.searchGPS(user_id);
		if(success == true) {
			forward.setUrl("layout.jsp");
			forward.setRedirect(true);
		}else {
	        request.setAttribute("form_menu", "nfcStore.jsp");
	        request.setAttribute("error", "1");
	        forward.setUrl("layout.jsp");
		}
		return forward;
	}

}
