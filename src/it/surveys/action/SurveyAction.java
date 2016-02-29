package it.surveys.action;

import com.opensymphony.xwork2.ActionContext;

import com.opensymphony.xwork2.ActionSupport;
import it.surveys.domain.Survey;
import it.surveys.model.SurveyManager;
import java.util.Map;

/**
 * Questa classe e' la "action" che gestisce tutte le attivita' che riguardano i sondaggi.
 * Essa e' un'estensione del Controller e fa da ponte tra le azioni client-side dell'utente
 * e le operazioni della logica applicativa.
 * Ogni suo metodo rappresenta una funzione dell'applicazione. 
 * @author L.Camerlengo
 * @version 1.0,26/02/16
 */
public class SurveyAction extends ActionSupport{
	
    private static final long serialVersionUID = 1L;
    private int id;
    private String question;
    private String[] answers = new String[4];
    private int[] categories;
    private String answer;
    private String message;
    private String output;
    
    /**
     * Il metodo createSurvey() viene attivato dopo che il responsabile ha effettuato l'accesso nella
     * sua pagina personale ed ha cliccato su "Crea sondaggio".
     * @return String esito della creazione di un determinato sondaggio. 
     */
    public String createSurvey(){
        if(validateSurvey()==false){
            setMessage("Non sono stati inseriti correttamente tutti i campi obbligatori.<br>" + 
            			"Oppure non è stata selezionata alcuna categoria.<br>" +
            			"Oppure ricorda che se le risposte sono due, devono essere Si/No.");
            return "fail";
        }
        SurveyManager sm = SurveyManager.getSurveyManager();
        Survey s = new Survey();
        s.setQuestion(question);
        s.setAnswers(answers);
        String result = sm.insert(s, categories);
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile creare il sondaggio.");
            return "fail";
        }
        return "success";
    }
    
    /**
     * Il metodo deleteSurvey() viene attivato se dopo che il responsabile ha effettuato l'accesso
     * nella sua pagina personale e dopo che ha effettuato un click sul pulsante "Cancella" di un determinato
     * sondaggio, esso ha clicclato su "Ok" nel relativo alert di conferma. 
     * @return String esito della cancellazione del sondaggio nel database.
     */
    public String deleteSurvey(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        Survey s = new Survey();
        s.setId(id);
        String result = sm.delete(s);
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile effettuare la cancellazione del sondaggio.");
            return "fail";
        }
        return "success";
    }
    
    /**
     * Il metodo displayResults() viene attivato dopo che il responsabile ha effettuato l'accesso nella sua 
     * pagina personale ed ha effettuato un click sul pulsante "Risultati" di un determinato sondaggio.
     * @return String esito del prelevamento dei risultati di un determinato sondaggio.
     */
    public String displayResults(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        String result = sm.displayResults(id);
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile prelevare i risultati del sondaggio.");
            return "fail";
        }
        setOutput(result);
        return "success";
    }
    
    /**
     * Il metodo displayCreatedSurveys() viene attivato dopo che il responsabile ha effettuato l'accesso nella
     * sua pagina personale.
     * @return String esito del prelevamento dei sondaggi creati. 
     */
    public String displayCreatedSurveys(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        String result = sm.displayCreatedSurveys();
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile caricare i sondaggi creati.");
            return "fail";
        }
        setOutput(result);
        return "success";
    }
    
    /**
     * Il metodo displayAllowedSurveys() viene attivato quando il cliente ha effettuato l'accesso nella sua
     * pagina personale.
     * @return String esito del prelevamento dei sondaggi preferiti del cliente. 
     */
    public String displayAllowedSurveys(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String result = sm.displayAllowedSurveys((int)session.get("idUser"));
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile caricare i sondaggi di tuo interesse.");
            return "fail";
        }
        setOutput(result);
        return "success";
    }
    
    /**
     * Il metodo displaySurvey() viene attivato dopo che il cliente ha effettuato l'accesso nella sua pagina
     * personale ed ha effettuato un click sul pulsante "Visualizza" di un determinato sondaggio.
     * @return String esito del prelevamento dei dati di un determinato sondaggio. 
     */
    public String displaySurvey(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        Survey s = new Survey();
        s.setId(id);
        String result = sm.retrieve(s);
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile visualizzare il sondaggio selezionato.");
            return "fail";
        }
        setOutput(result);
        return "success";
    }
    
    /**
     * Il metodo answerSurvey() viene attivato dopo che il cliente ha visualizzato un determinato sondaggio con le relative
     * risposte, ha selezionato una risposta ed ha effettuato un click sul pulsante "Rispondi".
     * @return String esito dell'inserimento della risposta.
     */
    public String answerSurvey(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        Map<String, Object> session = ActionContext.getContext().getSession();
        String result = sm.insertAnswer(id,(int)session.get("idUser"), answer);
        if(result.equals("db_fail")){
            setMessage("Non e' stato possibile inserire la risposta.");
            return "fail";
        }
        return "success";
    }
    
    /**
     * Il metodo validateSurvey() effettua un controllo dei campi required in fase di creazione del sondaggio da parte del responsabile.
     * Restituisce false se Ã¨ vuoto almeno uno dei seguenti campi required: la domanda del sondaggio, la categoria
     * alla quale associare il sondaggio e le minimo due risposte da associare al sondaggio. Restituisce true altrimenti. 
     * @return boolean esito della validazione 
     */
    private boolean validateSurvey(){
    	//controllo che i campi required sono inseriti correttamente
        if(question.isEmpty() || answers[0].isEmpty() || answers[1].isEmpty())
            return false;
        //se le risposte sono solo due, devono essere Si o No
        if(answers.length == 2) 
        	if((answers[0] != "Si" && answers[1] != "No") || (answers[0] != "No" && answers[1] != "Si"))
        		return false;
        //controllo che almeno una categoria è associata al sondaggio inserito
        if(categories.length < 1)
        	return false;
        return true;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int[] getCategories() {
        return categories;
    }

    public void setCategories(int[] categories) {
        this.categories = categories;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
        
        
        
	
}
