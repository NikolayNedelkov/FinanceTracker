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

						<button type="button" class="btn btn-primary" id="addtransaction"
							onclick="location.href='./transactions/add';">Add a new
							transaction</button>
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
													value="${transaction.account.accountName}"></c:out></td>

											<td><c:out value="${transaction.date}"></c:out></td>
											<td><input type="button" id="editbtn"
												class="btn btn-secondary"
												onclick="location.href='./transactions/delete/${transaction.id}';"
												value="Delete"></td>
												

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


</div>
<!-- /.container-fluid -->



<script src="js/budget.js" type="text/javascript"></script>