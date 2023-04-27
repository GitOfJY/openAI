<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>한국환경공단_에어코리아_대기오염통계 현황</title>
</head>
<body>

	<%
		String city = request.getParameter("city");
	%>
	
	<%= city%> 실시간 평균정보 조회
	
	<h3>조회 결과</h3>
	${result}


</body>
</html>