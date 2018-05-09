<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>


<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>My Accounts</h2>
		</div>
		<!-- /.row -->

		<div class="dashlog">
			<div class="row">
				<div class="col-lg-12">
					<div class="well">
						<button onclick="location.href='./accounts/add';" type="button"
							class="btn btn-primary" id="addtransaction">Add a new
							account</button>
					</div>

					<div id="search">
						<div id="search_btn" class="inline" style="display: block;">
							Search: <input type="text" class="form-control search"
								id="search_content" placeholder="Search by Account name">
						</div>

						<div class=" col-lg-12 itemlog list">

							<table class="table table-hover" class="col-lg-12 itemlog list"
								id="acc-table">
								<thead>
									<tr>
										<th scope="col"></th>
										<th scope="col">
											<form action="./accounts" method="get">
												<button name="criteria" type="submit" value="Balance"
													class="link">Balance</button>
											</form>
										</th>
										<th scope="col">
											<form action="./accounts" method="get">
												<button name="criteria" type="submit" value="Currency"
													class="link">Currency</button>
											</form>
										</th>
										<th scope="col">
											<form action="./accounts" method="get">
												<button name="criteria" type="submit" value="Account type"
													class="link">Account type</button>
											</form>
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${ sessionScope.user.accounts }"
										var="account">
										<tr class="item_name clickable">
											<th scope="row" class="item_name clickable"><c:out
													value="${account.accountName}" /></th>
											<td class="item_amt"><c:out value="${account.balance}" /></td>
											<td class="item_amt"><c:out value="${account.currency}" /></td>
											<td class="item_category"><c:out value="${account.type}" /></td>
											<td><input type="button" id="editbtn"
												class="btn btn-primary"
												onclick="location.href='./accounts/acc/${ account.account_id }';"
												value="Edit account" /></td>
											<td>
												<form name="accId" action="./accounts/del">
													<input type="hidden" name="accId"
														value="${ account.account_id }" /> <input type="submit"
														id="editbtn" class="btn btn-primary"
														value="Delete account" />
												</form>
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


<script src="js/budget.js" type="text/javascript"></script>
<script src="js/Chart.bundle.js" type="text/javascript"></script>
<script src="js/budget.js" type="text/javascript"></script>