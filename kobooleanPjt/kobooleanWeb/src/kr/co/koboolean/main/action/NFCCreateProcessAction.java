package kr.co.koboolean.main.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.koboolean.action.Action;
import kr.co.koboolean.main.svc.NFCCreateService;
import kr.co.koboolean.vo.ActionForward;
import kr.co.koboolean.vo.Orders;

public class NFCCreateProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Orders orders = new Orders();
		HttpSession session = request.getSession();
		orders.setOrder_id((String) session.getAttribute("user_id"));
		orders.setOrder_num(request.getParameter("order_num"));
		orders.setOrder_name(request.getParameter("order_name"));
		orders.setOrder_address(request.getParameter("order_address"));
		orders.setOrder_table(Integer.parseInt(request.getParameter("order_table")));
		orders.setOrder_phone(request.getParameter("order_phone"));
	
		ActionForward forward = null;
		
		NFCCreateService nfcCreateService = new NFCCreateService();
		boolean success = nfcCreateService.createOrderService(orders);
		
		if(success) {
			forward = new ActionForward();
			forward.setUrl("nfcCreateSuccess.jsp");
			forward.setRedirect(true);
		}else {
			response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>");
	        out.println("alert('이미 정보를 입력하셨거나, 누락된 정보가 존재합니다. 확인 부탁드립니다.')");
	        out.println("history.back()");
	        out.println("</script>");
		}
		return forward;
	}

}
