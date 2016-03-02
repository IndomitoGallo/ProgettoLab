package it.surveys.model;

import java.sql.*;
import java.util.HashMap;

import it.surveys.util.UtilDB;

/**
 * La classe UserDCS (Domain Control Service) realizza delle feature addizionali che non riguardano un
 * particolare oggetto del dominio User. L'uso di DCS è una variante all'approccio DAO "puro".
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 10/02/2016
 */
public class UserDCS {
	
	/**
	 * Il metodo verifyLoginData(String user, String pwd) verifica che i dati di Login inseriti 
	 * dall'utente corrispondano ai dati immessi in fase di registrazione dall'utente che 
	 * deve effettuare l'accesso.
	 * Il valore di ritorno è l'id dell'utente se i dati di Login sono corretti, 0 altrimenti.
	 * In caso di eccezioni viene restituito -1.
	 * @param user String lo username dell'utente
	 * @param pwd String la password dell'utente
	 * @return int esito della verifica
	 * @author Luca Talocci
	 */
	public static int verifyLoginData(String user, String pwd) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql = "SELECT id FROM user WHERE username='" + user +
											  "' AND password='" + pwd + "'";
			//memorizzazione del risultato della query in un ResultSet
			ResultSet rs = utl.query(stmt, sql);
			if(rs.next())
				return rs.getInt(1); //username e password passati in ingresso al metodo corrispondono ad un utente nel database
			else
				return 0; //username e password passati in ingresso al metodo non corrispondono ad alcun utente nel database
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return -1;
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return -1;
		 } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return -1;
            }
		}
	}
	
	/**
	 * Il metodo verifySignupData(String user, String mail) verifica che i dati di registrazione inseriti 
	 * dall'utente, in particolare username e email, non siano già presenti nel database.
	 * Il valore di ritorno è true se i dati di registrazione sono corretti, false altrimenti.
	 * In caso di eccezioni viene restituito fail.
	 * @param user String lo username dell'utente
	 * @param mail String l'e-mail dell'utente
	 * @return String esito della verifica
	 * @author Lorenzo Bernabei
	 */
	public static String verifySignupData(String user, String mail) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql1 = "SELECT * FROM user WHERE username='" + user + "'";
			String sql2 = "SELECT * FROM user WHERE email='" + mail + "'";
			//memorizzazione del risultato delle query in un ResultSet
			ResultSet rs1 = utl.query(stmt, sql1);
			if(rs1.next())
				return "false";	//username passato in ingresso al metodo già presente nel database
			ResultSet rs2 = utl.query(stmt, sql2);
			if(rs2.next())
				return "false";	//mail passata in ingresso al metodo già presente nel database
			return "true"; //username e mail passati in ingresso al metodo non sono presenti nel database
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
		 } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            }
		}
	}
	
	/**
	 * Il metodo verifyUpdateData(int idUser, String user, String mail) verifica che i dati di aggiornamento del
	 * profilo inseriti dall'utente, in particolare username e email, non siano già presenti nel database.
	 * Il valore di ritorno è true se i dati inseriti sono corretti, false altrimenti.
	 * In caso di eccezioni viene restituito fail.
	 * @param idUser int l'id dell'utente, per poter effettuare la verifica su tutti gli altri utenti
	 * @param user String lo username dell'utente
	 * @param mail String l'e-mail dell'utente
	 * @return String esito della verifica
	 * @author Luca Talocci
	 */
	public static String verifyUpdateData(int idUser, String user, String mail) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql1 = "SELECT * FROM user WHERE username='" + user + "' AND id<>" + idUser;
			String sql2 = "SELECT * FROM user WHERE email='" + mail + "' AND id<>" + idUser;
			//memorizzazione del risultato delle query in un ResultSet
			ResultSet rs1 = utl.query(stmt, sql1);
			if(rs1.next())
				return "false";	//username passato in ingresso al metodo già presente nel database
			ResultSet rs2 = utl.query(stmt, sql2);
			if(rs2.next())
				return "false";	//mail passata in ingresso al metodo già presente nel database
			return "true"; //username e mail passati in ingresso al metodo non sono presenti nel database
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
		 } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            }
		}
	}
	
	/**
	 * Il metodo insertCategoriesAssociation(int idUser, int[] categories) sfrutta i servizi
	 * forniti dalla classe UtilDB per inserire nel Database le associazioni di un utente con
	 * le categorie.
	 * @param idUser int
	 * @param categories int[] array degli id delle categorie
	 * @return String esito degli inserimenti
	 * @author Luca Talocci
	 */
	public static String insertCategoriesAssociation(int idUser, int[] categories) {
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
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            }
		}		
	    return "success";
	}

	/**
	 * Il metodo updateCategoriesAssociation(int idUser, int[] categories) sfrutta i servizi
	 * forniti dalla classe UtilDB per aggiornare nel Database le associazioni di un utente con
	 * le categorie. In particolare cancella tutte quelle presenti e inserisce le nuove scelte.
	 * @param idUser int
	 * @param categories int[] array degli id delle categorie
	 * @return String esito degli aggiornamenti
	 * @author Lorenzo Bernabei
	 */
	public static String updateCategoriesAssociation(int idUser, int[] categories) {
		UtilDB utl = null;
		Connection conn = null;	
		Statement stmt = null;	
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			String sql = "DELETE FROM categoriesUser WHERE id=" + idUser;
			utl.manipulate(stmt, sql); //eliminazione delle categorie presenti in precedenza
			for(int idCat : categories){	//per ognuna delle nuove categorie scelte dall'utente
				//SQL insert per inserire le nuove categorie scelte
				sql = "INSERT INTO categoriesUser VALUES(" + idUser + ", " + idCat + ")";
				utl.manipulate(stmt, sql);	//esecuzione del comando SQL
			}
	     } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return "fail";
            }
		}		
	    return "success";
	}
	
	/**
	 * Il metodo retrieveCategoriesAssociation(int idUser) sfrutta i servizi
	 * forniti dalla classe UtilDB per leggere dal Database le associazioni di un utente con
	 * le categorie.
	 * @param idUser int
	 * @return ArrayList<Integer> collezione contenente gli id delle categorie
	 * @author Lorenzo Bernabei
	 */
	public static HashMap<String, String> retrieveCategoriesAssociation(int idUser) {
		UtilDB utl = null;
		Connection conn = null;	
		Statement stmt = null;
		HashMap<String, String> categories = new HashMap<>();
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			String sql = "SELECT c1.idCategory, c2.name FROM categoriesUser c1, category c2 WHERE c1.idCategory=c2.id AND c1.idUser=" + idUser;
			ResultSet rs = utl.query(stmt, sql); //selezione di tutte le categorie associate all'utente
			while(rs.next()){
				categories.put(rs.getString(1), rs.getString(2)); 
			}
	     } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return null;
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return null;
	     } finally {
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
                return null;
            }
		}		
	    return categories;
	}
	
}
