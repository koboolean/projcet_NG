<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
a {
	text-decoration: none;
}

.jbMenu {
	text-align: center;
	width: 1108px;
	background-color: white;
	height: 90px;
	padding-top: 20px;
}

.keyword_wrap {
	margin: auto;
	margin-top: 0;
	width: 450px;
	height: 54px;
	border: 1px #ddd solid;
	border-radius: 4px;
}

.search_box {
	width: 95%;
	height: 100%;
	font-size: 25px;
	margin-left: 2%;
	border: none;
}

.imgSize {
	margin-top: 15%;
	height: 35px;
	width: 35px;
}

.search_pro {
	width: 95%;
}

.search_img {
	width: 5%;
}

#main-navigation {
	border-top: 1px solid #f1f3f6;
	height: 40px;
}

.outer-menu-item {
	float: left;
	position: relative;
}

.outer-menu-item:hover {
	background: black;
	color: white;
}

.menu-title {
	display: block;
	height: 30px;
	line-height: 30px;
	text-align: center;
	padding-right: 20px;
	padding-left: 20px;
	text-align: center;
}

ul>li:hover>ul {
	display: block;
	background: white;
}

.inner-menu {
	display: none;
	position: absolute;
	left: 0;
	padding-left: 0;
	width: 100%;
	background: white;
	box-shadow: 0 2px 6px rgba(5, 5, 5, 0.9);
	z-index: 1000;
	text-align: center;
}

.inner-menu-item>a {
	display: block;
	padding: 5px 10px;
	color: black;
}

.inner-menu-item>a:hover {
	background: black;
	color: white;
}

ul {
	list-style: none;
}

#login_menu {
	margin-left: 30%;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="jbMenu">
		<div class="keyword_wrap">
			<table>
				<tr>
					<td class="search_pro"><input class="search_box" type="text"
						placeholder="정보 검색"></td>
					<td class="search_img"><img src="images/search.png"
						class="imgSize"></td>
				</tr>
			</table>
		</div>
	</div>
		<nav id="main-navigation">
			<div class="pull-left">
				<ul class="outer-menu">
					<li class="outer-menu-item"><a class="menu-title"
						href="insertFoodAreaGo.main">상호정보 등록</a></li>
					<li class="outer-menu-item"><a class="menu-title"
						href="searchArea.main">상호정보 수정</a></li>
					<li class="outer-menu-item"><a class="menu-title"
						href="nfcStore.main">NFC 발급신청</a></li>
					<c:if test="${empty user_id}">
						<li class="outer-menu-item" id="login_menu"><a
							href="loginForm.main" class="menu-title">로그인</a>
						<li>
					</c:if>
					<c:if test="${not empty user_id}">
						<li class="outer-menu-item" id="login_menu"><span
							class="menu-title">${user_id}님 반갑습니다.</span>
							<ul class="inner-menu">
								<li class="inner-menu-item"><a href="logout.main">로그아웃</a>
								</li>
							</ul></li>
					</c:if>
				</ul>
			</div>
		</nav>

</body>
</html>