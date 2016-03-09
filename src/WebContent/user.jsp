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
	<button id="logout" onclick="window.location.assign('logout.action');">Logout</button>
	<p>
		Qui puoi vedere alcuni sondaggi che possono interessarti a cui non hai ancora risposto.<br>
		Clicca sul tasto "visualizza" per visionare un sondaggio e per poter rispondere.
	</p>
	
	<div id="message"><s:property escapeHtml="false" value="message"/></div>
	
	<s:property escapeHtml="false" value="output"/>
	  
	<p>Clicca sul tasto "Modifica Profilo" per visionare i tuoi dati e modificarli.</p>  
	<button onclick="window.location.assign('displayProfile.action');">Modifica profilo</button>
	
</body>

</html>