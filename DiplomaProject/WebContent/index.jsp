<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../commons/head.jsp" %>
<title>office操作小工具</title>
<script type="text/javascript">
	function fileupload() {
		document.getElementById("if").src = "CreateFile/FileUpLoad.jsp";
	}
	function fileuploadtopie() {
		document.getElementById("if").src = "PieChart/FileUpLoadToPie.jsp";
	}
</script>
</head>
<body>
	<div id="left">
		<div class="bg-primary" style="width: 20%; height: 800px; float: left;">
			<h2 class="text-center">office操作小工具</h2>
			<ul class="list-unstyled">
				<li class="bg-info" style="text-align: center;"><a href="javascript: void(0)" onclick="fileupload()"
					title="文件导入">生 成 文 件</a></li>
				<li class="bg-info" style="text-align: center;"><a href="javascript: void(0)" onclick="fileuploadtopie()"
				title="文件导入">信 息 统 计</a></li>
			</ul>
		</div>
		
		<div id="right" style="width: 80%; height: 800px; float: left;">
			<iframe id="if" src="CreateFile/FileUpLoad.jsp"  style="width: 100%; height: 800px;"></iframe>
		</div>
	</div>
</body>
</html>