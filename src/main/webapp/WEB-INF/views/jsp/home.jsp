<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.financetracker.model.transactions.Transaction"%>
<jsp:include page="menu.jsp"></jsp:include>

<div id="page-wrapper">

	<div class="container-fluid">

		<!-- Page Heading -->
		<div class="row center">
			<h2>
				Welcome to <span class="bold">Finance</span> Tracker
			</h2>
		</div>
		<!-- /.row -->

		<div class="col-lg-12">

			<h3>
				Here is a list of your <span class="bold">Accounts</span> in our
				system:
			</h3>

			<div class="card-group">

				<div class="card text-white bg-success mb-3"
					style="max-width: 40rem;">
					<form action="./home" method="get">
						<button name="criteria" value="debit" class="card-header">Debit
							Accounts</button>
					</form>
					<div class="card-body">
						<h5 class="card-title">Primary card title</h5>
						<p class="card-text">Overview of your Debit accounts: PayPal,
							Cash</p>
					</div>
				</div>


				<div class="card text-white bg-danger mb-3"
					style="max-width: 40rem;">
					<form action="./home" method="get">
						<button name="criteria" value="credit" class="card-header">Credit
							Accounts</button>
					</form>
					<div class="card-body">
						<h5 class="card-title">Primary card title</h5>
						<p class="card-text">Overview of your Debts: Credit Card,
							Loans</p>
					</div>
				</div>

				<div class="card text-white bg-primary mb-3"
					style="max-width: 40rem;">
					<form action="./home" method="get">
						<button name="criteria" value="investment" class="card-header">Investment
							Accounts</button>
					</form>
					<div class="card-body">
						<h5 class="card-title">Primary card title</h5>
						<p class="card-text">Overview of your Investment accounts: IRA,
							Other Investments</p>
					</div>
				</div>




			</div>

		</div>

	</div>
</div>

