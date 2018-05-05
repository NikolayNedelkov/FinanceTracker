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

						<button type="button" class="btn btn-primary" id="addtransaction"
							onclick="location.href='./transactions/add';">Add a new
							transaction</button>
					</div>

					<div id="search">
						<div id="search_btn" class="inline" style="display: block;">
							Search: <input type="text" class="form-control search"
								id="search_content" placeholder="Search by payee/payer name">
						</div>


						<div class=" col-lg-12 itemlog list">

							<table class="table table-hover" id="data_table"
								class="col-lg-12 itemlog list">
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
										<tr class="table_row clickable">
											<th scope="row" class="payee clickable"><c:out
													value="${transaction.payee}"></c:out></th>
											<td class="amount"><c:out value="${transaction.amount}"></c:out></td>
											<td class="isIncome"><c:if
													test="${transaction.isIncome} == true">
													<c:out value="Income"></c:out>
												</c:if> <c:if test="${transaction.isIncome} == false">
													<c:out value="Expense"></c:out>
												</c:if></td>
											<td class="category"><c:out
													value="${transaction.category}"></c:out></td>
											<td class="acc_name"><c:out
													value="${transaction.account.accountName}"></c:out></td>

											<td class="trans_date"><c:out
													value="${transaction.date}"></c:out></td>
											<td><input type="button" id="editbtn"
												class="btn btn-primary"
												onclick="location.href='./transactions/edit/${transaction.id}';"
												value="Edit">
												<input type="button" id="deletebtn"
												class="btn btn-secondary"
												onclick="location.href='./transactions/delete/${transaction.id}';"
												value="Delete">
												</td>
											
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



		<!-- search -->
	</div>
	<!-- dashlog -->


</div>
<!-- /.container-fluid -->



<script src="js/budget.js" type="text/javascript"></script>
