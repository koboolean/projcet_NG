<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="signInProcess.main" method="post">
		<input type="text" placeholder="아이디" name = "user_id">
		<input type="password" placeholder="비밀번호" name = "user_pw">
		<input type="text" placeholder="이름" name = "user_name">
		<input type="text" placeholder="전화번호" name = "user_phone">
		<input type = "submit" value="회원가입">
	</form>
</body>
</html>