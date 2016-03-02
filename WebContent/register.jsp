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
			
			<div id="message"><s:property escapeHtml="false" value="message"/></div>
			
			<s:form id="reg_form" name="register" action="register" method="POST">
				<s:textfield class="form-control" type="text" id="name" name="name" label="Nome" placeholder="nome" size="30"/>
		        <s:textfield class="form-control" type="text" id="surname" name="surname" label="Cognome" placeholder="cognome" size="30"/>
				<s:textfield class="form-control" type="text" id="username" name="username" label="Username" placeholder="username" size="30"/>
		        <s:textfield class="form-control" type="email" id="email" name="email" label="E-mail" placeholder="example@lab.it" size="30"/>
		        <s:password class="form-control" id="password" name="password" label="Password" placeholder="max 10 caratteri" maxlength="10" size="30"/>
				<s:checkboxlist class="form-control" id="categories" name="categories" label="Categorie" value="defaultCategories" list="categories"/>
				<s:submit class="btn" value="Registrati"/>
			</s:form>
			<br><br>
	    	<a href="index.jsp">Torna alla Home</a>
        </div>

</body>

</html>