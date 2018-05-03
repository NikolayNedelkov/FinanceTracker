<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>


<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>My Transactions</h2>
		</div>
		<!-- /.row -->

		<div class="dashlog">
			<div class="row">
				<div class="col-lg-12">
					<div class="well">
						Display by:
						<div id="display_btn" class="inline">
							<select class="form-control select" id="display_option">
								<option value="all">All</option>
								<option value="general">General</option>
								<option value="personal">Personal</option>
								<option value="food">Food</option>
								<option value="groceries">Groceries</option>
								<option value="shopping">Shopping</option>
								<option value="entertain">Entertain</option>
								<option value="transport">Transport</option>
								<option value="housing">Housing</option>
								<option value="medical">Medical</option>
								<option value="academic">Academic</option>
							</select>
						</div>
						<div class="inline" id="changecolor">
							Background Color:
							<div class="changecolor inline">
								<select class="form-control select" id="backcolor_option">
									<option>White</option>
									<option>Red</option>
									<option>Orange</option>
									<option>Yellow</option>
									<option>Green</option>
									<option>Blue</option>
									<option>Pink</option>
								</select>
							</div>
						</div>
						<button onclick="location.href='./transactions/add';" type="button" class="btn btn-primary" id="addtransaction">Add
							a new transaction</button>
					</div>


					<div id="search">
						<div id="search_btn" class="inline" style="display: block;">
							Search: <input type="text" class="form-control search"
								id="search_content">
						</div>


						<div class=" col-lg-12 itemlog list">

							<table class="table table-hover" class="col-lg-12 itemlog list">
								<thead>
									<tr>
										<th scope="col">Transaction Payee/Payer</th>
										<th scope="col">Amount</th>
										<th scope="col">Type</th>
										<th scope="col">Category</th>
										<th scope="col">Account</th>
										<th scope="col">Date</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${allUserTransactions}" var="transaction">
										<tr class="item_name clickable">
											<th scope="row" class="item_name clickable"><c:out
													value="${transaction.payee}"></c:out></th>
											<td class="item_amt"><c:out
													value="${transaction.amount}"></c:out></td>
											<td><c:if test="${transaction.isIncome} == true">
													<c:out value="Income"></c:out>
												</c:if> <c:if test="${transaction.isIncome} == false">
													<c:out value="Expense"></c:out>
												</c:if></td>
											<td class="item_category"><c:out
													value="${transaction.category}"></c:out></td>
											<td class="item_note"><c:out
													value="${transaction.accountID}"></c:out></td>
											<td><c:out value="${transaction.date}"></c:out></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
						<!-- itemlog -->
					</div>
					<!-- search -->
				</div>



			</div>
			<!-- col-lg-12 -->
		</div>
		<!-- /.row -->

		<div id="search">
			<div id="search_btn" class="inline">
				Search: <input type="text" class="form-control search"
					id="search_content">
			</div>
			<ul class=" col-lg-12 itemlog list">
				<li id="fake">
					<div class="inline item_name"></div>
					<div class="inline item_amt">
						<div class="inline currency"></div>

					</div>
					<div class="inline item_category"></div>
					<div class="inline item_note"></div>
				</li>

			</ul>
			<!-- itemlog -->
		</div>
		<!-- search -->
	</div>
	<!-- dashlog -->

	<div class="row">
		<div class="col-lg-12 summarylog">
			<div class="panel panel-default">
				<div class="panel-heading">
					Total Expense:
					<div id="totalexpense"></div>
				</div>
				<div class="panel-body">
					Total Income:
					<div id="totalincome"></div>
				</div>
				<div class="panel-heading">
					Net:
					<div id="net"></div>
				</div>
			</div>
		</div>
	</div>

</div>
<!-- /.container-fluid -->



<!-- </div>
/#page-wrapper
</div>
/#wrapper -->



<%-- <div class="modal fade" id="addExpenseModal">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Add a new transaction record</h4>
			</div>
			<div class="modal-body">
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
						<select class="form-control" class="addExpense_category_option"
							name="typeSelect">
							<option value="withdrawal">Withdrawal</option>
							<option value="deposit">Deposit</option>
						</select>
					</div>

					<div class="expense_category">
						<div class="inline modalcss">Category:</div>
						<select class="form-control" name="expense_categories">
							<option value="1">Home</option>
						</select>
					</div>

					<!--  id="addExpenseOn_btn" -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Add</button>
					</div>

				</form>
			</div>


			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.addExpenseModal -->

	<div class="modal fade" id="editExpenseModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit Expense</h4>
				</div>
				<div class="modal-body">
					<form id="editExpenseForm">
						<div class="expense_name">
							<div class="inline modalcss">Item Name:</div>
							<input type="text" class="form-control" placeholder="Item Name"
								id="editexpense_name">
						</div>
						<div class="expense_amt">
							<div class="inline modalcss">Amount:</div>
							<input type="number" step="any" class="form-control"
								id="editexpense_amt">
						</div>
						<div class="expense_category">
							<div class="inline category">Category:</div>
							<div class="inline modalcss">
								<select class="form-control select"
									id="editExpense_category_option">
									<option value="general">General</option>
									<option value="personal">Personal</option>
									<option value="food">Food</option>
									<option value="groceries">Groceries</option>
									<option value="shopping">Shopping</option>
									<option value="entertain">Entertain</option>
									<option value="transport">Transport</option>
									<option value="housing">Housing</option>
									<option value="medical">Medical</option>
									<option value="academic">Academic</option>
								</select>
							</div>
						</div>
						<!-- expense_category -->
						<div class="expense_note">
							<div class="inline modalcss">Note:</div>
							<input type="text" class="form-control" placeholder="Note"
								id="editexpense_note">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						id="deleteExpenseOn_btn">Delete</button>
					<button type="button" class="btn btn-primary"
						id="editExpenseOn_btn">Edit</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.editExpenseModal --> --%>

	<script src="js/budget.js" type="text/javascript"></script>
	<script src="js/Chart.bundle.js" type="text/javascript"></script>
	<script src="js/budget.js" type="text/javascript"></script>