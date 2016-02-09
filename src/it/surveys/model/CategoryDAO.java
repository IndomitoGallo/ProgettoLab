package it.surveys.model;

import java.sql.*;

import it.surveys.domain.Category;
import it.surveys.util.UtilDB;

/**
 * Questa classe contiene i metodi che eseguono le operazioni CRUD riguardanti la corrispondente
 * classe di dominio Category e implementa il pattern DAO (Data Access Object).
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 08/02/2016
 */
public class CategoryDAO {
	
	/**
	 * Questo metodo sfrutta i servizi forniti dalla classe UtilDB per inserire una
	 * categoria nel Database.
	 * @param c Category
	 * @return String
	 * @throws Exception
	 * @author Lorenzo Bernabei
	 */
	public static String insert(Category c) throws Exception {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;	
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL insert
			String sql = "INSERT INTO category VALUES(NULL, '"  + c.getName() + "')";
			utl.manipulate(stmt, sql);	//esecuzione del comando SQL
	     } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    return "success";
	}
	
	/**
	 * Questo metodo sfrutta i servizi forniti dalla classe UtilDB per leggere i dati di una
	 * specifica categoria dal Database.
	 * @param c Category
	 * @return String
	 * @throws Exception
	 * @author Lorenzo Bernabei
	 */
	public static String retrieve(Category c) throws Exception {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql = "SELECT * FROM user WHERE id=" + c.getId();
			//memorizzazione del risultato della query in un ResultSet
			ResultSet rs = utl.query(stmt, sql);
			//setto i campi dell'oggetto del dominio con i dati letti dal database
            if(rs.next()){
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
            }  
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    return "success";		
	}	
	
	/**
	 * Questo metodo sfrutta i servizi forniti dalla classe UtilDB per modificare i dati di
	 * una specifica categoria nel Database.
	 * @param u User
	 * @return String
	 * @throws Exception
	 * @author Lorenzo Bernabei
	 */
	public static String update(Category c) throws Exception {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL update
			String sql = "UPDATE user SET name='" + c.getName() + 
					  					  "' WHERE id=" + c.getId();
			utl.manipulate(stmt, sql);	//esecuzione del comando SQL
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    return "success";
	}
	
	/**
	 * Questo metodo sfrutta i servizi forniti dalla classe UtilDB per eliminare una
	 * specifica categoria dal Database.
	 * @param c Category
	 * @return String
	 * @throws Exception
	 * @author Lorenzo Bernabei
	 */
	public static String delete(Category c) throws Exception {	    
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL delete
			String sql = "DELETE FROM user WHERE id=" + c.getId();
			//esecuzione del comando SQL
			utl.manipulate(stmt, sql);
	     } catch (SQLException e) { //il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	     } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	     } finally {
	    	if(stmt != null) //chiusura dello statement
	    		utl.closeStatement(stmt);
	    	if(conn != null) //chiusura della connessione
	    		utl.closeConnection(conn);
		}		
	    return "success";    
	}
}
