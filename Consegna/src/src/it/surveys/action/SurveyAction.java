package it.surveys.action;

import com.opensymphony.xwork2.ActionContext;

import com.opensymphony.xwork2.ActionSupport;
import it.surveys.domain.Survey;
import it.surveys.model.SurveyManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

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
    /** le risposte inserite in fase di creazione del sondaggio. */
    private String[] answers = new String[4];
	/** le categorie associate al sondaggio. */
    private int[] categories;
    /** la risposta al sondaggio da parte dell'utente. */
    private String answer;
	/** eventuale messaggio di errore da visualizzare nella view. */
	private String message;
	/** eventuale output da visualizzare nella view. */
	private String output;
	/** le risposte da visualizzare nel form di risposta al sondaggio. */
	private LinkedHashMap<String, String> answersRadio;
	/** le risposte preselezionate da visualizzare nel form di risposta al sondaggio. */
	private HashMap<String, String> defaultAnswers;
    
	/**
     * Il metodo createSurvey() viene attivato dopo che il responsabile ha effettuato l'accesso nella
     * sua pagina personale ed ha cliccato su "Crea sondaggio".
     * Vengono validati i dati del form e viene chiamato il metodo insert del SurveyManager.
     * @return String esito della creazione di un determinato sondaggio. 
     */
    public String createSurvey(){
        if(validateSurvey()==false){
            setMessage("Non sono stati inseriti correttamente tutti i campi obbligatori.<br>" + 
            			"Oppure non e' stata selezionata alcuna categoria.<br>" +
            			"Oppure, se hai inserito due risposte, sono diverse da Si/No.");
            return "fail";
        }
        SurveyManager sm = SurveyManager.getSurveyManager();
        Survey s = new Survey(StringEscapeUtils.escapeSql(question), answers);
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
     * sondaggio, esso ha cliccato su "Ok" nel relativo alert di conferma.
     * Viene chiamato il corrispondente metodo del SurveyManager.
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
     * Viene chiamato il corrispondente metodo del SurveyManager.
     * Infine effettua i set degli attributi con i dati da mostrare all'utente.
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
     * Viene chiamato il corrispondente metodo del SurveyManager.
     * Infine effettua i set degli attributi con i dati da mostrare all'utente.
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
     * Fa uso dei dati della sessione e chiama il corrispondente metodo del SurveyManager.
     * Infine effettua i set degli attributi con i dati da mostrare all'utente.
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
     * personale ed ha effettuato un click sul pulsante "Rispondi" di un determinato sondaggio.
     * Viene chiamato il metodo retrieve del SurveyManager.
     * Infine effettua i set degli attributi con i dati da mostrare all'utente.
     * @return String esito del prelevamento dei dati di un determinato sondaggio. 
     */
    public String displaySurvey(){
        SurveyManager sm = SurveyManager.getSurveyManager();
        Survey s = new Survey();
        s.setId(id);
        LinkedHashMap<String, String> result = sm.retrieve(s);
        if(result == null){
            setMessage("Non e' stato possibile visualizzare il sondaggio selezionato.");
            setAnswersRadio(new LinkedHashMap<String, String>());
            setDefaultAnswers(new HashMap<String, String>());
            return "fail";
        }
        //Set degli attributi con i dati da mostrare
        setQuestion(result.get("question"));
        result.remove("question"); //viene rimossa la domanda, che era solo di appoggio nella HashMap
        setAnswersRadio(result);
        
		//Per default viene checkato il primo elemento dell'HashMap answersRadio
		setDefaultAnswers(new HashMap<String, String>());
		Set<String> keySet = result.keySet();
		Iterator<String> it = keySet.iterator();
		String key = it.next();
		getDefaultAnswers().put(key, result.get(key));
        
        return "success";
    }
    
    /**
     * Il metodo answerSurvey() viene attivato dopo che il cliente ha visualizzato un determinato sondaggio con le relative
     * risposte, ha selezionato una risposta ed ha effettuato un click sul pulsante "Rispondi".
     * Fa uso dei dati della sessione e chiama il metodo insertAnswer del SurveyManager.
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
     * Il metodo validateSurvey() effettua un controllo dei campi required in fase di creazione
     * del sondaggio da parte del responsabile. Se soltanto uno di essi non è stato inserito
     * dall'admin oppure se l'admin non ha selezionato almeno una categoria, il controllo ha
     * esito negativo, positivo altrimenti.
     * Inoltre si ha esito negativo anche nel caso in cui le risposte sono due e sono diverse da Si/No. 
     * @return boolean esito della validazione 
     */
    private boolean validateSurvey(){
    	//controllo che i campi required sono inseriti correttamente
        if(question.isEmpty() || answers[0].isEmpty() || answers[1].isEmpty())
            return false;
        //se le risposte sono solo due, devono essere Si o No
        if(answers[2].isEmpty()) 
        	if((!(answers[0].equals("Si")) && !(answers[1].equals("Si"))) || (!(answers[0].equals("No")) && !(answers[1].equals("No"))))
        		return false;
        //se le risposte sono quattro, la terza non deve essere vuota
        if(!answers[3].isEmpty())
        	if(answers[2].isEmpty())
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
        
    public LinkedHashMap<String, String> getAnswersRadio() {
		return answersRadio;
	}

	public void setAnswersRadio(LinkedHashMap<String, String> answersRadio) {
		this.answersRadio = answersRadio;
	}

	public HashMap<String, String> getDefaultAnswers() {
		return defaultAnswers;
	}    
	
	public void setDefaultAnswers(HashMap<String, String> defaultAnswers) {
		this.defaultAnswers = defaultAnswers;
	}
	
}
