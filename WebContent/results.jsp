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
	<p>	Ecco i risultati del sondaggio selezionato: </p>
	
	<br>
	
	<div id="message"><s:property escapeHtml="false" value="message"/></div>
	
	<s:property escapeHtml="false" value="output"/>
	
    <br><br>
	<a href="displayCreatedSurveys.action">Indietro</a>
	  
</body>

</html>