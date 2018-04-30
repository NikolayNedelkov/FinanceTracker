<<<<<<< HEAD
    <!DOCTYPE html>
        <html lang="en">

        <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="Finance Tracker App">

        <title>Finance Tracker</title>

        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Montserrat"
        rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/custom.css" rel="stylesheet">

        <!-- Morris Charts CSS -->
        <link href="css/plugins/morris.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link rel="stylesheet"
        href="https://use.fontawesome.com/releases/v5.0.10/css/all.css"
        integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
        crossorigin="anonymous">
        <!-- jQuery Lib -->
        <link
        href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.3/themes/start/jquery-ui.css"
        type="text/css" rel="Stylesheet" />
        <script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
        <script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.5/jquery-ui.min.js"></script>


        </head>


        <body>

        <div id="wrapper">

         <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
        data-target=".navbar-ex1-collapse">
        <span class="sr-only">Toggle navigation</span> <span
        class="icon-bar"></span> <span class="icon-bar"></span> <span
        class="icon-bar"></span>
        </button>
        <a class="navbar-brand"> Finance Tracker</a>
        </div>

        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">
        <li class="dropdown"><a href="#" class="dropdown-toggle"
        data-toggle="dropdown"><i class="fa fa-user"></i>
        <div class="inline" id="loginstatus">Hello, <%= request.getSession().getAttribute("email") %></div>
        <b class="caret"></b></a>
        <ul class="dropdown-menu">
        <li><a href="profile.jsp" id="profile_btn"><i
        class="fa fa-fw fa-location-arrow"></i>
        <div class="inline">My profile</div></a></li>
        <li><a href="logout.jsp" id="logoutbtn"><i
        class="fa fa-fw fa-power-off"></i>
        <div class="inline">Log out</div></a></li>
        </ul></li>
        </ul>

        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">

        <li><a href="home.jsp" id="dash_btn"><i class="fas fa-columns"></i>
        <div class="inline">My Dashboard</div></a>

        <a href="accounts" id="expense_btn"><i class="fas fa-credit-card"></i>
        <div class="inline">My Accounts</div></a>

        <a href="transactions.jsp"id="expense_btn"><i class="fas fa-exchange-alt"></i>
        <div class="inline">My Transactions</div></a>

        <a href="plannedpayments.jsp" id="income_btn"><i
        class="fas fa-history"></i>
        <div class="inline">My Planned Payments</div></a>

        <a href="budget.jsp" id="summary_btn"><i
        class="fas fa-money-bill-alt"></i>
        <div class="inline">My budget</div></a>

        <a href="charts.jsp" id="credit_btn"><i class="fas fa-chart-line"></i>
        <div class="inline">Charts and Reports</div></a></li>
        </ul>

        </div>
        <!-- /.navbar-collapse -->
        </nav>



        <!-- additional jQuery -->
        <script src="js/jquery.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

        <!-- JavaScript -->
        <script src="js/budget.js"></script>

        <script
        src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.2.0/list.min.js"></script>
        </body>

        </html>
=======

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Finance Tracker App">

<title>Finance Tracker</title>

<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Montserrat"
	rel="stylesheet">
<!-- Custom CSS -->
<link href="css/custom.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="css/plugins/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.10/css/all.css"
	integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
	crossorigin="anonymous">
<!-- jQuery Lib -->
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.3/themes/start/jquery-ui.css"
	type="text/css" rel="Stylesheet" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.5/jquery-ui.min.js"></script>


</head>


<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand"> Finance Tracker</a>
			</div>

			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i>
						<div class="inline" id="loginstatus">
							Hello,
							<%=request.getSession().getAttribute("firstName")%></div> <b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="profile.jsp" id="profile_btn"><i
								class="fa fa-fw fa-location-arrow"></i>
								<div class="inline">My profile</div></a></li>
						<li><a href="./logout" id="logoutbtn"><i
								class="fa fa-fw fa-power-off"></i>
								<div class="inline">Log out</div></a></li>
					</ul></li>
			</ul>

			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">

					<li><a href="home.jsp" id="dash_btn"><i
							class="fas fa-columns"></i>
							<div class="inline">My Dashboard</div></a> <a href="accounts.jsp"
						id="expense_btn"><i class="fas fa-credit-card"></i>
							<div class="inline">My Accounts</div></a> <a href="transactions.jsp"
						id="expense_btn"><i class="fas fa-exchange-alt"></i>
							<div class="inline">My Transactions</div></a> <a
						href="plannedpayments.jsp" id="income_btn"><i
							class="fas fa-history"></i>
							<div class="inline">My Planned Payments</div></a> <a
						href="budget.jsp" id="summary_btn"><i
							class="fas fa-money-bill-alt"></i>
							<div class="inline">My budget</div></a> <a href="charts.jsp"
						id="credit_btn"><i class="fas fa-chart-line"></i>
							<div class="inline">Charts and Reports</div></a></li>
				</ul>

			</div>
			<!-- /.navbar-collapse -->
		</nav>



		<!-- additional jQuery -->
		<script src="js/jquery.js"></script>

		<!-- Bootstrap Core JavaScript -->
		<script src="js/bootstrap.min.js"></script>

		<!-- JavaScript -->
		<script src="js/budget.js"></script>

		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/list.js/1.2.0/list.min.js"></script>
</body>

</html>
>>>>>>> af239ac416be446b0734f55f4d14283cf12430aa
