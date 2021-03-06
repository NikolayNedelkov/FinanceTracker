<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.PlannedTransaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">Add new Planned Transaction</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">
			<div class="expense_name">
				<div class="inline modalcss">Payee/Payer name:</div>
				<input type="text" class="form-control" id="addexpense_name"
					name="payee" required>
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Planned transaction date:</div>
				<input type="date" class="form-control" id="addplanned_transaction_date"
					name="plannedDate" required>
			</div>
			<div class="expense_name">
				<div class="inline modalcss">Enter the amount:</div>
				<input type="number" class="form-control" id="addexpense_name"
					name="amount" step="0.01" min="0.1" required>
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Choose Account:</div>

				<select class="form-control" name="accountSelect" required>
					<option value="" disabled selected>Please select an
						existing account</option>
					<c:forEach items="${ sessionScope.user.accounts }" var="account">
						<option value="${ account.accountName }">${ account.accountName }</option>
					</c:forEach>
				</select>
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Enter type:</div>

				<select class="form-control addExpense_category_option" id="type"
					name="typeSelect" onchange='loadCategories()' required>
					<option disabled selected>Please select a transaction type</option>
					<option value="false">Withdrawal</option>
					<option value="true">Deposit</option>
				</select>
			</div>


			<div class="expense_name">
				<div class="inline modalcss">Category:</div>

				<select class="form-control addExpense_category_option"
					id="categories" name="category" required>
					<option disabled selected>Please select a transaction category</option>
				</select>
			</div>

			<div class="expense_name">
				<div class="inline modalcss">Choose recurrency:</div>

				<select class="form-control" name="recurrencySelect" required>
					<option value="" disabled selected>Please select
						recurrency</option>
					<c:forEach items="${recurrencies}" var="recurrency">
						<option value="${ recurrency }">${ recurrency }</option>
					</c:forEach>
				</select>
			</div>



			<!--  id="addExpenseOn_btn" -->
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" data-dismiss="modal">Save</button>
				<button type="button" onclick="location.href = '../transactions';"
					class="btn btn-secondary">Back</button>
			</div>

		</form>
	</div>
</div>


<script>
	function loadCategories() {
		var type = document.getElementById('type').value;
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "add/getCategories?typeSelect="+ type, true);
		xhr.send(null);
		
		xhr.addEventListener('load', () => {
			var categories = JSON.parse(xhr.responseText.split(","));
			var select = document.getElementById("categories"); 
			select.innerHTML ="";
			for (var index=0; index < categories.length; index++) {
				var opt = categories[index];
			    var el = document.createElement("option");
			    
			    el.textContent = opt;
			    el.value = opt;
			    select.appendChild(el);
			}
		});
	}
</script>


