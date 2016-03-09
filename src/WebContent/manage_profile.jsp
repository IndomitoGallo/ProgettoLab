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
	
	<div id="message"><s:property escapeHtml="false" value="message"/></div>
	<br>
	
	<s:form id="profile_form" name="updateProfile" action="updateProfile" method="POST">
		<s:textfield class="form-control" type="text" id="name" name="name" label="Nome" placeholder="nome" size="30" value="%{name}"/>
        <s:textfield class="form-control" type="text" id="surname" name="surname" label="Cognome" placeholder="cognome" size="30" value="%{surname}"/>
		<s:textfield class="form-control" type="text" id="username" name="username" label="Username" placeholder="username" size="30" value="%{username}"/>
        <s:textfield class="form-control" type="email" id="email" name="email" label="E-mail" placeholder="example@lab.it" size="30" value="%{email}"/>
        <s:password class="form-control" id="password" name="password" label="Password" placeholder="*****" maxlength="10" size="30" value="%{password}"/>
		<s:checkboxlist class="form-control" id="categories" name="categories" label="Categorie" value="defaultCategories" list="categoriesCheckBox"/>
		<s:submit class="btn" value="Aggiorna"/>
	</s:form>
	<br><br>
	<a href="displayAllowedSurveys.action">Indietro</a>
	  
</body>

</html>