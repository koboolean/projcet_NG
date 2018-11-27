<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<style>
#special {
	background: #FCFBFB;
	width: 1400px;
	height: 1050px;
	margin: auto;
	padding-top: 20px;
}

#wrap {
	width: 1300px;
	margin: auto;
}

#title {
	height: 200px;
}

#mainContent {
	height: 1100px;
}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<c:if test="${empty form_menu}">
	<c:set var="form_menu" value="tableView.jsp" />
</c:if>
<body>
	<section id="special">
		<section id="wrap">
			<header id="title">
				<jsp:include page="main.jsp" />
			</header>
		</section>
		<section id="mainContent">
			<jsp:include page="${form_menu}" />
		</section>
	</section>
</body>
</html>