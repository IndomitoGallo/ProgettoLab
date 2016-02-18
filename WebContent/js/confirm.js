function confirmation(idSurvey) {
	var answer = confirm("Vuoi davvero cancellare il sondaggio?");
	if(answer) {
		window.location.assign("admin.jsp");
		//bisognerà metterci "deleteSurvey.action/id=idSurvey" o una cosa simile.
	}
}