<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<style>
body {
	background: #f2f4f7;
	margin: 0;
	padding: 0;
}

#title_background {
	top: 0;
	height: 160px;
	background: white;
	border-bottom: 1px solid #dee3eb;
}

#wrap {
	margin: auto;
	width: 1108px;
	height: 151px;
	
}

#title {
	height: 151px;
}

#wrap2 {
	margin: auto;
	width: 1108px;
	height: 900px;
	background: white;
	border-left: 1px solid #f1f3f6;
	border-right: 1px solid #f1f3f6;
}

#mainContent {
	height: 650px;
}

#footer {
	border-top: 1px solid #f1f3f6;
	height: 480px;
	height: 250px;
	width: 1108px;
}
</style>
<meta charset="UTF-8">
<title>NG 콕</title>
</head>
<c:if test="${empty form_menu}">
	<c:set var="form_menu" value="tableView.jsp" />
</c:if>
<body>
	<div id="title_background">
		<section id="wrap">
			<header id="title">
				<jsp:include page="main.jsp" />
			</header>
		</section>
	</div>
	<section id="wrap2">
		<section id="mainContent">
			<jsp:include page="${form_menu}" />
		</section>
		<footer id="bottom">
			<section id="footer">
				<jsp:include page="footer.html"></jsp:include>
			</section>
		</footer>
	</section>

</body>
</html>