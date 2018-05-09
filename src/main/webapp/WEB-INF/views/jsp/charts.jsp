<%@page import="java.util.TreeSet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*" %> 
<jsp:include page="menu.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>
<title>Your chart-diagram</title>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.js"></script>

</head>
<body>
	<div class="container">
		<canvas id="myChart"> </canvas>
	</div>

	<script>
		var myChart = document.getElementById('myChart').getContext('2d');
		var myChart = new Chart(
				myChart,
				{
					type : 'bar',
					data : {
						/* i t.n. */
						labels : [
							<% 
							ArrayList<String> names=(ArrayList<String>)(request.getAttribute("accounts"));
							//Iterator <String> it=names.iterator();
							for (int index=0; index<names.size(); index++){
								if(index==names.size()-1){
									out.print("'"+names.get(index)+"'");
								}
								else{
									out.print("'"+names.get(index)+"'"+",");
								}
							}%>
						],
						datasets : [{
							label:'Balance',
							data:[
								<% 
								ArrayList<Double> balance=(ArrayList<Double>)(request.getAttribute("balance"));
								for (int index=0; index<balance.size(); index++){
									if(index==balance.size()-1){
										out.print(balance.get(index));
									}
									else{
										out.print(balance.get(index)+",");
									}
								}%>
							],
							backgroundColor:'#184f8b', 
							borderColor: '#08182b', 
							borderWidth: 2
						}]
					},
					options : {}
				});
	</script>
</body>
</html>