<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<%-- 				<c:if test="${type} eq 'Loan' ">
					<div class="expense_name">
						<div id="percentdiv">Percentage:</div>
						<form:input type="text" id="percent" name="percentage"
							path="percentage" />
					</div>
				</c:if>



				<c:if test="${type} ne 'Loan' ">
					<div class="expense_name">
						<div id="percentdiv" class="inline modalcss hidden-input">Percentage:</div>
						<form:input class="hidden-input" type="text" id="percent"
							name="percentage" path="percentage" />
					</div>
				</c:if> --%>




				<div class="expense_name">
					<div class="inline modalcss">Currency:</div>

					<form:select class="form-control" path="currency">
						<form:option value="-" label="--Select Currency" />
						<form:options items="${allCurrencies}" />
					</form:select>
				</div>

				<div class="expense_name">
					<div class="inline modalcss">Account type:</div>

					<form:select id="formichka" onchange="changed(event)"
						class="form-control" path="type">
						<form:option value="-" label="--Select type" />
						<form:options id="allTypes" items="${allTypes}" />
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
	function changed(event) {
		var form = event.target;
		var optionId = form.options[form.selectedIndex].id;
		showElement(optionId)

	}
	function showElement(optionId) {
		var element = document.getElementById("percent");
		var elementDiv = document.getElementById("percentdiv");

		if (optionId === 'allTypes1' || optionId === 'allTypes2') {
			element.classList.remove("hidden-input");
			elementDiv.classList.remove("hidden-input");
		} else {
			element.classList.add("hidden-input");
			elementDiv.classList.add("hidden-input");
		}
	}
</script>
