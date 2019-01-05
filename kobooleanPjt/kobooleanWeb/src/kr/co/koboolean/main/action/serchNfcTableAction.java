package kr.co.koboolean.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.vo.ActionForward;

public class serchNfcTableAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String area_id = request.getParameter("nfc_area_id");
		String area_table_num = request.getParameter("nfc_area_table_num");
		System.out.println(area_id + " " + area_table_num);
		ActionForward forward = new ActionForward();
		forward.setUrl("selecttable://nfcclick?area_id="+area_id+"&area_table_num="+area_table_num);
		forward.setRedirect(true);
		return forward;
	}
}
