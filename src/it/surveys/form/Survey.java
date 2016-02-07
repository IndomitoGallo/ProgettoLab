package it.surveys.form;
/**
 * La classe che modella la rispettiva entità del database e rappresenta quinidi
 * una delle classi del dominio dell'applicazione.
 * @author L.Camerlengo
 * @version 1.0,6/02/2016
 */
public class Survey {
    private int id;
    private String question;
    private String[] answers;
    
   
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
