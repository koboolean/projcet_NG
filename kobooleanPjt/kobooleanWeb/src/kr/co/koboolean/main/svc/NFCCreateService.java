package kr.co.koboolean.main.svc;

import static kr.co.koboolean.db.JdbcUtil.close;
import static kr.co.koboolean.db.JdbcUtil.commit;
import static kr.co.koboolean.db.JdbcUtil.getConnection;
import static kr.co.koboolean.db.JdbcUtil.rollback;

import java.sql.Connection;

import kr.co.koboolean.main.dao.OrderDAO;
import kr.co.koboolean.vo.Orders;

public class NFCCreateService {

	public boolean createOrderService(Orders orders) {
		Connection con = getConnection();
		OrderDAO orderDAO = OrderDAO.getInstance();
		orderDAO.setConnection(con);
		boolean successCount = false;
		int count = orderDAO.createOrder(orders);
		if(count > 0) {
			successCount = true;
			commit(con);
		}else {
			rollback(con);
		}
		close(con);
		
		return successCount;
	}

}
