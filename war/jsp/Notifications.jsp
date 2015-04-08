<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<p> Reciرedwqr </p>
<body>
<c:forEach items="${it.userList}" var="requests" >
<p> Reciever <c:out value="${requests.Reciever}"></c:out></p>
"
<br><br>
</c:forEach>
</body>
</html>