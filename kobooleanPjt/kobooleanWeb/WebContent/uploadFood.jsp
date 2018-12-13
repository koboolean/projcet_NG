<%@ page contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.oreilly.servlet.*" %>
<%@ page import = "com.oreilly.servlet.multipart.*" %>
<%
	// 한글처리
	request.setCharacterEncoding("utf-8");
	// 절대경로 지정
	String path = getServletContext().getRealPath("food");
	System.out.print(path);
	
	int max = 1024 * 1024 * 100;
	
	DefaultFileRenamePolicy policy = new DefaultFileRenamePolicy();
	
	MultipartRequest mr = new MultipartRequest(request, path, max, "utf-8", policy);
	
	String mobile_str1 = mr.getParameter("food_write");
	String mobile_str2 = mr.getParameter("price_write");
	String mobile_img = mr.getFilesystemName("mobile_img");
	
	System.out.println(mobile_img);
	System.out.println(mobile_str1);
	System.out.println(mobile_str2);
	
%>