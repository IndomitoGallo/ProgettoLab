package it.surveys.model;

import java.sql.*;
import it.surveys.util.UtilDB;

/**
 * La classe UserDCS (Domain Control Service) realizza delle feature addizionali che non riguardano un
 * particolare oggetto del dominio User. L'uso di DCS è una variante all'approccio DAO "puro".
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.1 08/02/2016
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
	public static Boolean verifyLoginData(String user, String pwd) throws Exception{
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql = "SELECT * FROM user WHERE username=" + user +
											  "AND password=" + pwd;
			//memorizzazione del risultato della query in un ResultSet
			ResultSet rs = utl.query(stmt, sql);
			if(rs.next())
				return true; //username e password passati in ingresso al metodo corrispondono ad un utente nel database
			else
				return false; //username e password passati in ingresso al metodo non corrispondono ad alcun utente nel database
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
	}
	
	/**
	 * Il metodo insertCategoriesAssociation(int idUser, int[] categories) sfrutta i servizi
	 * forniti dalla classe UtilDB per inserire nel Database le associazioni tra gli utenti
	 * e le categorie.
	 * @param idUser int
	 * @param categories int[]
	 * @return String
	 * @throws Exception
	 * @author Luca Talocci
	 */
	public static String insertCategoriesAssociation(int idUser, int[] categories) throws Exception {
		UtilDB utl = null;
		Connection conn = null;	
		Statement stmt = null;	
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			for(int idCat : categories){	//per ognuna delle categorie scelte dall'utente
				//SQL insert
				String sql = "INSERT INTO categoriesUser VALUES(" + idUser + ", " + idCat + ")";
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
