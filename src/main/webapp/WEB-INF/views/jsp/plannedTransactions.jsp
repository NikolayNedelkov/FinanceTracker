<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>My Planned Transactions</h2>
		</div>
		<!-- /.row -->

		<div class="dashlog">
			<div class="row">
				<div class="col-lg-12">
					<div class="well">
						Display by:
						<div id="display_btn" class="inline">
							<select class="form-control select" id="display_option"
								onchange='filterSelect()'>
								<option value="" disabled selected>-category-</option>
								<option value="All categories">All Categories</option>
								<c:if test="${transaction.isIncome eq false}">
									<c:out value="Expense"></c:out>
									</td>
								</c:if>

								<c:forEach items="${categories}" var="category">
									<option value="${category}">${category}</option>
								</c:forEach>
							</select>
						</div>

						<button type="button" class="btn btn-primary" id="addtransaction"
							onclick="location.href='./plannedTransactions/add';">Add
							a new planned transaction</button>
					</div>

					<div id="search">
						<div id="search_btn" class="inline" style="display: block;">
							Search: <input type="text" class="form-control search"
								id="search_planned_transaction"
								placeholder="Search by transaction name" onkeyup='search()'>
						</div>


						<div class=" col-lg-12 itemlog list">

							<table class="table table-hover" id="trans_table"
								class="col-lg-12 itemlog list">
								<thead>
									<tr>
										<th scope="col">Transaction Payee/Payer</th>
										<th scope="col">Amount</th>
										<th scope="col">Type</th>
										<th scope="col">Category</th>
										<th scope="col">Account</th>
										<th scope="col">Payment day</th>
										<th scope="col">Recurrency</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${allUserPlannedTransactions}"
										var="transaction">
										<tr class="table_row clickable">
											<th scope="row" class="payee clickable"><c:out
													value="${transaction.payee}"></c:out></th>
											<td class="amount"><c:out value="${transaction.amount}"></c:out></td>
											<td class="isIncome"><c:if test="${transaction.isIncome eq true}">
													<c:out value="Income"></c:out></td>
											</c:if>
											<c:if test="${transaction.isIncome eq false}">
												<c:out value="Expense"></c:out>
												</td>
											</c:if>
											<td class="category"><c:out
													value="${transaction.category}"></c:out></td>
											<td class="acc_name"><c:out
													value="${transaction.account.accountName}"></c:out></td>
											<td class="trans_date"><c:out
													value="${transaction.plannedDate}"></c:out></td>
											<td class="recurrency"><c:out
													value="${transaction.recurrency}"></c:out></td>
											<td><input type="button" id="editbtn"
												class="btn btn-primary"	onclick="location.href='./transactions/edit/${transaction.id}';"
												value="Edit"> 
												<input type="button" id="deletebtn"	class="btn btn-secondary"
												onclick="location.href='./plannedTransactions/delete/${transaction.id}';"
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



		<!-- search -->
	</div>

</div>
<script src="js/budget.js" type="text/javascript"></script>
<script type="text/javascript">
	function filterSelect(){
		
		var select, filter, table, tr, td, i;
		select = document.getElementById("display_option");
		filter = select.value.toUpperCase(); 
		table = document.getElementById("trans_table");
		tr = table.getElementsByTagName("tr");
		
		
		if (filter === "ALL CATEGORIES") {
			for (i = 0; i < tr.length; i++) {
				tr[i].style.display = "";
			}
		}else{
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];

				if (td) {
					if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
		
	}
</script>


<script type="text/javascript">

function search()  {
	var input, filter, table, tr, td, i;
	input = document.getElementById("search_planned_transaction");
	filter = input.value.toUpperCase();
	table = document.getElementById("trans_table");
	tr = table.getElementsByTagName("tr");

	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("th")[0];

		if (td) {
			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

</script>
