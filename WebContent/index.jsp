<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>

	<div class="card login-container">
		<h1>Login</h1>
	    <img id="login-profile-img" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
	    <s:form id="login_form" name="login" action="login" method="POST">
	        <s:textfield class="form-control" type="text" id="username" name="username" label="Username" placeholder="username"/>
	        <s:password class="form-control" id="password" name="password" label="Password" placeholder="*****"/>
	        <s:submit class="btn" value="Login"/>
	    </s:form>
	    <br><br>
	    <a href="register.jsp">Registrati adesso!</a> <!-- bisognerà chiamare displayCheckBoxCategories() -->
	</div>
	  
</body>

</html>