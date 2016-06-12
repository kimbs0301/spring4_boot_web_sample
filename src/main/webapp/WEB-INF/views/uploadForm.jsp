<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<spring:eval expression="@configProperties" var="webProp" scope="request"/>
<title>spring boot web example</title>
</head>
<body>
	<div>
		<form method="POST" enctype="multipart/form-data" action="${webProp['context.path']}/file/upload">
			<table>
				<tr>
					<td>File to upload:</td>
					<td><input type="file" name="file" id="file" /></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Upload" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>