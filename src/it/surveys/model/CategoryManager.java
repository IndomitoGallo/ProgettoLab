package it.surveys.model;

import it.surveys.domain.Category;

/**
 * Questa classe è il cuore del model e gestisce tutte le azioni che riguardano l'utente,
 * difatti offre i suoi servizi alla CategoryAction.
 * Il manager, poi, utilizza i servizi del secondo livello del model: le classi DAO e DCS.
 * Saranno queste ultime a dialogare con il Database.
 * Infine il manager è stato implementato come una factory con pattern singleton.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 11/02/15
 */

public class CategoryManager {
	
	private static CategoryManager ctm;
	
	/**
	 * Il metodo è l'implementazione del pattern singleton per avere una sola
	 * istanza della classe. Quando viene chiamato per la prima volta istanzia
	 * e ritorna un nuovo oggetto di tipo CategoryManager, mentre le volte successive,
	 * ritorna l'oggetto che è stato istanziato la prima volta e che è stato
	 * salvato in un attrivuto privato e statico.
	 * @return ctm oggetto singleton CategoryManager 
	 */  
	public static CategoryManager getCategoryManager() {
		if(ctm == null)
			ctm = new CategoryManager();
		return ctm;
	}
	
	/**
	 * Il metodo insert(Category c) gestisce l'inserimento di un oggetto di tipo
	 * Category nel database usufruendo dei servizi offerti dalla classe CategoryDAO.
	 * @param c Category
	 * @return String esito dell'inserimento
	 * @author Luca Talocci
	 */
	public String insert(Category c) {
		String result;
		result = CategoryDAO.insert(c);
		if(result == "fail") 
			return "insert_fail";
		else return "success";
	}
	
	/**
	 * Il metodo delete(Category c) gestisce la cancellazione di un oggetto di tipo
	 * Category dal database usufruendo dei servizi offerti dalla classe CategoryDAO.
	 * @param c Category
	 * @return String esito della cancellazione
	 * @author Luca Talocci
	 */
	public String delete(Category c) {
		String result;
		result = CategoryDAO.delete(c);
		if(result == "fail")
			return "delete_fail";
		else return "success";
	}
	
	/*
	public String displayCategories() {
		
	}
	*/
}
