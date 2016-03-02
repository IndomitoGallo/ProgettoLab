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
	<p>Qui di seguito puoi cancellare una categoria esistente.</p>
	<s:form id="category_form" name="deleteCategory" action="deleteCategory" method="POST">
		<s:radio class="form-control" id="categories" name="id" label="Categorie" list="# {'1':'Categoria1','2':'Categoria2','3':'Categoria3','4':'Categoria4'}" value="1"/>
		<s:submit class="btn" value="Elimina"/>
	</s:form>
	<br><br>
	<a href="admin.jsp">Indietro</a>
	  
</body>

</html>