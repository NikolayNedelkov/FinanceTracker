<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/main2.css">
</head>
<body>
	<form action="/login">
		<div class="container">
			<label for="email"><b>Email</b></label> <input type="text"
				placeholder="Enter Username" name="email" required> <label
				for="pass"><b>Password</b></label> <input type="password"
				placeholder="Enter Password" name="pass" required>

			<button type="submit">Login</button>
			<div class="container" style="background-color: transparent;">
				<span class="psw">Don't have an <a href="#">account?</a></span>
			</div>
			<label> <input type="checkbox" checked="checked"
				name="remember"> Remember me
			</label>
		</div>
	</form>
</body>
</html>