/**
 * 
 */
package kr.co.koboolean.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

/**
 * @author user
 *
 */
public class nfcStoreAction implements Action {

	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		request.setAttribute("form_menu", "nfcStore.jsp");
		forward.setUrl("layout.jsp");
		return forward;
	}

}
