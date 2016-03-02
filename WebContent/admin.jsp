<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>AziendaLab</title>
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<script type="text/javascript" src="js/confirm.js"></script>
</head>

<body>

	<h1>Benvenuto nella tua pagina personale!</h1>
	<button id="logout" onclick="window.location.assign('logout.action');">Logout</button>
	<p>	Qui puoi: </p>
	<ul style="list-style-type:circle;">
		<li>Inserire e/o rimuovere sondaggi</li>
		<li>Vedere i risultati di un sondaggio</li>
		<li>Inserire e/o rimuovere categorie</li>
	</ul>
	
	<button onclick="window.location.assign('displayCheckBoxCategories.action?flag=survey');">Crea nuovo sondaggio</button>
	<button onclick="window.location.assign('displayListCategories.action');">Crea nuova categoria</button>
	<button onclick="window.location.assign('displayRadioCategories.action');">Elimina categoria esistente</button>
	
	<br>
	
	<div id="message"><s:property escapeHtml="false" value="message"/></div>
	
	<s:property escapeHtml="false" value="output"/>
	  
</body>

</html>