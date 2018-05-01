<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Edit account</h2>
	<form:form commandName="currentAccount">
	Account Name:<form:input path="accountName" />
		<br>
	Balance:<form:input path="balance" />
		<br>
	Account number::<form:input path="lastFourDigits" />
		<br>
		<p>(Optional last 4 digits)</p>
		Currency:<form:select path="currency">
			<br>
			<form:option value="-" label="--Select Currency" />
			<form:options items="${allCurrencies}" />
		</form:select>
		Account type:<form:select path="type">
			<form:option value="type" />
			<form:options items="${allTypes}" />
		</form:select>
		<input type="submit" value="Submit" />
	</form:form>
</body>
</html>