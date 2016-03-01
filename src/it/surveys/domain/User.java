package it.surveys.domain;

/**
 * La classe User modella la rispettiva entita' del Database e rappresenta
 * quindi una delle classi del dominio dell'applicazione.
 * @author Luca Talocci
 * @version 1.0 06/02/2016
 */
public class User {
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	
    /**
     * Il Costruttore di default, senza parametri.
     * Inizializza gli attributi con una stringa vuota.
     */
	public User(){
		this.username = "";
		this.password = "";
		this.email = "";
		this.name = "";
		this.surname = "";
	}
	
	/**
	 * Il Costruttore con parametri.
	 * @param username String lo username dell'utente
	 * @param password String la password dell'utente
	 * @param email String l'e-mail dell'utente
	 * @param name String il nome dell'utente
	 * @param surname String il cognome dell'utente
	 */
	public User(String username, String password, String email, String name, String surname){
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
