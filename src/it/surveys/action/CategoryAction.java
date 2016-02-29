package it.surveys.action;

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
    private String message;
    private String output;
    private String flag;
    
    /**
     * Il metodo createCategory() viene attivato dopo che il responsabile ha effettuato l'accesso
     * nella sua pagina personale ed ha effettuato un click su "Crea nuova categoria".
     * @return String esito della creazione della categoria. 
     */
    public String createCategory(){
        if(validateCategory() == false){
            setMessage("Non sono stati inseriti correttamente tutti i campi obligatori.");
            return "fail";
        }
        CategoryManager cm = CategoryManager.getCategoryManager();
        Category c = new Category();
        c.setName(name);
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
     * nella sua pagina personale ed ha effettuato un click su "Crea nuova categoria".
     * @return String esito del prelevamento delle categorie esistenti.
     */
    public String displayListCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        String result = cm.displayListCategories();
        if(result.equals("fail")){
            setMessage("Non e' stato possibile caricare le categorie esistenti.");
            return "fail";
        }
        return "success";
    }
    
    /**
     * Il metodo displayRadioCategories() viene attivato dopo il responsabile ha effettuato l'accesso nella 
     * sua pagina personale ed ha effettuato un click su "Elimina categoria esistente".
     * @return String esito del prelevamento delle categorie esistenti. 
     */
    public String displayRadioCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        String result = cm.displayRadioCategories();
        if(result.equals("fail")){
            setMessage("Non e' stato possibile caricare le categorie esistenti.");
            return "fail";     
        }
        return "success";
    }
    
    /**
     * Il metodo displayCheckBoxCategories() viene attivato dopo che il cliente ha effettuato l'accesso nella
     * pagina personale ed ha effettuato click su "ModificaProfilo". 
     * @return esito del prelevamento delle categorie. 
     */
    public String displayCheckBoxCategories(){
        CategoryManager cm = CategoryManager.getCategoryManager();
        String result = cm.displayCheckBoxCategories();
        if(result.equals("fail")){
            setMessage("Non e' stato possibile caricare le categorie.");
            return flag + "_fail";
        }
        return flag + "_success";
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
    
    
}
