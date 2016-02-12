package it.surveys.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import it.surveys.domain.User;
import it.surveys.model.UserManager;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 12/02/2016
 */
public class UserAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String username;
	private String password;
	private int[] categories;
	private String email;
	private String name;
	private String surname;
	private String message;
	private String output;
	
	/**
	 * Il metodo register() si attiva nel momento in cui l'utente invia i dati di registrazione
	 * attraverso l'apposito form nella pagina registrazione.jsp.
	 * @return String
	 * @throws Exception
	 * @author Luca Talocci
	 */
	public String register() throws Exception {
		if(validateRegister() == false) {
			setMessage("Non sono stati inseriti correttamente tutti i campi obbligatori");
			return "fail";
		}
		else {
			UserManager usm = UserManager.getUserManager();
			User u = new User(username, password, email, name, surname);
			String outcome;
			outcome = usm.register(u, categories);
			if(outcome == "signup_db_fail") {
				setMessage("Errore di connessione al database: non è stato possibile effettuare la registrazione");
				return "fail";
			}
			else return "success";
		}
	}
	
	/**
	 * Il metodo login() si attiva nel momento in cui l'utente invia i dati di login
	 * attraverso l'apposito form.
	 * @return String
	 * @throws Exception
	 * @author Luca Talocci 
	 */
	public String login() throws Exception {
		if(validateLogin() == false) {
			setMessage("Non sono stati inseriti correttamente tutti i campi obbligatori");
			return "fail";
		}
		else {
			UserManager usm = UserManager.getUserManager();
			String outcome;
			outcome = usm.verifyLoginData(username, password);
			if(outcome == "false") {
				setMessage("Username o password inesistente");
				return "fail";
			}
			else if(outcome == "fail") {
				setMessage("Errore di connessione al database: non è stato possibile effettuare il login");
				return "fail";
			}
			else {
				//prova createSession
				Map<String, Object> session = ActionContext.getContext().getSession();
				((HttpSession) session).setAttribute("id", this.id);
				((HttpSession) session).setAttribute("name", this.username);
				((HttpSession) session).setAttribute("pwd", this.password);
				return "success";
			}
		}
	}
	
	/**
	 * Il metodo logout() si attiva nel momento in cui l'utente spinge il tasto di logout.
	 * @return String
	 * @throws Exception
	 * @author Luca Talocci 
	 */
	public String logout() throws Exception {
		//prova close session
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("id");
		session.remove("name");
		session.remove("pwd");
		return "success";
	}
	
	/**
	 * 
	 */
	public String displayProfile() {
		return "";
	}
	
	/**
	 * 
	 */
	public String updateProfile() {
		return "";
	}
	
	/**
	 * 
	 */
	public void createSession() {
		
	}
	
	/**
	 * 
	 */
	public void closeSession() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	private Boolean validateRegister() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	private Boolean validateLogin() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	private Boolean validateUpdateProfile() {
		return true;
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
