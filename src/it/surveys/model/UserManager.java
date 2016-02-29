package it.surveys.model;

import java.util.ArrayList;

import it.surveys.domain.User;

/**
 * La classe UserManager è il cuore del model e gestisce tutte le azioni che riguardano l'utente,
 * difatti offre i suoi servizi alla UserAction.
 * Il manager, poi, utilizza i servizi del secondo livello del model: le classi DAO e DCS.
 * Saranno queste ultime a dialogare con il Database.
 * Il manager è stato implementato come una factory con pattern singleton.
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
	 * salvato nell'attributo, privato e statico, {@link #usm}.
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
			return "verification_fail";
		if(result == "fail")
			return "db_fail";
		result = UserDAO.insert(u);
		if(result == "fail")
			return "db_fail";
		result = UserDCS.insertCategoriesAssociation(u.getId(), categories);
		if(result == "fail") {
			/*Se viene inserito con successo l'utente ma non le categorie
			bisogna abortire l'intera procedura. Si elimina l'utente inserito
			con successo precedentemente, il che si traduce nell'eliminazione
			del record ma anche delle categorie associate e inserite fino a quel
			momento (prima dell'eccezione).*/
			UserDAO.delete(u);
			return "db_fail";
		}	
		return "success";	
	}
	
	/**
	 * Il metodo displayProfile(User u) chiama vari metodi del livello inferiore per selezionare
	 * tutti i dati associati al profilo di un utente e restituirli alla classe UserAction per
	 * essere visualizzati.
	 * In particolare, prima seleziona dal DB i dati dell'utente (settando lo stato dell'oggetto
	 * User), poi seleziona le categorie di interesse scelte dall'utente e le utilizza per
	 * selezionare tutte le categorie esistenti e restituire una stringa formattata in HTML con
	 * dei check box precompilati delle categorie.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public String displayProfile(User u) {
		String result;
		result = UserDAO.retrieve(u);
		if(result == "fail")
			return "fail";
		ArrayList<Integer> userCategories;
		userCategories = UserDCS.retrieveCategoriesAssociation(u.getId());
		if(userCategories == null)
			return "fail";
		String categories;
		categories = CategoryDCS.displayCheckBoxCategories(userCategories);
		if(categories == "fail")
			return "fail";
		return categories;
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
			return "verification_fail";
		if(result == "fail")
			return "db_fail";
		result = UserDAO.update(u);
		if(result == "fail")
			return "db_fail";
		result = UserDCS.updateCategoriesAssociation(u.getId(), categories);
		if(result == "fail")
			return "db_fail";
		return "success";
	}
	
	/**
	 * Questo metodo chiama semplicemente il corrispondente del livello inferiore in UserDCS.
	 * Se viene restituito 0 o -1 corrisponde ad un fallimento, altrimenti viene restituito l'id dell'utente.
	 * @param user String
	 * @param pwd String
	 * @return int
	 * @author Lorenzo Bernabei
	 */
	public int verifyLoginData(String user, String pwd) {
		int result = UserDCS.verifyLoginData(user, pwd);
		if(result == 0)
			return 0;
		if(result == -1)
			return -1;
		return result;
	}
	
}
