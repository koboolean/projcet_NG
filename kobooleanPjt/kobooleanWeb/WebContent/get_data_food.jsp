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

	String idx = request.getParameter("mobile_idx");
	int mobile_idx = Integer.parseInt(idx);

	String sql = "select mobile_img, mobile_food, mobile_pri from foods where food_idx = ?";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setInt(1, mobile_idx);

	ResultSet rs = pstmt.executeQuery();

	rs.next();

	JSONObject obj = new JSONObject();

	String mobile_img = rs.getString("mobile_img");
	String mobile_str1 = rs.getString("mobile_food");
	int mobile_str = rs.getInt("mobile_pri");
	
	String mobile_str2 = String.valueOf(mobile_str);
	
	obj.put("mobile_img", mobile_img);
	obj.put("mobile_str1", mobile_str1);
	obj.put("mobile_str2", mobile_str2);

	db.close();
%>
<%=obj.toJSONString()%>