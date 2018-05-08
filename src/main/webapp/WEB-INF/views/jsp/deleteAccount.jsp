
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="menu.jsp"></jsp:include>


<div id="page-wrapper">

	<div class="container-fluid">

		<div class="dashlog">
			<div class="row">
				<div class="col-lg-12">
					<div class=" col-lg-12 itemlog list">

						<table class="table table-hover" class="col-lg-12 itemlog list">
							<thead>
								<tr>
									<th scope="col"></th>
									<th scope="col">Are you sure you want to delete this
										account?</th>

								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
						<form name="accountId" action="./delete" method="post">
							<input type="hidden" name="accountId" value="${ account.account_id }" />
							<input type="submit" id="editbtn" class="btn btn-primary"
								value="I'm sure" />
							<div class="modal-footer">
								<button type="button" onclick="location.href='../accounts';"
									class="btn btn-primary">Back</button>
							</div>
						</form>


					</div>
					<!-- itemlog -->
				</div>
				<!-- search -->
			</div>



		</div>
	</div>
</div>



<script src="js/budget.js" type="text/javascript"></script>
<script src="js/Chart.bundle.js" type="text/javascript"></script>
<script src="js/budget.js" type="text/javascript"></script>