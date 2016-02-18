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
	<p>Qui di seguito puoi aggiungere una nuova categoria diversa da quelle esistenti.</p>
	<table class="withform">
        <tr>
        	<td>Qui apparirà la lista delle categorie</td>
        </tr>
     </table>
	<s:form id="category_form" name="createCategory" action="createCategory" method="POST">
		<s:textfield class="form-control" type="text" id="category" name="name" label="Categoria" size="30"/>
		<s:submit class="btn" value="Inserisci"/>
	</s:form>	
	<br><br>
	<a href="admin.jsp">Indietro</a>
	  
</body>

</html>