<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="$(it.usersList)" var="user" >

<p> User Name <c:out value="$(user.name)"></c:out></p>
<p> User Email <c:out value="$(user.email)"></c:out></p>
<br><br>
</c:forEach>
</body>
</html>