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
	<form action="nfcCreate.main" method="post">
		<p>배치 테이블 수에 맞게 NFC가 부착되니 정확한 테이블 수를 입력하셔야 합니다.</p>
		<input type="text" placeholder="매장 명" name = "order_name">
		<input type="text" placeholder="매장 주소" name = "order_address">
		<input type="text" placeholder="사업자 번호" name="order_num"> 
		<input type="text" placeholder="배치 테이블 수" name="order_table"> 
		<input type="text" placeholder="사업자 전화번호" name="order_phone"> 
		<input type="submit" value="발급받기">
	</form>
</body>
</html>