<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">Change Password</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="./changePassword" method="post">
			<div class="expense_name">
				<div class="inline modalcss">New password:</div>
				<input placeholder="Enter new password"
					onmousedown="this.type='text'" onmouseup="this.type='password'"
					onmousemove="this.type='password'" class="form-control"
					id="addexpense_name" name="newPass">

			</div>
			<div class="expense_name">
				<div class="inline modalcss">Confirm new password:</div>
				<input placeholder="Re-enter new password "
					onmousedown="this.type='text'" onmouseup="this.type='password'"
					onmousemove="this.type='password'" class="form-control"
					id="addexpense_name" name="newPass2">
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Current password* :</div>
				<input placeholder="Enter current password"
					onmousedown="this.type='text'" onmouseup="this.type='password'"
					onmousemove="this.type='password'" class="form-control"
					id="addexpense_name" name="currPass">
			</div>



			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="submit" class="btn btn-secondary" data-dismiss="modal">Update</button>
				<button type="button" onclick="location.href = './myProfile';"class="btn btn-primary">Back</button>
			</div>
		</form>
	</div>
</div>

