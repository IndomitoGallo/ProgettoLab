<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>

	<h1>Benvenuto nella tua pagina personale!</h1>
	<p>Qui di seguito puoi vedere i tuoi dati personali e puoi modificarli.</p>
	<s:form id="profile_form" name="updateProfile" action="updateProfile" method="POST">
		<s:textfield class="form-control" type="text" id="name" name="name" label="name" placeholder="name" size="30"/>
        <s:textfield class="form-control" type="text" id="surname" name="surname" label="surname" placeholder="surname" size="30"/>
		<s:textfield class="form-control" type="text" id="username" name="username" label="username" placeholder="username" size="30"/>
        <s:textfield class="form-control" type="email" id="email" name="email" label="e-mail" placeholder="example@lab.it" size="30"/>
        <s:password class="form-control" key="password" id="password" name="password" placeholder="*****" maxlength="10" size="30"/>
        <s:checkboxlist class="form-control" label="Cagegorie" name="categories" id="categories" list="{'Categoria1','Categoria2','Categoria3','Categoria4'}"/>
		<s:submit class="btn" value="Aggiorna"/>
	</s:form>
	<br><br>
	<a href="user.jsp">Indietro</a>
	  
</body>

</html>