<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>

        <div class="card reg-container">
			<h1>Registrazione</h1>
			<img id="reg-profile-img" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
			<s:form id="reg_form" name="register" action="register" method="POST">
				<s:textfield class="form-control" type="text" id="name" name="name" label="name" placeholder="name" size="30"/>
		        <s:textfield class="form-control" type="text" id="surname" name="surname" label="surname" placeholder="surname" size="30"/>
				<s:textfield class="form-control" type="text" id="username" name="username" label="username" placeholder="username" size="30"/>
		        <s:textfield class="form-control" type="email" id="email" name="email" label="e-mail" placeholder="example@lab.it" size="30"/>
		        <s:password class="form-control" key="password" id="password" name="password" placeholder="*****" maxlength="10" size="30"/>
				<s:checkboxlist class="form-control" label="Cagegorie" name="categories" id="categories" list="{'Categoria1','Categoria2','Categoria3','Categoria4'}"/>
				<s:submit class="btn" value="Registrati"/>
			</s:form>
			<br><br>
	    	<a href="index.jsp">Torna alla Home</a>
        </div>

</body>

</html>