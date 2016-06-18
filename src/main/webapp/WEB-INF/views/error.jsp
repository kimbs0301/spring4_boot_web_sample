<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<pre><b>URL : </b></pre>
	<pre>
		<c:out value="${url}" />
	</pre>
	<pre><b>errorCode : </b></pre>
	<pre>
		<c:out value="${errorCode}" />
	</pre>
	<pre><b>errorMsg : </b></pre>
	<pre>
		<c:out value="${errorMsg}" />
	</pre>
	<pre><b>stackTrace : </b></pre>
	<pre>
		<c:out value="${stackTrace}" />
	</pre>
</body>
</html>