package it.surveys.model;

import java.sql.*;
import java.util.ArrayList;
import it.surveys.util.UtilDB;

/**
 * La classe UserDCS (Domain Control Service) realizza delle feature addizionali che non riguardano un
 * particolare oggetto del dominio.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 06/02/2016
 */

public class UserDCS {
	
	/**
	 * Il metodo verifyLoginData(String user, String pwd) verifica che i dati di Login inseriti 
	 * dall'utente corrispondano ai dati immessi in fase di registrazione dall'utente che 
	 * deve effettuare l'accesso.
	 * Il valore di ritorno è un tipo booleano: true se i dati di Login sono corretti, false altrimenti.
	 * @param user String
	 * @param pwd String
	 * @return Boolean
	 * @throws Exception
	 * @author Luca Talocci
	 */
	public Boolean verifyLoginData(String user, String pwd) throws Exception{
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		ArrayList<String> result = new ArrayList<String>();
		//String result = "";
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql = "SELECT password FROM user WHERE username=" + user;
			//memorizzazione del risultato della query in un ResultSet
			ResultSet rs = utl.query(stmt, sql);
			//conversione del ResultSet in una stringa
			result = utl.resultSetToArrayString(rs);
			//result = utl.resultSetToString(rs);
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return false;
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return false;
		 } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    if(result.contains(pwd)) //la password passata in ingresso al metodo è uguale a quella registrata nel database
	    	return true;
	    else return false; //la password passata in ingresso al metodo è diversa da quella registrata nel database
	}
	
	/**
	 * Il metodo insertCategoriesAssociation(int idUser, String[] categories) sfrutta i metodi forniti dalla classe UtilDB
	 * per inserire nel Database le associazioni tra gli utenti e le categorie.
	 * @param idUser int
	 * @param categories String[]
	 * @return String
	 * @throws Exception
	 * @author Luca Talocci
	 */
	public String insertCategoriesAssociation(int idUser, ArrayList<String> categories) throws Exception {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;	
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			for(String s : categories){	//per ognuna delle categorie scelte dall'utente
				//SQL insert
				String sql = "INSERT INTO categoriesUser VALUES(" + idUser + ", " + Integer.parseInt(s) + ")";
				utl.manipulate(stmt, sql);	//esecuzione del comando SQL
			}
	     } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "ins_failure";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "ins_failure";
	     } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    return "ins_success";
	}
	
}
