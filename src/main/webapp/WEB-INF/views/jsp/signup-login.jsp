<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign-Up/Login Form</title>
<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login or register to the Finance Tracker</title>
</head>
<body>
	<div class="form">
		<ul class="tab-group">
			<li class="tab"><a href="#login">Log In</a></li>
			<li class="tab active"><a href="#signup">Sign Up</a></li>
		</ul>
		<div class="tab-content">
			<div id="login">
				<h1>Welcome To Finance Tracker!</h1>

				<form action="login" method="post">

					<div class="field-wrap">
						<label> Email Address<span class="req">*</span>
						</label> <input type="email" required autocomplete="on" name="email" />
					</div>

					<div class="field-wrap">
						<label> Password<span class="req">*</span>
						</label> <input type="password" required autocomplete="off"
							name="password" />
					</div>

					<p class="forgot">
						<a href="./forgotPassword">Forgot Password?</a>
					</p>

					<button class="button button-block" />
					Log In
					</button>

				</form>

			</div>

			<div id="signup">
				<h1>Sign Up for Free</h1>

				<form action="register" method="post">

					<div class="top-row">
						<div class="field-wrap">
							<label> First Name<span class="req">*</span>
							</label> <input type="text" required autocomplete="off" name="firstName" />
						</div>

						<div class="field-wrap">
							<label> Last Name<span class="req">*</span>
							</label> <input type="text" required autocomplete="off" name="lastName" />
						</div>
					</div>

					<div class="field-wrap">
						<label> Email Address<span class="req">*</span>
						</label> <input type="email" required autocomplete="off" name="email" />
					</div>

					<div class="field-wrap">
						<label> Set A Password<span class="req">*</span>
						</label> <input type="password" required autocomplete="off"
							name="password" />
					</div>

					<button type="submit" class="button button-block" />
					Get Started
					</button>

				</form>

			</div>



		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="js/index.js"></script>

</body>
</html>
