package it.surveys.model;

import java.util.HashMap;

import it.surveys.domain.Category;

/**
 * La classe CategoryManager � il cuore del model e gestisce tutte le azioni che riguardano le categorie,
 * difatti offre i suoi servizi alla CategoryAction.
 * Il manager, poi, utilizza i servizi del secondo livello del model: le classi DAO e DCS.
 * Saranno queste ultime a dialogare con il Database.
 * Infine il manager � stato implementato come una factory con pattern singleton.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 11/02/15
 */
public class CategoryManager {
	
	private static CategoryManager ctm;
	
	/**
	 * Il metodo � l'implementazione del pattern singleton per avere una sola
	 * istanza della classe. Quando viene chiamato per la prima volta istanzia
	 * e ritorna un nuovo oggetto di tipo CategoryManager, mentre le volte successive,
	 * ritorna l'oggetto che � stato istanziato la prima volta e che � stato
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
	 * l'inserimento della categoria. In particolare prima controlla che non sia gi� presente
	 * e poi la inserisce.
	 * Se viene restituito false o fail, si ritorna un fallimento, altrimenti un successo.
	 * @param c Category
	 * @return String esito dell'inserimento
	 * @author Luca Talocci
	 */
	public String insert(Category c) {
		String result;
		result = CategoryDCS.verifyCategory(c.getName());
		if(result.equals("false"))
			return "verification_fail";
		if(result.equals("fail"))
			return "db_fail";
		result = CategoryDAO.insert(c);
		if(result.equals("fail"))
			return "db_fail";		
		return "success";
	}
	
	/**
	 * Il metodo delete(Category c) gestisce la cancellazione di un oggetto di tipo
	 * Category dal database usufruendo dei servizi offerti dalla classe CategoryDAO.
	 * Se viene restituito fail, si ritorna un fallimento, altrimenti un successo.
	 * @param c Category
	 * @return String esito della cancellazione
	 * @author Luca Talocci
	 */
	public String delete(Category c) {
		String result;
		result = CategoryDAO.delete(c);
		if(result.equals("fail"))
			return "fail";
		return "success";
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una stringa formattata in HTML con una lista delle
	 * categorie esistenti. Se viene restituito fail, si ritorna un fallimento.
	 * @return String stringa formattata opportunamente
	 * @author Lorenzo Bernabei
	 */
	public String displayListCategories() {
		String result;
		result = CategoryDCS.displayListCategories();
		if(result.equals("fail"))
			return "fail";
		return result;
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una HashMap<String, String> che costituir� il contenuto 
	 * dell'attributo "list" del tag <s:radio> secondo la sintassi di Struts2.
	 * Se non � stato possibile creare la HashMap il metodo restituisce null.
	 * @return HashMap<String, String> collezione di coppie (key, value)=(idCategory, nameCategory)
	 * @author Lorenzo Bernabei
	 */
	public HashMap<String, String> displayRadioCategories() {
		HashMap<String, String> result;
		result = CategoryDCS.displayRadioCategories();
		if(result == null)
			return null;
		return result;
	}
	
	/**
	 * Questo metodo usufruisce dei servizi offerti dalla classe CategoryDCS, chiamando il
	 * corrispondente metodo e restituendo una HashMap<String, String> che costituir� il contenuto 
	 * dell'attributo "list" del tag <s:checkboxlist> secondo la sintassi di Struts2.
	 * Se non � stato possibile creare la HashMap il metodo restituisce null.
	 * @return HashMap<String, String> collezione di coppie (key, value)=(idCategory, nameCategory)
	 * @author Lorenzo Bernabei
	 */
	public HashMap<String, String> displayCheckBoxCategories() {
		HashMap<String, String> result;
		result = CategoryDCS.displayCheckBoxCategories();
		if(result == null)
			return null;
		return result;
	}
}
