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
	<input type="button" onclick="location.href = 'insertFoodAreaGo.main'"
		value="상호정보 등록">
	<input type="button" onclick="location.href = 'searchArea.main'"
		value="상호정보 수정">		
	<input type="button" onclick="location.href='nfcStore.main'"
		value="NFC 발급신청">
	<input type="button" onclick = "location.href='insertFood'" value="음식정보 입력">
	<input type="button" onclick="location.href = 'logout.main'"
		value="로그아웃">
</body>
</html>