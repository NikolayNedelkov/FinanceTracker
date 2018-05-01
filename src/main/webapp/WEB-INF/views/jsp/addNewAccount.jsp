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

	<form:form commandName="account">
	Account Name:<form:input path="accountName" /><br>
	Balance:<form:input path="balance" /><br>
	Account number::<form:input path="lastFourDigits" /><br>
		<p>(Optional last 4 digits)</p>
	
	Currency:<form:select path="currency"><br>
			<form:option value="-" label="--Select Currency" />
			<form:options items="${allCurrencies}" />
		</form:select>
	
	
		Account type:<form:select path="type"><br>
			<%-- <form:option value="-" label="--Select Currency" /> --%>
			<form:options items="${allTypes}" />
		</form:select>

		<input type="submit" value="Add Account" />

		<!-- <button type="submit">Add</button> -->
	</form:form>

<%-- 

	<form action="./addAccount" method="post">
		<p>Account Name:</p>
		<input type="text" name="account name" required="required"> <br>
		<p>Balance:</p>
		<input type="text" name="balance" required="required"> <br>
		<p>Account number:</p>
		<input type="text" name="last 4 digits">
		<p>(Optional last 4 digits)</p>
		<br>
		<p>Currency:</p>
		<select name="currency" required="required">
			<option value="BGN">BGN</option>
			<option value="EUR">EUR</option>
			<option value="USD">USD</option>
			<option value="GBP">GBP</option>
			<option value="AUD">AUD</option>
		</select>
		<p>Account type:</p>
		<br> <select name="type" required="required">

			<option value="Credit Card">Credit Card</option>
			<option value="Loan">Loan</option>
			<option value="Pension">Pension</option>
			<option value="IRA">IRA</option>
			<option value="Other Investment">Other Investment</option>
			<option value="Cash">Cash</option>
			<option value="PayPal">PayPal</option>
			<option value="Gift Card">Gift Card</option>
		</select><br>
		<button type="submit">Add</button>
	</form> --%>
</body>
</html>