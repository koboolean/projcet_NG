<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.*"%>
<%
	String order_latis = request.getParameter("order_latis");
	String order_longs = request.getParameter("order_longs");
	String order_id = request.getParameter("order_id");
	// DB 접속하기.
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@203.244.145.214:1521:XE";
	String id = "koboolean";
	String pw = "1234";
	Connection db = DriverManager.getConnection(url, id, pw);

	String sql = "update orders set order_latis = ? , order_longs = ? where order_id = ?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, order_latis);
	pstmt.setString(2, order_longs);
	pstmt.setString(3, order_id);
	int success = pstmt.executeUpdate();

	JSONObject root = new JSONObject();
	if (success > 0) {
		root.put("order_id", order_id);
	} else {
		root.put("order_id", "?");
	}
	db.close();
%>

<%=root.toJSONString()%>

