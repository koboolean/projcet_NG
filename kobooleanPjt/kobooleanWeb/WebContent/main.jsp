<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style>
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	${user_id}님 반갑습니다.
	
	<br>
	<input type="button" onclick="location.href = 'insertFoodArea.jsp'"
		value="상호정보 등록">
	<input type="button" onclick="location.href='nfcStore.jsp'"
		value="NFC 발급신청">
	<input type="button" onclick="location.href = 'logout.main'"
		value="로그아웃">
		
	<%-- <jsp:include page="searchArea.jsp"></jsp:include> --%>
</body>
</html>