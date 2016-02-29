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
	<button id="logout">Logout</button>
	<p>	Qui puoi: </p>
	<ul style="list-style-type:circle;">
		<li>Inserire e/o rimuovere sondaggi</li>
		<li>Vedere i risultati di un sondaggio</li>
		<li>Inserire e/o rimuovere categorie</li>
	</ul>
	
	<a href="displayCheckBoxCategories.action?flag=survey">Crea nuovo sondaggio</a>
	<a href="new_category.jsp">Crea nuova categoria</a> <!-- bisognerà chiamare displayListCategories() -->
	<a href="delete_category.jsp">Elimina categoria esistente</a> <!-- bisognerà chiamare displayRadioCategories() -->
	
	<br><br>
	
	<table>
		<tr>
			<th>Sondaggio</th>
			<th>Risultati</th>
			<th>Cancellazione</th>
		</tr>
        <tr>
        	<td>Sondaggio1</td> <!-- bisognerà passare l'id del sondaggio come parametro sia in visualizza che in cancella -->         
        	<td><a href="results.jsp">Visualizza</a></td> <!-- in questo caso dovrei chiamare displayResults() --> 
        	<td><a onclick="confirmation()">Cancella</a></td>
        </tr>
		<tr>
        	<td>Sondaggio2</td>          
        	<td><a href="results.jsp">Visualizza</a></td> 
        	<td><a onclick="confirmation()">Cancella</a></td>
        </tr>
        <tr>
        	<td>Sondaggio3</td>          
        	<td><a href="results.jsp">Visualizza</a></td> 
        	<td><a onclick="confirmation()">Cancella</a></td>
        </tr>
		<tr>
        	<td>Sondaggio4</td>          
        	<td><a href="results.jsp">Visualizza</a></td> 
        	<td><a onclick="confirmation()">Cancella</a></td>
        </tr>
    </table>  
    <!-- I link <a> sono solo di prova -->
	  
</body>

</html>