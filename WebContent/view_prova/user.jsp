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
	<button id="logout">Logout</button>
	<p>
		Qui puoi vedere alcuni sondaggi che possono interessarti.<br>
		Clicca sul tasto "view" per visionare un sondaggio e per poter rispondere.
	</p>
	
	<table>
		<tr>
			<th>Sondaggio</th>
			<th>Rispondi</th>
		</tr>
        <tr>
        	<td>Sondaggio1</td> <!-- bisogner� passare l'id del sondaggio come parametro -->          
        	<td><a href="survey.jsp">Visualizza</a></td> <!-- in questo caso dovrei chiamare displaySurvey() -->
        </tr>
		<tr>
        	<td>Sondaggio2</td>          
        	<td><a href="survey.jsp">Visualizza</a></td> 
        </tr>
        <tr>
        	<td>Sondaggio3</td>          
        	<td><a href="survey.jsp">Visualizza</a></td> 
        </tr>
		<tr>
        	<td>Sondaggio4</td>          
        	<td><a href="survey.jsp">Visualizza</a></td> 
        </tr>
    </table>  
    <!-- I link <a> sono solo di prova -->
	  
	<p>Clicca sul tasto "Modifica Profilo" per visionare i tuoi dati e modificarli.</p>  
	<a href="displayProfile.action">Modifica profilo!</a>
	
</body>

</html>