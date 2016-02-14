<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>

        <div class="card card-container reg">
			<h1>Registrazione</h1>
			<img id="reg-profile-img" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
			<s:form id="reg_form" name="register" action="UserAction" method="POST">
				<s:textfield class="form-control" type="text" id="name" name="name" label="name" placeholder="name"/>
		        <s:textfield class="form-control" type="text" id="surname" name="surname" label="surname" placeholder="surname"/>
				<s:textfield class="form-control" type="text" id="username" name="username" label="username" placeholder="username"/>
		        <s:textfield class="form-control" type="email" id="email" name="email" label="e-mail" placeholder="example@lab.it"/>
		        <s:password class="form-control" key="password" id="password" name="password" placeholder="*****" maxlength="10"/>
				<s:submit class="btn" value="Registrati"/>
			</s:form>
        </div>

</body>

</html>