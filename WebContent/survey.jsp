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
	
	<table class="withform">
		<tr>
			<th>Sondaggio</th>
		</tr>
        <tr>
        	<td>Domanda</td>          
        </tr>
		<tr>
        	<td>	
        		<s:form id="answer_form" name="answer" action="answer" method="POST">					
                    <s:radio label="Risposte" name="answer" id="answer" list="# {'1':'Risposta1','2':'Risposta2','3':'Risposta3','4':'Risposta4'}" value="1"/>                    
					<s:submit class="btn" value="Rispondi"/>
				</s:form>
			</td>          
        </tr>
    </table>  
    <!-- I link <a> sono solo di prova -->
	<br><br>
	<a href="user.jsp">Indietro</a>
	
</body>

</html>