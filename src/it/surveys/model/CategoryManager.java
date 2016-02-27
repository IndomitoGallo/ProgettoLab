package it.surveys.model;

import it.surveys.domain.Category;

/**
 * La classe CategoryManager è il cuore del model e gestisce tutte le azioni che riguardano le categorie,
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
	 * salvato in un attributo privato e statico, {@link #ctm}.
	 * @return ctm l'oggetto singleton CategoryManager 
	 */  
	public static CategoryManager getCategoryManager() {
		if(ctm == null)
			ctm = new CategoryManager();
		return ctm;
	}
	
	/**
	 * Il metodo insert(Category c) chiama vari metodi del livello inferiore per effettuare
	 * l'inserimento della categoria. In particolare prima controlla che non sia già presente
	 * e poi la inserisce.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param c Category
	 * @return String esito dell'inserimento
	 * @author Luca Talocci
	 */
	public String insert(Category c) {
		String result;
		result = "metodo da implementare";//CategoryDCS.verifyCategory(c.getName());
		if(result == "false")
			return "verification_fail";
		if(result == "fail")
			return "db_fail";
		result = CategoryDAO.insert(c);
		if(result == "fail") 
			return "db_fail";		
		return "success";
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
			return "fail";
		return "success";
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una stringa formattata in HTML con una lista delle
	 * categorie esistenti.
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String displayListCategories() {
		String result;
		result = "metodo da implementare";//CategoryDCS.displayListCategories();
		if(result == "fail")
			return "fail";
		return "success";
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una stringa formattata in HTML con dei radio
	 * button delle categorie esistenti.
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String displayRadioCategories() {
		String result;
		result = "metodo da implementare";//CategoryDCS.displayRadioCategories();
		if(result == "fail")
			return "fail";
		return "success";
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una stringa formattata in HTML con dei check box
	 * delle categorie esistenti.
	 * @return String
	 * @author Lorenzo Bernabei
	 */
	public String displayCheckBoxCategories() {
		String result;
		result = "metodo da implementare";//CategoryDCS.displayCheckBoxCategories();
		if(result == "fail")
			return "fail";
		return "success";
	}
}
