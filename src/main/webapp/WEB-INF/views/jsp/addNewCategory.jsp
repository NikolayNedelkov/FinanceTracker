<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">Add new transaction category</span>
			</h2>
		</div>
		<form id="addExpenseForm" action="" method="POST">
			<div class="expense_name">
				<div class="inline modalcss">Name of category:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="categoryName" required>
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Enter type:</div>

				<select class="form-control addExpense_category_option" id="type"
					name="typeSelect" required>
					<option disabled selected>Please select a transaction type</option>
					<option value="false">Withdrawal</option>
					<option value="true">Deposit</option>
				</select>
			</div>

			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" data-dismiss="modal">Save</button>
				<button type="button"
					onclick="location.href = '../add';"
					class="btn btn-secondary">Back</button>

			</div>
		</form>
	</div>
</div>

