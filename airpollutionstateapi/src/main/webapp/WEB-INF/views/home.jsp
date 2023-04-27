<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>한국환경공단_에어코리아_대기오염통계 현황</title>
</head>
<body>
	<h1>
		대기오염통계 현황
	</h1>
	
	<a href="http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst?itemCode=PM10&dataGubun=HOUR&pageNo=1&numOfRows=100&returnType=xml&serviceKey=CuuUnwNPMSK%2B62LBRMBjebxaAU2x1lMX7gLBWInKOstN%2FT5gMofURMsXbbXR50oCS2OE2T1w9zOmllFVdopmMQ%3D%3D">
		시도별 미세먼지(PM10) 실시간 평균정보 조회
	</a>
	
	<br>
	
	<a href="http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst?sidoName=서울&searchCondition=DAILY&pageNo=1&numOfRows=100&returnType=xml&serviceKey=CuuUnwNPMSK%2B62LBRMBjebxaAU2x1lMX7gLBWInKOstN%2FT5gMofURMsXbbXR50oCS2OE2T1w9zOmllFVdopmMQ%3D%3D">
		시군구별 미세먼지(PM10) 실시간 평균정보 조회
	</a>
	
	<br>
	
	<a href="http://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg?inqBginDt=20230416&inqEndDt=20230417&msrstnName=종로구&pageNo=1&numOfRows=100&returnType=xml&serviceKey=CuuUnwNPMSK%2B62LBRMBjebxaAU2x1lMX7gLBWInKOstN%2FT5gMofURMsXbbXR50oCS2OE2T1w9zOmllFVdopmMQ%3D%3D">
		측정소별 미세먼지(PM10) 실시간 일평균 정보 조회 
	</a>
	
	<br>
	<br>
		
	<form method="get" action="/result.do">
		<label for="city">도시</label> 
		<select id="city" name="city" size="1">
			<option value="">선택하세요.</option>
			<option value="서울">서울</option>
			<option value="부산">부산</option>
			<option value="대구">대구</option>
			<option value="인천">인천</option>
			<option value="광주">광주</option>
			<option value="대전">대전</option>
			<option value="울산">울산</option>
		</select>
		<input type="submit" value="확인" style="margin-left:50px">
	</form>

</body>
</html>