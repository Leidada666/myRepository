<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>title</title>
</head>
<body>
	<c:if test="${requestScope.result == 1 }">
		<form action="./getFileDataByTitle" method="post">
		
		
			<table class="table table-hover">
				<tr>
					<c:forEach var="list" items="${requestScope.list }" varStatus="status">
						<td>
							<c:choose>
								<c:when test="${list eq '学号' || list eq '姓名' || list eq '性别'}">
									<div class="checkbox">
							   			<label>
							   				<input type="checkbox" name="titles" checked="checked" onclick="return false;" value="${status.index }">${list }
							   			</label>
									</div>
								</c:when>
								<c:otherwise>
									<div class="checkbox">
							   			<label>
											<input type="checkbox" name="titles" value="${status.index  }">${list }			
							   			</label>
									</div>
								</c:otherwise>
							</c:choose>
						</td>
					</c:forEach>
				</tr>
			
			</table>
			<tr>
				<input class="btn btn-default" type="submit" value="检索">
			</tr>
		</form>
	</c:if>
	
	<c:if test="${requestScope.result == 0 }">
		<table class="table table-hover">
			<tr>
				<td>
					<a class="btn btn-default" href="javascript:history.back(-1)">请重试</a>
				</td>
			</tr>
			<tr>
				<td>
					<p>${requestScope.message }</p>
				</td>
			</tr>
		</table>
		
		
		
	</c:if>
</body>
</html>