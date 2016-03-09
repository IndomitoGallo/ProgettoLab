package it.surveys.model;

import java.util.LinkedHashMap;

import it.surveys.domain.Survey;

/**
 * La classe SurveyManager classe e' il cuore del model e gestisce tutte le azioni che riguardano i sondaggi,
 * difatti offre i suoi servizi alla classe SurveyAction.
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
        if(sm == null)
            sm = new SurveyManager();
        return sm;      
    }
    
    /**
     * Questo metodo chiama due metodi dei livelli inferiori per effettuare l'inserimento del sondaggio sul
     * database; In particolare si inserisce prima il sondaggio nel database e se tutto va a buon fine si inseriscono
     * le relative associazioni con le categorie.
     * Restituisce "success" se l'inserimento del sondaggio con le relative associazioni con le categorie sono state
     * inserite correttamente nel database, "db_fail" altrimenti.
     * @param s Survey
     * @param categories int[] array degli id delle categorie
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
	    return result;
    }
    
    /**
     * Questo metodo utilizza il corrispondente del livello inferiore in SurveyDAO per recuperare
     * i dati del sondaggio e restituisce una LinkedHashMap<String, String> in cui chiave e valore sono
     * identici e corrispondono alle possibili risposte del sondaggio. Ovviamente hanno due significati
     * diversi, la chiave corrisponde al contenuto vero e proprio che poi andrà nel DB, il valore
     * corrisponde al label da visualizzare nella pagine jsp.
     * La collezione cosi' costruita costituira' il contenuto dell'attributo "list" del tag
     * <s:radio> secondo la sintassi di Struts2.
     * Infine, solo come valore di appoggio da passare alla action, viene inserita nella LinkedHashMap anche
     * la domanda del sondaggio.
     * In caso di errore viene restituito null.
     * @param s Survey
     * @return LinkedHashMap<String, String> collezione di coppie (key, value)
     */
    public LinkedHashMap<String, String> retrieve(Survey s){
        String result = SurveyDAO.retrieve(s);
        if(result.equals("fail")){
            return null;
        } 
        String[] answers = s.getAnswers();
        
        LinkedHashMap<String, String> survey = new LinkedHashMap<>();
        survey.put("question", s.getQuestion());
        
        if(answers.length==2){
        	survey.put(answers[0], answers[0]);
        	survey.put(answers[1], answers[1]);
        }else if(answers.length==3){
        	survey.put(answers[0], answers[0]);
        	survey.put(answers[1], answers[1]);
        	survey.put(answers[2], answers[2]);
        }else{
        	survey.put(answers[0], answers[0]);
        	survey.put(answers[1], answers[1]);
        	survey.put(answers[2], answers[2]);
        	survey.put(answers[3], answers[3]);
        }
        
        return survey;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente metodo del livello inferiore in SurveyDAO.
     * Se la cancellazione del sondaggio e' andata a buon fine restituisce "success", "db_fail" altrimenti.
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
     * Questo metodo chiama semplicemente il corrispondente metodo del livello inferiore in SurveyDCS.
     * Restituisce una stringa formattata in HTML con una tabella contenente i risultati di un determinato sondaggio,
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
     * Questo metodo chiama semplicemente il corrispondente metodo del livello inferiore in SurveyDCS.
     * Restituisce stringa formattata in HTML con una tabella contenente i sondaggi presenti nel database, altrimenti
     * se non e' presente alcun sondaggio nel database viene restiuito un messaggio sotto forma di stringa, altrimenti 
     * se qualcosa e' andato storto restituisce "db_fail".
     * @return String stringa formattata opportunamente
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
     * Restituisce una stringa formattata in HTML con una tabella contenente i sondaggi appartenenti
     * alle categorie di interesse per l'utente presenti nel database, altrimenti se non e' presente
     * alcun sondaggio associato alle categorie di interesse dell'utente restituisce un messaggio sotto
     * forma di stringa, altrimenti se qualcosa e' andato storto restituisce "db_fail".
     * @param idUser int
     * @return String stringa formattata opportunamente 
     */
    public String displayAllowedSurveys(int idUser){
        String result=SurveyDCS.displayAllowedSurvey(idUser);
        if(result.equals("fail")){
            return "db_fail";
        }
        return result;
    }
    
    /**
     * Questo metodo chiama semplicemente il corrispondente metodo del livello inferiore in SurveyDCS.
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
