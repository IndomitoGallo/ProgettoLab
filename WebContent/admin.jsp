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
	<p>	Qui puoi: </p>
	<ul>
		<li>Inserire e/o rimuovere sondaggi.</li>
		<li>Vedere i risultati di un sondaggio</li>
		<li>Inserire e/o rimuovere categorie</li>
	</ul>
	
	<a href="new_survey.jsp">Crea nuovo sondaggio</a>
	<a href="new_category.jsp">Crea nuova categoria</a>
	<a href="delete_category.jsp">Elimina categoria esistente</a>
	
	<br><br>
	
	<table>
		<tr>
			<th>NomeSondaggio</th>
			<th>Azione1</th>
			<th>Azione2</th>
		</tr>
        <tr>
        	<td>Sondaggio1</td>          
        	<td><a href="results.jsp">view results</a></td> 
        	<td><a href="delete.jsp">delete</a></td>
        </tr>
		<tr>
        	<td>Sondaggio2</td>          
        	<td><a href="results.jsp">view results</a></td> 
        	<td><a href="delete.jsp">delete</a></td>
        </tr>
        <tr>
        	<td>Sondaggio3</td>          
        	<td><a href="results.jsp">view results</a></td> 
        	<td><a href="delete.jsp">delete</a></td>
        </tr>
		<tr>
        	<td>Sondaggio4</td>          
        	<td><a href="results.jsp">view results</a></td>
        	<td><a href="delete.jsp">delete</a></td> 
        </tr>
    </table>  
    <!-- I link <a> sono solo di prova -->
	  
</body>

</html>