package kr.co.koboolean.main.dao;

import static kr.co.koboolean.db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import kr.co.koboolean.vo.Orders;


public class OrderDAO {
	// singleton 패턴사용
	private static OrderDAO orderDAO;
	private Connection con;

	private OrderDAO() {

	}

	public static OrderDAO getInstance() {
		if (orderDAO == null) {
			orderDAO = new OrderDAO();
		}
		return orderDAO;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

	public int createOrder(Orders order) {
		int insertMem = 0;
		PreparedStatement pstmt = null;

		String sql = "INSERT INTO ORDERS(order_id, order_num, order_name, order_address, order_table, order_phone)"
				+ "VALUES(?,?,?,?,?,?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, order.getOrder_id());
			pstmt.setString(2, order.getOrder_num());
			pstmt.setString(3, order.getOrder_name());
			pstmt.setString(4, order.getOrder_address());
			pstmt.setInt(5, order.getOrder_table());
			pstmt.setString(6, order.getOrder_phone());
			
			insertMem = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return insertMem;
	}


	
	
}
