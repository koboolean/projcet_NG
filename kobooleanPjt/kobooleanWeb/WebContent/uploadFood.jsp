<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.oreilly.servlet.*" %>
<%@ page import = "com.oreilly.servlet.multipart.*" %>
<%@ page import = "java.sql.*"%>
<%
	// 한글처리
	request.setCharacterEncoding("utf-8");


	Class.forName("oracle.jdbc.OracleDriver");
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "koboolean";
	String pw = "1234";
	
	Connection db = DriverManager.getConnection(url, id, pw);
	
	
	// 절대경로 지정
	String path = getServletContext().getRealPath("food");
	
	int max = 1024 * 1024 * 100;
	
	DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
	
	MultipartRequest mr = new MultipartRequest(request, path, max, "utf-8", policy);
	
	String user_id = mr.getParameter("user_id");
	String mobile_str1 = mr.getParameter("food_write");
	String mobile_str2 = mr.getParameter("price_write");
	String mobile_img = mr.getFilesystemName("mobile_img");
	
	System.out.println(mobile_img);
	System.out.println(mobile_str1);
	System.out.println(mobile_str2);
	System.out.println(user_id); 
	
	
	String sql = "insert into foods (food_idx, user_id, mobile_img, mobile_food, mobile_pri) values (food_seq.nextval, ?, ?, ?, ?)";
	PreparedStatement pstmt = db.prepareStatement(sql);
	pstmt.setString(1, user_id);
	pstmt.setString(2, mobile_img);
	pstmt.setString(3, mobile_str1);
	pstmt.setInt(4, Integer.parseInt(mobile_str2));
	
	pstmt.execute();
	
	db.close();
	
%>
OK