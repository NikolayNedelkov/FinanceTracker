<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<jsp:include page="menu.jsp"></jsp:include>

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
			


			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Save</button>
				<button type="submit" class="btn btn-primary">Back</button>
			</div>

		</form>
	</div>
</div>

