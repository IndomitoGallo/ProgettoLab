package it.surveys.domain;

/**
 * La classe Category modella la rispettiva entita' del Database e rappresenta
 * quindi una delle classi del dominio dell'applicazione.
 * @author Lorenzo Bernabei
 * @version 1.0 08/02/2016
 */
public class Category {
	private int id;
    private String name;
    
    //Costruttore
    public Category() {
		this.id = 0;
		this.name = "";
	}
    
    //Costruttore con parametri
    public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
