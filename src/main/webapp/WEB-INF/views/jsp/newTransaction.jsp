
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

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
					<form:radiobutton path="isIncome" value="true" />
					<form:radiobutton path="isIncome" value="false" />
					<%-- 					<form:select name= "type" class="form-control" path="isIncome">
						<form:option value="-" label="--Select type" />
						<form:option value="false" label=""/>
						<form:option value="true" label="" />
					</form:select> --%>
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
</div>