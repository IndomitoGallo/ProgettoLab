<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>
	<div class="container">
        <div class="card card-container">
        	<h1>Login</h1>
            <img id="profile-img" class="login-profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
            <s:form class="login_form" id="login_form" name="login_form" action="UserAction" method="POST">
                <s:textfield class="form-control" type="text" id="username" name="username" placeholder="Username" label="username"/>
                <s:password class="form-control" key="password" id="password" name="password" placeholder="*****"/>
                <s:submit class="btn btn-lg" value="Login"/>
            </s:form>
        </div>
    </div>
</body>

</html>