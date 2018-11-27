<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section id="writeFormArea">
		<form action="insertFoodArea.main?area_id=${user_id}" method="POST">
			<table>
				<tr>
					<td class="td_left"><label for="user_id">대표자 아이디</label></td>
					<td class="td_right" id="area_id">${user_id}</td>
				</tr>
				<tr>
					<td class="td_left">상호 명</td>
					<td class="td_right"><input type="text" class="area_name"
						name="area_name"></td>
				</tr>
				<tr>
					<td class="td_left"><label for="area_address">위치정보</label></td>
					<td class="td_right"><input class="text_name" type="text"
						name="area_address"></td>
				</tr>
				<tr>
					<td class="td_left"><label for="area_num">사업자 번호</label></td>
					<td class="td_right"><input class="text_name" type="text"
						name="area_num"></td>
				</tr>
				<tr>
					<td class="td_left_content"><label for="area_intro">가게
							소개</label></td>
					<td class="td_right_content"><textarea rows="23.5" cols="100"
							name="area_intro" id="area_intro"></textarea></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="정보 등록"> <input
						type="reset" value="다시 작성 "></td>
				</tr>
			</table>
		</form>
	</section>
</body>
</html>