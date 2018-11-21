package kr.co.koboolean.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

public class SelectGPSAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setUrl("posturl://postgps");
		forward.setRedirect(true);
		return forward;
	}

}
