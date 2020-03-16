<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../commons/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
</head>
<body>
	<table class="table table-hover">
		<tr >
			<td><h1>根据模板总和文件</h1></td>
		</tr>
		<tr class="bg-info">
			<td>
				<form action="../fileupload" enctype="multipart/form-data" method="post">
					<div class="form-group">
		    		<label for="exampleInputFile">File input</label>
		    		<input type="file" name="template" id="exampleInputFile"><br>
		    		<p class="help-block">选择模板文件,大小限制为1M</p>
		 			</div>
					<div class="form-group">
		    		<label for="exampleInputFile">File input</label>
		    		<input type="file" name="files" multiple="multiple" id="exampleInputFile1"><br>
		    		<p class="help-block">选择文件源,大小限制为1M</p>
		 			</div>
		 			<div class="form-group">
		 			<input type="hidden" name="addr" value="<%=request.getRemoteAddr()%>">
		 			</div>
					<input type="submit" class="btn btn-default" value="确定选择">
					<input type="reset" class="btn btn-default" value="重新选择">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>