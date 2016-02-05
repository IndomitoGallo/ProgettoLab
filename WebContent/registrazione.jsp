<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>
	<h1>Registrazione</h1>
	<s:form id="reg_form" name="reg_form" action="UserAction" method="POST">
		<s:textfield name="nome" label="Nome"/>
        <br>
        <s:textfield name="cognome" label="Cognome"/>
        <br>
		<s:textfield name="username" label="Username"/>
        <br>
        <s:textfield name="email" label="e-mail"/>
        <br>
        <s:password key="Password" maxlength="10"/>
        <br>	
		<s:submit value="Registrati"/>
	</s:form>
</body>

</html>