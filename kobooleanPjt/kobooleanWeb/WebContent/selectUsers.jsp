<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.*"%>
<%
	String order_id = request.getParameter("web_id");
	String order_pw = request.getParameter("web_pw");
	
	// DB 접속하기.
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@203.244.145.214:1521:XE";
	String id = "koboolean";
	String pw = "1234";
	Connection db = DriverManager.getConnection(url, id, pw);

	String sql = "select user_name from users where user_id = ? and user_pw = ?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, order_id);
	pstmt.setString(2, order_pw);
	ResultSet rs = pstmt.executeQuery();
<<<<<<< HEAD
	
	JSONObject root = new JSONObject();
	
	if(rs.next()){
	String user_name = rs.getString("user_name");

	root.put("user_name", user_name);
	}
	else{
	root.put("user_name", "?");
=======

	JSONObject root = new JSONObject();
	
	if(rs.next()){
		
	String user_name = rs.getString("user_name");

	root.put("user_name", user_name);
	}else{
		root.put("user_name","?");
>>>>>>> 조현준
	}
	db.close();
%>

<%=root.toJSONString()%>

