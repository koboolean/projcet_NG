<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="java.sql.*"%>
<%
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "koboolean";
	String pw = "1234";

	Connection db = DriverManager.getConnection(url, id, pw);
	String area_name = "";
	
	String user_id = request.getParameter("user_id");

	String sql = "select area_name from areas where area_id = ?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, user_id);

	ResultSet rs = pstmt.executeQuery();

	if (rs.next()) {
		area_name = rs.getString("area_name");
	}
	
	JSONObject obj = new JSONObject();

	if (area_name.equals("")) {
		obj.put("area_name", "?");
	} else {
		obj.put("area_name", area_name);
	}
	db.close();
%>
<%=obj.toJSONString()%>