package it.surveys.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import it.surveys.domain.User;
import it.surveys.model.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe è la "action" che gestisce tutte le attività che riguardano l'utente.
 * Essa è un'estensione del Controller e fa da ponte tra le azioni client-side dell'utente
 * e le operazioni della logica applicativa.
 * Ogni suo metodo rappresenta una funzione dell'applicazione. 
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 12/02/2016
 */
public class UserAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String password;
	/** le categorie scelte dall'utente. */
	private int[] categories;
	private String email;
	private String name;
	private String surname;
	/** eventuale messaggio di errore da visualizzare nella view. */
	private String message;
	/** eventuale output da visualizzare nella view. */
	private String output;
	/** le categorie da visualizzare nei form. */
	private HashMap<String, String> categoriesCheckBox;
	/** le categorie preselezionate da visualizzare nei form. */
	private HashMap<String, String> defaultCategories;
	
	/**
	 * Il metodo register() si attiva nel momento in cui l'utente invia i dati di registrazione
	 * attraverso l'apposito form. Vengono validati i dati del form e viene chiamato il corrispondente
	 * metodo dello UserManager.
	 * @return String successo o fallimento
	 * @author Luca Talocci
	 */
	public String register() {
		if(validateRegister() == false) {
			setMessage("Non sono stati inseriti tutti i campi.<br>" + 
					   "Oppure non e' stata selezionata alcuna categoria.");
			return "fail";
		}
		else {
			UserManager usm = UserManager.getUserManager();
			User u = new User(getUsername(), getPassword(), getEmail(), getName(), getSurname());
			String outcome;
			outcome = usm.register(u, getCategories());
			if(outcome.equals("verification_fail")) {
				setMessage("Username o Email gia' presenti!");
				return "fail";
			}
			if(outcome.equals("db_fail")) {
				setMessage("Non e' stato possibile effettuare la registrazione!");
				return "fail";
			}
			return "success";
		}
	}
	
	/**
	 * Il metodo login() si attiva nel momento in cui l'utente invia i dati di login
	 * attraverso l'apposito form. Vengono validati i dati del form, vengono verificati
	 * i dati di login e viene creata una nuova sessione.
	 * @return String successo (separato per Admin e Cliente) o fallimento
	 * @author Luca Talocci 
	 */
	public String login() {
		if(validateLogin() == false) {
			setMessage("Devi inserire username e password!");
			return "fail";
		}
		else {
			UserManager usm = UserManager.getUserManager();
			int outcome;
			outcome = usm.verifyLoginData(getUsername(), getPassword());
			if(outcome == 0) {
				setMessage("Username o password errati!");
				return "fail";
			}
			if(outcome == -1) {
				setMessage("Non e' stato possibile effettuare il login!");
				return "fail";
			}
			createSession(outcome);
			if (getUsername().equals("admin"))
				return "admin_success"; //per reindirizzare l'admin alla sua pagina personale
			return "success";
		}
	}

	/**
	 * Il metodo logout() si attiva nel momento in cui l'utente clicca sul tasto di logout.
	 * Viene chiusa la sessione esistente.
	 * @return String successo o fallimento
	 * @author Luca Talocci
	 */
	public String logout() {
		closeSession();
		return "success";
	}
	
	/**
	 * Il metodo displayProfile() si attiva nel momento in cui l'utente clicca sul tasto di
	 * gestione profilo. Fa uso dei dati della sessione e chiama il corrispondente metodo dello
	 * UserManager. Infine effettua i set degli attributi con i dati da mostrare all'utente.
	 * @return String successo o fallimento
	 * @author Lorenzo Bernabei 
	 */
	public String displayProfile() {
		UserManager usm = UserManager.getUserManager();
		User u = new User();

		Map<String, Object> session = ActionContext.getContext().getSession();
		u.setId((int)session.get("idUser")); //viene preso l'id dell'utente dalla sessione e settato l'oggetto
		
		ArrayList<HashMap<String, String>> outcome;
		outcome = usm.displayProfile(u);
		if(outcome == null) {
			setMessage("Non e' stato possibile visualizzare i dati del profilo!");
			setCategoriesCheckBox(new HashMap<String, String>());
			setDefaultCategories(new HashMap<String, String>());
			return "fail";
		}
		//vengono settati tutti i dati da mostrare all'utente
		setCategoriesCheckBox(outcome.get(0));
		setDefaultCategories(outcome.get(1));
		setUsername(u.getUsername());
		setPassword(u.getPassword());
		setEmail(u.getEmail());
		setName(u.getName());
		setSurname(u.getSurname());

		return "success";

	}
	
	/**
	 * Il metodo updateProfile() si attiva nel momento in cui l'utente invia i nuovi dati
	 * del profilo attraverso l'apposito form.
	 * Valida i dati del form e chiama il corrispondente metodo dello UserManager.
	 * @return String successo o fallimento
	 * @author Lorenzo Bernabei
	 */
	public String updateProfile() {
		if(validateUpdateProfile() == false) {
			setMessage("Non sono stati inseriti tutti i campi.<br>" + 
					   "Oppure non e' stata selezionata alcuna categoria.");
			return "fail";
		}
		else {
			UserManager usm = UserManager.getUserManager();
			User u = new User(getUsername(), getPassword(), getEmail(), getName(), getSurname());
			Map<String, Object> session = ActionContext.getContext().getSession();
			u.setId((int)session.get("idUser")); //viene preso l'id dell'utente dalla sessione e settato l'oggetto
			String outcome;
			outcome = usm.update(u, getCategories());
			if(outcome.equals("verification_fail")) {
				setMessage("Username o Email gia' presenti!");
				return "fail";
			}
			if(outcome.equals("db_fail")) {
				setMessage("Non e' stato possibile effettuare l'aggiornamento dei dati!");
				return "fail";
			}
			return "success";
		}
	}
	
	/**
	 * Il metodo createSession() crea una nuova sessione inserendo l'id dell'utente.
	 * @param idUser
	 * @author Lorenzo Bernabei, Luca Talocci
	 */
	public void createSession(int idUser) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("idUser", idUser);
	}
	
	/**
	 * Il metodo closeSession() chiude la sessione corrente rimuovendo l'id dell'utente.
	 * @author Lorenzo Bernabei, Luca Talocci
	 */
	public void closeSession() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("idUser");
	}
	
	/**
	 * Il metodo validateRegister() effettua un controllo sui campi required inseriti dall'utente 
	 * in fase di registrazione. Se soltanto uno di essi non è stato inserito dall'utente
	 * oppure se l'utente non ha selezionato almeno una categoria il controllo ha esito
	 * negativo, positivo altrimenti.
	 * @return Boolean
	 * @author Luca Talocci
	 */
	private Boolean validateRegister() {
		if(getUsername().isEmpty() || getPassword().isEmpty() || getEmail().isEmpty() ||
				getName().isEmpty() || getSurname().isEmpty())
			return false;
		//controllo che almeno una categoria è associata all'utente
        if(getCategories().length < 1)
        	return false;
		return true;
	}
	
	/**
	 * Il metodo validateLogin() effettua un controllo sui campi inseriti dall'utente 
	 * nel form di Login. Se soltanto uno di essi non è stato inserito dall'utente
	 * il controllo ha esito negativo, positivo altrimenti.
	 * @return Boolean
	 * @author Luca Talocci
	 */
	private Boolean validateLogin() {
		if(getUsername().isEmpty() || getPassword().isEmpty())
			return false;
		return true;
	}
	
	/**
	 * Il metodo validateUpdateProfile() effettua un controllo sui campi required inseriti dall'utente 
	 * nella pagina di modifica del profilo. Se soltanto uno di essi non è stato inserito dall'utente
	 * oppure se l'utente non ha selezionato almeno una categoria il controllo ha esito
	 * negativo, positivo altrimenti.
	 * @return Boolean
	 * @author Luca Talocci
	 */
	private Boolean validateUpdateProfile() {
		if(getUsername().isEmpty() || getPassword().isEmpty() || getEmail().isEmpty() ||
				getName().isEmpty() || getSurname().isEmpty())
			return false;
		//controllo che almeno una categoria è associata all'utente
        if(getCategories().length < 1)
        	return false;
		return true;
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

	public int[] getCategories() {
		return categories;
	}

	public void setCategories(int[] categories) {
		this.categories = categories;
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

	public HashMap<String, String> getCategoriesCheckBox() {
		return categoriesCheckBox;
	}

	public void setCategoriesCheckBox(HashMap<String, String> categoriesCheckBox) {
		this.categoriesCheckBox = categoriesCheckBox;
	}
	
	public HashMap<String, String> getDefaultCategories() {
		return defaultCategories;
	}

	public void setDefaultCategories(HashMap<String, String> defaultCategories) {
		this.defaultCategories = defaultCategories;
	} 
	
}
