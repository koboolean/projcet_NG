<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	// DB 접속하기.
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@203.244.145.214:1521:XE";
	String id = "koboolean";
	String pw = "1234";
	Connection db = DriverManager.getConnection(url, id, pw);

	String sql = "select * from orders";
	PreparedStatement pstmt = db.prepareStatement(sql);

	ResultSet rs = pstmt.executeQuery();

	JSONObject root = new JSONObject();
	JSONArray arr = new JSONArray();
	
	
	
	if (rs.next()) {
		do{
			String order_name = rs.getString("order_name");
			String order_latis = rs.getString("order_latis");
			String order_longs = rs.getString("order_longs");
			
			if(order_name != null && order_latis != null && order_longs != null){
				root.put("order_name", order_name);
				root.put("order_latis", order_latis);
				root.put("order_longs", order_longs);
				
				arr.add(root);
			}
		}while(rs.next());
			
	} else {
		root.put("order_name", "?");
		arr.add(root);
	}
	db.close();
	rs.close();
%>

<%=arr.toJSONString()%>