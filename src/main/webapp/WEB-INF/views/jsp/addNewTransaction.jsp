<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menu.jsp"></jsp:include>


<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>
				<span class="bold">Add a new transaction</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="transactions/add" method="post">
			<div class="expense_name">
				<div class="inline modalcss">Payee/Payer name:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee">
			</div>

			<div class="expense_name" data-provide="datepicker">
				<div class="inline modalcss">Date of transaction:</div>
				<input type="date" id="sandbox-container" class="form-control"
					name="date">
			</div>

			<div class="expense_amt">
				<div class="inline modalcss">Enter the amount:</div>
				<input type="number" class="form-control" id="addexpense_amt"
					name="amount">
			</div>

			<div class="expense_name">
				<div class="inline modalcss">Choose Account:</div>
				<select class="form-control" class="addExpense_category_option"
					name="accountSelect">
					<c:forEach items="${ sessionScope.user.accounts }" var="account">
						<option value="${ account.accountName }">${ account.accountName }</option>
					</c:forEach>
				</select>
			</div>

			<div class="expense_name">
				<div class="inline modalcss">Enter type:</div>
				<select class="form-control" id='type'
					class="addExpense_category_option" name="typeSelect"
					onchange='loadCategories()'>
					<option value="0">Withdrawal</option>
					<option value="1">Deposit</option>
				</select>
			</div>

			<div class="expense_category">
				<div class="inline modalcss">Category:</div>
				<select id='categories' class="form-control"
					name="expense_categories">
				</select>
			</div>

			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-primary">Add</button>
			</div>
		</form>
	</div>
</div>



<%-- <div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">New Transaction</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">

			<form:form commandName="transaction">
				<div class="expense_name">
					<div class="inline modalcss">Payee/Payer name:</div>
					<form:input type="text" class="form-control" id="addexpense_name"
						name="payee" path="payee" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Date of transaction:</div>
					<form:input type="date" class="form-control" id="addexpense_name"
						name="date" path="date" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Enter the amount:</div>
					<form:input type="number" class="form-control" id="addexpense_name"
						name="amount" path="amount" />
				</div>



				<div class="expense_name">
					<div class="inline modalcss">Account type:</div>

					<form:select class="form-control" path="account">
						<form:option value="-" label="--Select type" />
						<form:options items="${allAccounts}" />
					</form:select>
				</div>



				<div class="expense_name">
					<div class="inline modalcss">Enter type:</div>
					
					Deposit:<form:radiobutton name="isIncome" path="isIncome" value="true"onclick='loadCategories()' />
					Withdrawal: <form:radiobutton name = "isIncome" path="isIncome" value="false" onclick='loadCategories()'/>
					
				</div>


				<div class="expense_name">
					<div class="inline modalcss">Category:</div>

					<form:select class="form-control" path="category">
						<form:option value="1" label="--TestCategory" />

						<!-- Ajax will be here -->

					</form:select>
				</div>


				<div class="modal-footer">
					<button type="submit" class="btn btn-primary" data-dismiss="modal">Add
						Transaction</button>
					<button type="submit" class="btn btn-secondary">Back</button>
				</div>
			</form:form>

		</form>
	</div>
</div> --%>



<script src="js/budget.js" type="text/javascript"></script>
<script src="js/Chart.bundle.js" type="text/javascript"></script>

<script>
	function loadCategories() {
	
		var type = document.getElementsById('type').value;
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "./transactions/add/api?typeSelect="+ type, true);
		xhr.send(null);
		
		xhr.addEventListener('load', () => {
			var categories = JSON.parse(xhr.responseText);
			var html = '';
			for (var index=0; index < categories.length; index++) {
				html += "<option> " + categories[index] + " </option>";
			}
			document.getElementById('categories').innerHTML = html;
		});
	}
	</script>