<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>this is piechart</title>
<script type="text/javascript">
	function showChart(obj) {
		//1 创建Ajax引擎对象
		var ajax;
		if(window.XMLHttpRequest){
			//火狐
			ajax = new XMLHttpRequest();
		}else if (window.ActiveXObject) {
			//IE6、7
			ajax = new ActiveXObject("Msxml2.XMLHTTP");
		}
		
		
		//2 复写onreadystatement函数
		ajax.onreadystatechange=function(){

			//4 判断Ajax状态码
			if(ajax.readyState == 4){
				
				//5 判断响应状态码
				if(ajax.status == 200){
					var img = document.createElement('img');
					img.setAttribute("src","./createPieChart?index="+obj);
					document.getElementById("chart").appendChild(img);
				}else if(ajax.status == 404){
					var showdiv = document.getElementById("showdiv");
					showdiv.innerHTML = "请求资源不存在";
				}else if(ajax.status == 500){
					var showdiv = document.getElementById("showdiv");
					showdiv.innerHTML = "服务器繁忙";
				}
			}
			
		}
		//3 发送请求
		ajax.open("get", "./createPieChart?index="+obj);
		ajax.send(null); 
	}
</script>
</head>
<body>
	<table class="table table-hover">
		<c:forEach var="peoList" items="${finalList }">
			<tr>
				<c:forEach var="mes" items="${peoList }">
					<td>${mes }</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>

	<c:forEach var="subject" items="${finalList[0] }" varStatus="status">
		<c:choose >
			<c:when test="${subject ne '姓名' && subject ne '学号' && subject ne '性别'}">
				<a class="btn btn-default" href="javascript:void(0)" onclick="showChart(${status.index})">${subject }成绩总览</a>
			</c:when>
		</c:choose>
	</c:forEach>
	
	<div id="chart">
	
	</div>
</body>
</html>