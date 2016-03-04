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
	<p>
		Qui puoi visionare e rispondere al sondaggio selezionato.
	</p>
	
	<div id="message"><s:property escapeHtml="false" value="message"/></div>
	
	<table class="withform">
		<tr>
        	<th>Sondaggio</th>
        </tr>
        <tr>
        	<td><s:property escapeHtml="false" value="question"/></td>
        </tr>
        <tr>
        	<td>
        		<s:form id="answer_form" name="answerSurvey" action="answerSurvey?id=%{id}" method="POST">
        			<s:radio class="form-control" id="answer" name="answer" label="Risposte" value="defaultAnswers" list="answersRadio" />
        			<s:submit class="btn" value="Rispondi"/>
        		</s:form>
        	</td>
        </tr>
    </table>

	<br><br>
	<a href="displayAllowedSurveys.action">Indietro</a>
	
</body>

</html>