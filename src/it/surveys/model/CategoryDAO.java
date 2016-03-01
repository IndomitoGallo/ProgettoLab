package it.surveys.model;

import java.sql.*;

import it.surveys.domain.Category;
import it.surveys.util.UtilDB;

/**
 * La classe CategoryDAO (Data Access Object) contiene i metodi che eseguono le operazioni 
 * CRUD riguardanti la corrispondente classe di dominio Category.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 08/02/2016
 */
public class CategoryDAO {
	
	/**
	 * Il metodo sfrutta i servizi forniti dalla classe UtilDB per inserire una
	 * categoria nel Database.
	 * @param c Category
	 * @return String esito dell'inserimento
	 * @author Lorenzo Bernabei
	 */
	public static String insert(Category c) {
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
	 * Il metodo sfrutta i servizi forniti dalla classe UtilDB per leggere i dati di una
	 * specifica categoria dal Database.
	 * Viene, quindi settato l'oggetto Category passato come parametro.
	 * @param c Category
	 * @return String esito della SELECT SQL
	 * @author Lorenzo Bernabei
	 */
	public static String retrieve(Category c) {
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
	 * Il metodo sfrutta i servizi forniti dalla classe UtilDB per modificare i dati di
	 * una specifica categoria nel Database.
	 * @param u User
	 * @return String esito dell'aggiornamento
	 * @author Lorenzo Bernabei
	 */
	public static String update(Category c) {
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
	 * Il metodo sfrutta i servizi forniti dalla classe UtilDB per eliminare una
	 * specifica categoria dal Database.
	 * @param c Category
	 * @return String esito della cancellazione
	 * @author Lorenzo Bernabei
	 */
	public static String delete(Category c) {	    
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
			if(utl.manipulate(stmt, sql) == 0){ 
				//se la query non ha interessato alcun record del DB, viene restituita una stringa di errore
				return "fail";
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
}
