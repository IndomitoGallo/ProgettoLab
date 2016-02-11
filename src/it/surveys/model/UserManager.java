package it.surveys.model;

import java.util.ArrayList;

import it.surveys.domain.User;

/**
 * Questa classe è il cuore del model e gestisce tutte le azioni che riguardano l'utente,
 * difatti offre i suoi servizi alla UserAction.
 * Il manager, poi, utilizza i servizi del secondo livello del model: le classi DAO e DCS.
 * Saranno queste ultime a dialogare con il Database.
 * Infine il manager è stato implementato come una factory con pattern singleton.
 * @author Lorenzo Bernabei, Luca Talocci
 * @version 1.0 10/02/2016
 */
public class UserManager {
	
	private static UserManager usm;
	
	/**
	 * Il metodo e' l'implementazione del pattern singleton per avere una sola
	 * istanza dell'utility. Quando viene chiamato per la prima volta, istanzia
	 * e ritorna un nuovo oggetto di tipo UserManager, mentre le volte successive,
	 * ritorna l'oggetto che e' stato istanziato la prima volta e che e' stato
	 * salvato nell'attributo, privato e statico, {@link #usm}
	 * @return usm l'oggetto singleton UserManager 
	 */  
	public static UserManager getUserManager() {
		if(usm == null)
			usm = new UserManager();
		return usm;
	}
	
	/**
	 * Questo metodo chiama vari metodi del livello inferiore per effettuare la registrazione
	 * dell'utente. In particolare prima si verifica se i dati inseriti sono corretti e in
	 * quel caso si inseriscono l'utente e le associazioni con le categorie scelte.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param u User
	 * @param categories int[]
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String register(User u, int[] categories) {
		String result;
		
		result = UserDCS.verifySignupData(u.getUsername(), u.getEmail());
		if(result == "false")
			return "signup_fail";
		else if(result == "fail")
			return "signup_db_fail";
		else {
			result = UserDAO.insert(u);
			if(result == "fail")
				return "signup_db_fail";
			else {
				result = UserDCS.insertCategoriesAssociation(u.getId(), categories);
				if(result == "fail") {
					/*Se viene inserito con successo l'utente ma non le categorie
					bisogna abortire l'intera procedura. Si elimina l'utente inserito
					con successo precedentemente, il che si traduce nell'eliminazione
					del record ma anche delle categorie associate e inserite fino a quel
					momento (prima dell'eccezione).*/
					UserDAO.delete(u);
					return "signup_db_fail";
				}	
				else
					return "success";
			}
		}	
	}	

	/**
	 * Questo metodo chiama vari metodi del livello inferiore per effettuare l'update del
	 * profilo utente. In particolare prima si verifica se i nuovi dati sono corretti e in
	 * quel caso si aggiornano l'utente e le associazioni con le categorie scelte.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param u User
	 * @param categories int[]
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String update(User u, int[] categories) {
		String result;
		result = UserDCS.verifyUpdateData(u.getId(), u.getUsername(), u.getEmail());
		if(result == "false")
			return "update_fail";
		else if(result == "fail")
			return "update_db_fail";
		else {
			result = UserDAO.update(u);
			if(result == "fail")
				return "update_db_fail";
			else {
				result = UserDCS.updateCategoriesAssociation(u.getId(), categories);
				if(result == "fail") {
					return "update_db_fail";
				}	
				else
					return "success";
			}
		}	
	}
	
	/**
	 * Questo metodo chiama semplicemente il corrispondente del livello inferiore in UserDCS.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param user String
	 * @param pwd String
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String verifyLoginData(String user, String pwd) {
		String result = UserDCS.verifyLoginData(user, pwd);
		if(result == "false")
			return "login_fail";
		else if(result == "fail")
			return "login_db_fail";
		else
			return "success";
	}
	
	/**
	 * Il metodo displayProfile(User u) chiama vari metodi del livello inferiore per visionare tutti i dati associati
	 * al profilo di un utente e restituirli alla classe UserAction. In particolare, prima seleziona dal DB i dati
	 * dell'utente, poi seleziona le categorie di interesse dell'utente ed infine seleziona tutte le 
	 * categorie esistenti e le restituisce già formattate per essere visualizzate dall'utente.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public String displayProfile(User u) {
		String result;
		result = UserDAO.retrieve(u);
		if(result == "fail") {
			return "display_fail";
		}
		else {
			ArrayList<String> userCategories;
			userCategories = UserDCS.retrieveCategoriesAssociation(u.getId());
			String categories;
			categories = CategoryDCS.displayCategories();
			return categories;
		}
	}
}
