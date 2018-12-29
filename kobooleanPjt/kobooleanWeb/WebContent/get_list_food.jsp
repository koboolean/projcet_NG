<%@ page contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import = "org.json.simple.*"%>
<%@ page import="java.sql.*"%>
<%
	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "koboolean";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	String user_id = request.getParameter("user_id");
	
	String sql = "select food_idx, mobile_img, mobile_food, mobile_pri from foods where user_id = ? order by food_idx desc";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, user_id);
	
	ResultSet rs = pstmt.executeQuery();

	JSONArray array = new JSONArray();
	
	while(rs.next()){
		int mobile_idx = rs.getInt("food_idx");
		String mobile_img = rs.getString("mobile_img");
		String mobile_str1 = rs.getString("mobile_food");
		int mobile_str = rs.getInt("mobile_pri");
		
		String mobile_str2 = String.valueOf(mobile_str);
		JSONObject obj = new JSONObject();
		obj.put("mobile_idx", mobile_idx);
		obj.put("mobile_img", mobile_img);
		obj.put("mobile_str1",mobile_str1);
		obj.put("mobile_str2",mobile_str2);
		
		array.add(obj);
	}
	db.close();
%>
<%=array.toJSONString()%>