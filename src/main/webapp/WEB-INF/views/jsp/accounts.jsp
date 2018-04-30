<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Your accounts:</h1>
	<c:if test="${ fn:length(sessionScope.user.accounts)==0 }">
		<p>No accounts added yet</p>
	</c:if>
	<c:if test="${ fn:length(sessionScope.user.accounts)>0 }">
		<c:forEach items="${ sessionScope.user.accounts }" var="account">
			<h4>
				<c:out value="${ account.accountName }"></c:out>
			</h4>
			<table border="1">
				<tr>
					<td><c:out value="${account.balance}" /></td>
					<%-- 				<td><c:out value="${account.lastfourdigits}" /></td>
				<td><c:out value="${account.percentage}" /></td>
				<td><c:out value="${account.paymentDueDay}" /></td> --%>
					<td><c:out value="${account.currency}" /></td>
					<td><c:out value="${account.type}" /></td>
				</tr>
			</table>
			<hr>
		</c:forEach>
	</c:if>
</body>
</html>