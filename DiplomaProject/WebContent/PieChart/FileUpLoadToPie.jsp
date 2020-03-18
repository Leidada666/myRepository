<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择文件</title>
</head>
<body>

<table class="table table-hover">
		<tr >
			<td><h1>选择文件</h1></td>
		</tr>
		<tr class="bg-info">
			<td>
				<form action="../getTitle" enctype="multipart/form-data" method="post">
					<div class="form-group">
		    		<label for="exampleInputFile">File input</label>
		    		<input type="file" name="files" multiple="multiple">
		    		<p class="help-block">选择文件源,大小限制为1M</p>
		    		<p class="help-block">要求：文件具有统一格式，顺序也得一致</p>
		 			</div>
					<input type="submit" class="btn btn-default" value="确定选择">
					<input type="reset" class="btn btn-default" value="重新选择">
				</form>
			</td>
		</tr>
</table>
</body>
</html>