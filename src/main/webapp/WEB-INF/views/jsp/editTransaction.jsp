<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">
		<div class="row center">
			<h2>
				<span class="bold">Edit transaction</span>
			</h2>
		</div>

		<form id="addExpenseForm" action="" method="post">
			<form:form commandName="currentTransaction">
				<div class="expense_name">
					<div class="inline modalcss">Payee/Payer name:</div>
					<form:input type="text" class="form-control" id="addexpense_name"
						name="payee" path="payee" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Planned transaction date:</div>
					<form:input type="date" class="form-control"
						id="addplanned_transaction_date" path="date" name="plannedDate" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Enter the amount</div>
					<form:input type="number" class="form-control" id="addexpense_name"
						name="amount" step="0.01" min="0.1" path="amount" />
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Choose Account:</div>

					<form:select class="form-control" name="accountSelect"
						path="account">
							<form:option value="-" label="Please select an existing account" />
						    <c:forEach items="${ sessionScope.user.accounts }" var="account">
							<form:option value="${ account.accountName }">${ account.accountName }</form:option>
						</c:forEach>
					</form:select>
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Enter type:</div>

					<form:select class="form-control addExpense_category_option"
						id="type" path="isIncome" name="typeSelect" onchange='loadCategories()'>
						<form:option value="false" label="Please select a transaction type" /> 	
						<form:option value="false" >Withdrawal</form:option>
						<form:option value="true">Deposit</form:option>
					</form:select>
				</div>
				
				<div class="expense_name">
					<div class="inline modalcss">Category:</div>

					<form:select class="form-control addExpense_category_option"
						id="categories" path="category" name="category">
						<form:option value="-" label="Please select a transaction category" />
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


<script>
	function loadCategories() {
		var type = document.getElementById('type').value;
		var xhr = new XMLHttpRequest();
		xhr.open("GET", "edit/${account_id}/getCategories?typeSelect="+ type, true);
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

