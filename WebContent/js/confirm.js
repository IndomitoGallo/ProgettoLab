function confirmation(idSurvey) {
	var answer = confirm("Vuoi davvero cancellare il sondaggio?");
	if(answer) {
		window.location.assign("deleteSurvey.action?id=idSurvey");
	}
}