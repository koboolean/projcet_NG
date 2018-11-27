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
	<form action="login.main" method="post">
		<input type = "text" placeholder="아이디" name = "user_id">
		<input type = "password" placehorder = "비밀번호" name = "user_pw">
		<input type = "submit" value="로그인">
	</form>
	<form action = "signIn.main">
		<input type="submit" value = "회원가입">
	</form>
</body>
</html>
