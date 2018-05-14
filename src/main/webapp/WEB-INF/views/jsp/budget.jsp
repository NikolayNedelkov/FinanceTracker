
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

						<table class="table table-hover" class="col-lg-12 itemlog list">
							<thead>
								<tr>
									<th scope="col"></th>
									<th scope="col">Income</th>
									<th scope="col">Outcome</th>
									<th scope="col">In/Out</th>
									<th scope="col">Previous</th>
									<th scope="col">Balance Total</th>
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

								<tr>
									<td class="item_amt"><c:out value="Total" /></td>
									<c:forEach items="${statisticTotal}" var="total">
										<td class="item_amt"><c:out value="${total}" /></td>
									</c:forEach>
								</tr>

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