<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Sign-Up/Login Form</title>
<link
	href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<div class="form">
		<ul class="tab-group">
			<li onclick="location.href='../';" class="tab"><a>Log In</a></li>
			<li onclick="location.href='../';" class="tab active"><a>Sign
					Up</a></li>
		</ul>

		<div id="login">
			<h1>Enter your new password</h1>

			<form action="<c:url value='/resetPassword/${ token }'></c:url>"
				method="post">
				<c:if test="${resetPassword!=null}">
					<label style="color: red"><c:out value="${resetPassword}" /></label>
				</c:if>

				<div class="field-wrap">
					<label> Enter new password<span class="req">*</span>
					</label> <input type="password" required autocomplete="on" name="password"
						required="" />
				</div>
				<div class="field-wrap">
					<label> Retype new password <span class="req">*</span>
					</label> <input type="password" required autocomplete="on"
						name="repeatPassword" required="" />
				</div>
				<button class="button button-block" type="submit" />
				Set new password
				</button>

			</form>

		</div>
	</div>
	<!-- /form -->
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src="js/index.js"></script>

</body>
</html>
