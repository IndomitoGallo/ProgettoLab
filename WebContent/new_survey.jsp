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
	<p>Qui di seguito puoi creare un nuovo sondaggio.</p>
	<s:form id="survey_form" name="createSurvey" action="createSurvey" method="POST">
		<s:textfield class="form-control" type="text" id="question" name="question" label="name" size="30"/>
		<s:textfield class="form-control" type="text" id="answer1" name="answers[0]" label="answer1" size="30"/>
		<s:textfield class="form-control" type="text" id="answer2" name="answers[2]" label="answer2" size="30"/>
		<s:textfield class="form-control" type="text" id="answer3" name="answers[3]" label="answer3" size="30"/>
        <s:textfield class="form-control" type="text" id="answer4" name="answers[4]" label="answer4" size="30"/>
        <s:checkboxlist class="form-control" label="Categorie" name="categories" id="categories" list="{'Categoria1','Categoria2','Categoria3','Categoria4'}"/>
		<s:submit class="btn" value="Crea"/>
	</s:form>
	<br><br>
	<a href="admin.jsp">Indietro</a>
	  
</body>

</html>