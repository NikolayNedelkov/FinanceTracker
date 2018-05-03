<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="menu.jsp"></jsp:include>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div id="page-wrapper">

		<div class="container-fluid">

			<!-- Page Heading -->
			<div class="row center">
				<h2>My accounts</h2>
			</div>
		</div>
	</div>

	<c:if test="${ fn:length(sessionScope.user.accounts)==0 }">
		<p>No accounts added yet</p>
	</c:if>
	<c:if test="${ fn:length(sessionScope.user.accounts)>0 }">
		<c:forEach items="${ sessionScope.user.accounts }" var="account">
			<h4>
				<a href="./accounts/acc/${ account.account_id }"></a>

				<c:out value="${ account.accountName }"></c:out>
				<input type="button"
					onclick="location.href='./accounts/acc/${ account.account_id }';"
					value="Edit account" /><br>
			</h4>
			<table border="1">
				<tr>
					<th>Balance</th>
					<th>Currency</th>
					<th>Accounttype</th>
				</tr>
				<tr>
					<td><c:out value="${account.balance}" /></td>
									<td><c:out value="${account.lastfourdigits}" /></td>
				<td ><c:out value="${account.percentage}" /></td>
				<td><c:out value="${account.paymentDueDay}" /></td>
					<td><c:out value="${account.currency}" /></td>
					<td><c:out value="${account.type}" /></td>
				</tr>
			</table>
			<hr>
		</c:forEach>
	</c:if>

	<div class="card text-white bg-secondary mb-3"
		style="max-width: 40rem;">
		<div class="card-header">
			<a href="accounts/add" id="dash_btn">Add new Account </a>
		</div>
		<div class="card-body">
			<h5 class="card-title">Primary card title</h5>
			<p class="card-text">Some quick example text to build on the card
				title and make up the bulk of the card's content.</p>
		</div>
	</div>
</body>
</html>
 --%>
















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
						<button onclick="location.href='./accounts/add';" type="button" class="btn btn-primary" id="addtransaction">Add
							a new account</button>
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
									<th scope="col"></th>
										<th scope="col">Balance</th>
										<th scope="col">Currency</th>
										<th scope="col">Account type</th>
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
											<td> <input type="button" id="editbtn" class = "btn btn-primary"
												onclick="location.href='./accounts/acc/${ account.account_id }';"
												value="Edit account" /></td>
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





<!-- 	<div class="modal-dialog" role="document">
		<div class="modal-content"> -->
<!-- <div class="modal-header"> -->

<!-- </div> -->




<!-- /.modal-content -->
<!-- 		</div>
		/.modal-dialog
	</div> -->
<!-- /.addExpenseModal -->
<!-- </div> -->


<script src="js/budget.js" type="text/javascript"></script>
<script src="js/Chart.bundle.js" type="text/javascript"></script>
<script src="js/budget.js" type="text/javascript"></script>