
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>


<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>My Budget</h2>
		</div>
		<!-- /.row -->

		<div class="dashlog">
			<div class="row">
				<div class="col-lg-12">
					<div class=" col-lg-12 itemlog list">




						<%-- 
						<table class="table table-hover" class="col-lg-12 itemlog list">
							<thead>
								<tr>
									<th scope="col"></th>
									<th scope="col">Total Income</th>
									<th scope="col">Total Expense</th>
								</tr>
							</thead>
							<tbody>
								<tr class="item_name clickable">
									<th scope="row" class="item_name clickable"><c:out
											value="Totals" /></th>
									<td class="item_amt"><c:out
											value="${sessionScope.budget.totalIncome}" /></td>
									<td class="item_amt"><c:out
											value="${sessionScope.budget.totalExpense}" /></td>

								</tr>
							</tbody>
						</table> --%>





						<table class="table table-hover" class="col-lg-12 itemlog list">
							<thead>
								<tr>
									<th scope="col"></th>
									<th scope="col">Income</th>
									<th scope="col">Outcome</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${budgetByAccount}" var="list">
									<tr class="item_name clickable">
										<th scope="row" class="item_name clickable"><c:out
												value="${list.key}" /></th>
										<c:forEach items="${list.value}" var="balance">
											<td class="item_amt"><c:out value="${balance}" /></td>
										</c:forEach>
									</tr>
								</c:forEach>



								<%-- 	<c:forEach items="${budgetByAccount}" var="currentAccount"
									varStatus="currentAccountStatus">  
						       //Key
						       Key: ${currentAccount.key}
						       //Iterate over values ,assuming vector of strings
						       <c:forEach items="${currentAccount.value}"
										var="accountName" varStatus="valueStatus">
						           Value: ${accountName}
						       </c:forEach>
								</c:forEach>


								<c:forEach items="${budgetByAccount}" var="entry">
								Key = ${entry.key}, value = ${entry.value}
									<tr class="item_name clickable">
										<th scope="row" class="item_name clickable"><c:out
												value="${account.accountName}" /></th>
										<td class="item_amt"><c:out value="${account.incomes}" /></td>
										<td class="item_amt"><c:out value="${account.outcomes}" /></td>
									</tr>
								</c:forEach> --%>








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



<script src="js/budget.js" type="text/javascript"></script>
<script src="js/Chart.bundle.js" type="text/javascript"></script>
<script src="js/budget.js" type="text/javascript"></script>