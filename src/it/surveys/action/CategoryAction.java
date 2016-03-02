package it.surveys.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;

import it.surveys.domain.Category;
import it.surveys.model.CategoryManager;

/**
 * Questa classe e' la "action" che gestisce tutte le attivita' che riguardano le categorie.
 * Essa e' un'estensione del Controller e fa da ponte tra le azioni client-side dell'utente
 * e le operazioni della logica applicativa.
 * Ogni suo metodo rappresenta una funzione dell'applicazione. 
 * @author L.Camerlengo 
 * @version 1.0,28/02/16
 */
public class CategoryAction extends ActionSupport{
	
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
	/** eventuale messaggio di errore da visualizzare nella view. */
	private String message;
	/** eventuale output da visualizzare nella view. */
	private String output;
	private HashMap<String, String> categories;
	private HashMap<String, String> defaultCategories;
	
	/** 
	 * viene utilizzato in displayCheckBoxCategories() per differenziare
	 * successo e fallimento, in base a chi ha richiesto la action. In pratica
	 * reindirizza l'utente alla registrazione o l'admin alla creazione del sondaggio.
	 */
    private String flag;
    
    /**
     * Il metodo createCategory() viene attivato dopo che il responsabile ha effettuato l'accesso
     * nella sua pagina personale ed ha effettuato un click su "Crea nuova categoria".
     * Vengono validati i dati del form e viene chiamato il metodo insert del CategoryManager.
     * @return String esito della creazione della categoria. 
     */
    public String createCategory(){
        if(validateCategory() == false){
            setMessage("Non sono stati inseriti correttamente tutti i campi obligatori.");
            return "fail";
        }
        CategoryManager cm = CategoryManager.getCategoryManager();
        Category c = new Category(name);
        String result = cm.insert(c);
        if(result.equals("verification_fail")){
            setMessage("Spiaciente, la categoria " + name + " e' gia esistente.");
            return "fail";
        } else if(result.equals("db_fail")){
            setMessage("Non e' stato posssibile creare la categoria.");
        }
        return "success";
    }
    
    /**
     * Il metodo deleteCategory() viene attivato dopo che il responsabile ha effettuato un click
     * su "Elimina categoria esistente" all'interno della sua pagina personale ed ha selezionato
     * una determinata categoria ed ha cliccato su "Cancella".
     * Viene chiamato il corrispondente metodo del CategoryManager.
     * @return String esito della cancellazione della categoria. 
     */
    public String deleteCategory(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        Category c = new Category();
        c.setId(id);
        String result = cm.delete(c);
        if(result.equals("fail")){
            setMessage("Non e' stato possibile cancellare la categoria.");
            return "fail";
        }
        return "success";
    }
    
    /**
     * Il metodo displayListCategories() viene attivato dopo che il responsabile ha effettuato l'accesso
     * nella sua pagina personale ed ha effettuato un click su "Crea nuova categoria". Viene chiamato il
     * corrispondente metodo del CategoryManager. Infine effettua i set degli attributi con i dati da mostrare
     * all'utente.
     * @return String esito del prelevamento delle categorie esistenti.
     */
    public String displayListCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        String result = cm.displayListCategories();
        if(result.equals("fail")){
            setMessage("Non e' stato possibile caricare le categorie esistenti.");
            return "fail";
        }
		setOutput(result);
        return "success";
    }
    
    /**
     * Il metodo displayRadioCategories() viene attivato dopo che il responsabile ha effettuato l'accesso nella 
     * sua pagina personale ed ha cliccato su "Elimina categoria esistente". Viene chiamato il corrispondente
     * metodo del CategoryManager. Infine effettua i set degli attributi con i dati da mostrare all'utente.
     * @return String esito del prelevamento delle categorie esistenti (vedi {@link #flag}). 
     */
    public String displayRadioCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        HashMap<String, String> result = cm.displayRadioCategories();
        if(result == null){
            setMessage("Non e' stato possibile caricare le categorie esistenti.");
            return "fail";     
        }
		setCategories(result);
		
		//Per default viene checkato il primo elemento dell'HashMap categories
		defaultCategories = new HashMap<>();
		Set<String> keySet = result.keySet();
		Iterator<String> it = keySet.iterator();
		String key = it.next();
		defaultCategories.put(key, result.get(key));
		
        return "success";
    }
    
    /**
     * Il metodo displayCheckBoxCategories() viene attivato dopo che il cliente ha effettuato l'accesso nella
     * pagina personale ed ha cliccato su "ModificaProfilo". Viene chiamato il corrispondente metodo del
     * CategoryManager. Infine effettua i set degli attributi con i dati da mostrare all'utente.
     * @return esito del prelevamento delle categorie. 
     */
    public String displayCheckBoxCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        HashMap<String, String> result = cm.displayCheckBoxCategories();
        if(result == null){
            setMessage("Non e' stato possibile caricare le categorie.");
            return getFlag() + "_fail";
        }
		setCategories(result);
		defaultCategories = new HashMap<>();
        return getFlag() + "_success";
    }
    
    /**
     * Il metodo validateCategory() effettua una validazione del campo required al momento della creazione
     * di una nuova categoria da parte del responsabile.
     * Restituisce false se il campo name contenente il nome della categoria da inserire e' vuoto.
     * Restituisce true altrimenti.
     * @return boolean esito della validazione. 
     */
    private boolean validateCategory(){
        if(name.isEmpty()){
            return false;
        }
        return true;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public HashMap<String, String> getCategories() {
		return categories;
	}

	public void setCategories(HashMap<String, String> categories) {
		this.categories = categories;
	}
    
	public HashMap<String, String> getDefaultCategories() {
		return defaultCategories;
	} 
	
}
