<%@ page contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.*"%>
<%@ page import="java.sql.*"%>
<%
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "koboolean";
	String pw = "1234";

	Connection db = DriverManager.getConnection(url, id, pw);

	String mobile_idx = request.getParameter("mobile_idx");

	String sql = "delete from foods where food_idx = ?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, mobile_idx);

	int ok = pstmt.executeUpdate();

	JSONArray array = new JSONArray();

	JSONObject obj = new JSONObject();
	if(ok > 0){
		obj.put("delete_ok", "OK");	
	}else{
		obj.put("delete_ok", "?");
	}

	db.close();
%>
<%=obj.toJSONString()%>