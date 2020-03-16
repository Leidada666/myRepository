<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结果</title>
</head>
<body>
	<c:if test="${requestScope.result eq '0' }">
		<a class="btn btn-default" href="javascript:history.back(-1)">请重试</a>
	</c:if>
	<c:if test="${requestScope.result ne '0' }">
		<form action="./download" method="post">
			<input type="hidden" name="file" value="${requestScope.filepath }">
			<input class="btn btn-default" type="submit" value="下载文件">
		</form>
	</c:if>
	
	<table class="table table-hover">
		<c:forEach var="message" items="${requestScope.message }">
			<tr>
				<td>${message }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>