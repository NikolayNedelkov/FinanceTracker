<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">My Accounts</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">
			<form:form commandName="currentAccount">
			<div class="expense_name">
					<div class="inline modalcss">Account Name:</div>
					<form:input type="text" class="form-control" id="addexpense_name"
						name="accountName" path="accountName" />
				</div>
				
				<div class="expense_name">
					<div class="inline modalcss">Balance:</div>
					<form:input type="number" class="form-control" id="addexpense_name"
						name="accountName" path="balance" />
				</div>
			
			<div class="expense_name">
					<div class="inline modalcss">Account number:</div>
					<form:input type="text" class="form-control" id="addexpense_name"
						name="accountName" path="lastFourDigits" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Currency:</div>

					<form:select class="form-control" path="currency">
						<form:option value="-" label="--Select Currency" />
						<form:options items="${allCurrencies}" />
					</form:select>
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Account type:</div>

					<form:select class="form-control" path="type">
						<form:option value="-" label="--Select type" />
						<form:options items="${allTypes}" />
					</form:select>
				</div>
							<div class="modal-footer">
					<button type="submit" class="btn btn-secondary"
						data-dismiss="modal">Submit changes</button>
					<button type="submit" class="btn btn-primary">Back</button>
				</div>
			</form:form>


			<!--  id="addExpenseOn_btn" -->


		</form>
	</div>
</div>			
			