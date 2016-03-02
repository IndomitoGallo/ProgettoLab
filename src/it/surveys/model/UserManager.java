package it.surveys.model;

import java.util.HashMap;

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
	 * Il metodo register(User u, int[] categories) chiama vari metodi del livello inferiore
	 * per effettuare la registrazione dell'utente.
	 * In particolare prima si verifica se i dati inseriti sono corretti e in
	 * quel caso si inseriscono l'utente e le associazioni con le categorie scelte.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param u User
	 * @param categories int[] array degli id delle categorie
	 * @return String esito della registrazione
	 * @author Lorenzo Bernabei
	 */
	public String register(User u, int[] categories) {
		String result;
		result = UserDCS.verifySignupData(u.getUsername(), u.getEmail());
		if(result.equals("false"))
			return "verification_fail";
		if(result.equals("fail"))
			return "db_fail";
		result = UserDAO.insert(u);
		if(result.equals("fail"))
			return "db_fail";
		result = UserDCS.insertCategoriesAssociation(u.getId(), categories);
		if(result.equals("fail")) {
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
	 * un check box precompilato delle categorie. Se viene restituito fail, si ritorna un fallimento.
	 * @param u User
	 * @return String stringa formattata opportunamente
	 * @author Luca Talocci
	 */
	public HashMap<String, String> displayProfile(User u, HashMap<String, String> defaultCategories) {
		HashMap<String, String> categories;
		String result = UserDAO.retrieve(u);
		if(result.equals("fail"))
			return null;
		defaultCategories = UserDCS.retrieveCategoriesAssociation(u.getId());
		if(defaultCategories == null)
			return null;
		categories = CategoryDCS.displayCheckBoxCategories();
		if(categories.equals("fail"))
			return null;
		return categories;
	}

	/**
	 * Questo metodo chiama vari metodi del livello inferiore per effettuare l'update del
	 * profilo utente. In particolare prima si verifica se i nuovi dati sono corretti e in
	 * quel caso si aggiornano l'utente e le associazioni con le categorie scelte.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param u User
	 * @param categories int[] array degli id delle categorie
	 * @return String esito dell'aggiornamento del profilo
	 * @author Lorenzo Bernabei
	 */
	public String update(User u, int[] categories) {
		String result;
		result = UserDCS.verifyUpdateData(u.getId(), u.getUsername(), u.getEmail());
		if(result.equals("false"))
			return "verification_fail";
		if(result.equals("fail"))
			return "db_fail";
		result = UserDAO.update(u);
		if(result.equals("fail"))
			return "db_fail";
		result = UserDCS.updateCategoriesAssociation(u.getId(), categories);
		if(result.equals("fail"))
			return "db_fail";
		return "success";
	}
	
	/**
	 * Questo metodo chiama semplicemente il corrispondente del livello inferiore in UserDCS.
	 * Se viene restituito 0 o -1 corrisponde ad un fallimento, altrimenti viene restituito l'id dell'utente.
	 * @param user String lo username dell'utente
	 * @param pwd String la password dell'utente
	 * @return int valore numerico che rappresenta l'esito della verifica
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
