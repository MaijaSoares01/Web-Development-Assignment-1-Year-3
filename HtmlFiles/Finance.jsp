<html>
<head>
<title>Compound Interest Calculator</title>
</head>
<body>
<h1>Compound Interest Calculator</h1>
<h3>Welcome <%=request.getParameter("username")%>!</h3>
<p>Please enter an initial amount, compound interest rate, and duration in years.</br></p>
<form action = "Finance" action="Finance">
<hr>
<b>Initial Amount:</b><br><input type = "number"name = "initialAmount" placeholder="0"/></br>
<b>Compound Interest Rate:</b><br><input type = "number"name = "compoundInt" placeholder="0"/> <b>%</b></br>
<b>Duration in Years:</b><br><input type = "number"name = "duration" placeholder="0"/></br>
<input type = "hidden"name = "username" value="<%=request.getParameter("username")%>"/>
<br><input type = "submit" name = "financeSubmit" value = "SUBMIT"/>
<hr>
</form>

</body>
</html>