package it.surveys.model;

import it.surveys.domain.Survey;

/**
 * Questa classe e' il cuore del model e gestisce tutte le azioni che riguardano i sondaggi,
 * difatti offre i suoi servizi alla SurveyAction.
 * Il manager, poi, utilizza i servizi del secondo livello del model: le classi DAO e DCS.
 * Saranno queste ultime a dialogare con il Database.
 * Infine il manager e' stato implementato come una factory con pattern singleton.
 * @author L.Camerlengo
 * @version 1.0 26/02/16
 */
public class SurveyManager {
    
    private static SurveyManager sm;
    
    /**
     * Il metodo e' l'implementazione del pattern singleton per avere una sola
     * istanza della classe. Quando viene chiamato per la prima volta istanzia
     * e ritorna un nuovo oggetto di tipo SurveyManager, mentre le volte successive,
     * ritorna l'oggetto che e' stato istanziato la prima volta e che e' stato
     * salvato in un attributo privato e statico, {@link #sm}.
     * @return sm l'oggetto singleton SurveyManager 
     */
    public static SurveyManager getSurveyManager(){
        if(sm==null)
            sm=new SurveyManager();
        return sm;      
    }
    
    /**
     * Questo metodo chiama due metodi dei livelli inferiori per effettuare l'inserimento del sondaggio sul
     * database; In particolare si inserisce prima il sondaggio nel database e se tutto va a buon fine si inseriscono
     * le relative associazioni con le categorie.
     * Restituisce "success" se l'inserimento del sondaggio con le relative associazioni con le categorie sono state
     * inserite correttamente nel database, "db_fail" altrimenti.
     * @param s Survey
     * @param categories int[]
     * @return String esito dell'inserimento
     */
    public String insert(Survey s,int[] categories){
	    String result=SurveyDAO.insert(s);
	    if(result.equals("fail")){
	        return "db_fail";
	    }
	    result=SurveyDCS.insertCategoriesAssociation(s.getId(), categories);
	    if(result.equals("fail")){
	    	/*Se viene inserito con successo il sondaggio ma non le categorie
			bisogna abortire l'intera procedura. Si elimina il sondaggio inserito
			con successo precedentemente, il che si traduce nell'eliminazione
			del record ma anche delle categorie associate e inserite fino a quel
			momento (prima dell'eccezione).*/
			SurveyDAO.delete(s);
	        return "db_fail";
	    }
	    return "success";
    }
    
    /**
     * Questo metodo utilizzando il corrispondente del livello inferiore in surveyDAO, crea una tabella 
     * sotto forma di stringa contenente la domanda del sondaggio e tutte le possibili risposte selezionabili dal cliente per rispondere ad
     * un determinato sondaggio.
     * Restituisce un tabella sotto forma di stringa contenente la domanda e le possibili risposte selezionabili di un determinato sondaggio se
     * il metodo retrieve del livello inferiore ha esito positivo, "db_fail" altrimenti.
     * @param s Survey
     * @return String tabella contenente la domanda e le possibili risposte selezionabili di un determinato sondaggio.
     */
    public String retrieve(Survey s){
        String result=SurveyDAO.retrieve(s);
        String[] answers=s.getAnswers();
        if(result.equals("fail")){
            return "db_fail";
        } 
        
        result="<table class=\"withform\">" +
                "<tr>" +
                    "<th>Sondaggio</th>" +
                "</tr>";
        result=result+"<tr>" + "<td>"+s.getQuestion()+"</td></tr>";
        
        if(answers.length==2){
            result=result+"<tr><td><s:form id=\"answer_form\" name=\"answer\" action=\"answerSurvey\" method=\"POST\">" +
                   "<s:radio class=\"form-control\" id=\"answer\" name=\"answer\" label=\"Risposte\" list=\"# {'" + answers[0] + "':'" + answers[0] + "','" +
                   answers[1] + "':'" + answers[1] + "'}\" value=\"'" + answers[0] + "'\"/>";
        }else if(answers.length==3){
            result=result+"<tr><td><s:form id=\"answer_form\" name=\"answer\" action=\"answerSurvey\" method=\"POST\">"+
            		"<s:radio class=\"form-control\" id=\"answer\" name=\"answer\" label=\"Risposte\"list=\"# {'" + answers[0] + "':'" + answers[0] + "','" +
                    answers[1] + "':'" + answers[1] + "','" + answers[2] + "':'" + answers[2] + "'}\" value=\"'" + answers[0] + "'\"/>";
        }else{
            result=result+"<tr><td><s:form id=\"answer_form\" name=\"answer\" action=\"answerSurvey\" method=\"POST\">"+
                   "<s:radio class=\"form-control\" id=\"answer\" name=\"answer\" label=\"Risposte\"list=\"# {'" + answers[0] + "':'" + answers[0] + "','" +
                   answers[1] + "':'" + answers[1] + "','" + answers[2] + "':'" + answers[2] + "','" + answers[3] + "':'" + answers[3] + "'}\" value=\"'" + answers[0] + "'\"/>";
        }
        result=result + "<s:submit class=\"btn\" value=\"Rispondi\"/></s:form></td></tr></table>";
        
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente del livello inferiore in SurveyDAO.
     * Se la cancellazione del sondaggio è andata a buon fine restituisce "success", "db_fail" altrimenti.
     * @param s Survey
     * @return String esito della cancellazione del sondaggio
     */
    public String delete(Survey s){
        String result = SurveyDAO.delete(s);
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente del livello inferiore in SurveyDCS.
     * Restituisce una tabella sotto forma di stringa contenente i risultati di un determinato sondaggio,
     * altrimenti se qualcosa e' andato storto restituisce "db_fail".
     * @param idSurvey int
     * @return String Risultati di un determinato sondaggio
     */
    public String displayResults(int idSurvey){
        String result = SurveyDCS.displayResults(idSurvey);
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente del livello inferiore in SurveyDCS.
     * Restituisce una tabella sotto forma di stringa contenente i sondaggi presenti nel database, altrimenti
     * se non e' presente alcun sondaggio nel database viene restiuito un messaggio sotto forma di stringa
     * che notifica al responsabile che non sono presenti sondaggi, altrimenti 
     * se qualcosa e' andato storto restituisce "db_fail".
     * @return String sondaggi presenti nel database.
     */
    public String displayCreatedSurveys(){
        String result=SurveyDCS.displayCreatedSurveys();
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente del livello inferiore in SurveyDCS.
     * Restituisce una tabella sotto forma di stringa contenente i sondaggi appartenenti alle categoire di interesse per l'utente presenti nel 
     * database, altrimenti se non e' presente alcun sondaggio associato alle categorie di interesse dell'utente restiuisce un
     * messaggio sotto forma di stringa, che invita l'utente ad aggiungere nuove categorie di interesse, altrimenti se qualcosa e'
     * andato storto restituisce "db_fail".
     * @param idUser int
     * @return String Sondaggi delle relative categorie preferite dell'utente. 
     */
    public String displayAllowedSurveys(int idUser){
        String result=SurveyDCS.displayAllowedSurvey(idUser);
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente del livello inferiore in SurveyDCS.
     * Restituisce "success" se l'inserimento della risposta e' andato a buon fine, "db_fail" altrimenti.
     * @param idSurvey int
     * @param idUser int
     * @param answer String
     * @return String esito dell'inserimento della risposta
     */
    public String insertAnswer(int idSurvey, int idUser, String answer){
        String result = SurveyDCS.insertAnswer(idSurvey, idUser, answer);
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }

}
