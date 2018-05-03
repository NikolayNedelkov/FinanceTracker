
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">Add new Transaction</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">
			<div class="expense_name">
				<div class="inline modalcss">Payee/Payer name:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee">
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Date of transaction:</div>
				<input type="date" class="form-control" id="addexpense_name"
					name="date">
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Enter the amount:</div>
				<input type="number" class="form-control" id="addexpense_name"
					name="amount">
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Choose Account:</div>

				<select class="form-control" name="accountSelect">
					<c:forEach items="${ sessionScope.user.accounts }" var="account">
						<option value="${ account.accountName }">${ account.accountName }</option>
					</c:forEach>
				</select>
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Enter type:</div>

				<select name="typeSelect">
					<option value="0">Withdrawal</option>
					<option value="1">Deposit</option>
				</select>
			</div>
			
			
			<div class="expense_name">
				<div class="inline modalcss">Category:</div>
					<select name="category">
						<option value="1">Car</option>
					</select>
			</div>	


			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="submit" class="btn btn-secondary" data-dismiss="modal">Save</button>
				<button type="submit" class="btn btn-primary">Back</button>
			</div>

		</form>
	</div>
</div>