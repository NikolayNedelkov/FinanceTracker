
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menu.jsp"></jsp:include>
<%-- <link type="text/css" href="<%=request.getContextPath() %>/css/bootstrap.css" rel="stylesheet"/>
<head>
    <link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/static/css/custom.css" />"rel="stylesheet">
    <script src="<c:url value="/resources/js/main.js" />"></script>
</head> --%>
<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">My Profile</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">


			<div class="expense_name">
				<div class="inline modalcss">First Name:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee" disabled="disabled">
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Last Name:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee" disabled="disabled">
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Email :</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee" disabled="disabled">
			</div>










<!-- Raboti!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 -->
			<form:form commandName="account">
				<div class="expense_name">
					<div class="inline modalcss">
						Account Name:</div>
						<form:input type="text" class="form-control" id="addexpense_name"
							name="accountName" path="accountName" />
					</div>
					
					<div class="expense_name">
					<div class="inline modalcss">
					Currency:</div>
					
					<form:select class="form-control" path="currency">
					<form:option value="-" label="--Select Currency" />
					<form:options items="${allCurrencies}" />
					</form:select>
					
				</div>	
					
					
					
<!-- Do tuk!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 -->					
					
					
					
					
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
					<br>
					<%-- <form:option value="-" label="--Select Currency" /> --%>
					<form:options items="${allTypes}" />
				</form:select>

				<input type="submit" value="Add Account" />

				<!-- <button type="submit">Add</button> -->
			</form:form>


			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Save</button>
				<button type="submit" class="btn btn-primary">Back</button>
			</div>

		</form>
	</div>
</div>