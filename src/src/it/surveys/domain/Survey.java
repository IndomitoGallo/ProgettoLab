package it.surveys.domain;

/**
 * La classe Survey modella la rispettiva entita' del database e rappresenta quindi
 * una delle classi del dominio dell'applicazione.
 * @author L.Camerlengo
 * @version 1.0 06/02/2016
 */
public class Survey {
    private int id;
    private String question;
    private String[] answers;
    
    /**
     * Il Costruttore di default, senza parametri.
     * Inizializza gli attributi con una stringa vuota o con null.
     */
	public Survey(){
		this.question = "";
		this.answers = null;
	}
	
	/**
	 * Il Costruttore con parametri.
	 * @param question String la domanda del sondaggio
	 * @param answers String[] un array contenente le possibili risposte al sondaggio
	 */
	public Survey(String question, String[] answers){
		this.question = question;
		this.answers = answers;
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
}
