package it.surveys.model;

import java.sql.*;

import it.surveys.domain.User;
import it.surveys.util.UtilDB;

/**
 * La classe UserDAO contiene i metodi di interrogazione e manipolazione della corrispondente
 * classe di dominio User e quindi, si fa carico di gestire le operazioni con il database.
 * In pratica contiene le funzionalit� di base (CRUD).
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.1 08/02/2016
 */

public class UserDAO {
	
	/**
	 * Il metodo insert(User u) sfrutta i metodi forniti dalla classe UtilDB
	 * per inserire i dati di un utente nel Database.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public static String insert(User u) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;	
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL insert
			String sql = "INSERT INTO user VALUES(NULL, '"  + u.getUsername() + "', '" +
				 	 							    		  u.getName() + "', '" + 
				 	 							    		  u.getSurname() + "', '" +
				 	 							    		  u.getPassword() + "', '" + 
				 	 							    		  u.getEmail() + "')";
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
	 * Il metodo retrieve(User u) sfrutta i metodi forniti dalla classe UtilDB
	 * per leggere i dati di un utente specifico memorizzato nel Database.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public static String retrieve(User u) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL select
			String sql = "SELECT * FROM user WHERE id=" + u.getId();
			//memorizzazione del risultato della query in un ResultSet
			ResultSet rs = utl.query(stmt, sql);
            if(rs.next()){
                u.setId(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setName(rs.getString(3));
                u.setSurname(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setEmail(rs.getString(6));
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
	 * Il metodo update(User u) sfrutta i metodi forniti dalla classe UtilDB
	 * per modificare i dati di un utente specifico nel Database.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public static String update(User u) {
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL update
			String sql = "UPDATE user SET username='" + u.getUsername() + 
										  "', name='" + u.getName() + 
										  "', surname='" + u.getSurname() +
				 	 					  "', password='" + u.getPassword() +
				 	 					  "', email='" + u.getEmail() + 
				 	 					  "' WHERE id=" + u.getId();
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
	 * Il metodo delete(User u) sfrutta i metodi forniti dalla classe UtilDB
	 * per eliminare i dati di un utente specifico nel Database.
	 * @param u User
	 * @return String
	 * @author Luca Talocci
	 */
	public static String delete(User u) {	    
		UtilDB utl = null;	
		Connection conn = null;	
		Statement stmt = null;
		try {
			utl = UtilDB.getUtilDB();	//istanza della classe factory UtilDB
			conn= utl.createConnection();	//connection to DB
			stmt=conn.createStatement();	//creazione dello Statement
			//SQL delete
			String sql = "DELETE FROM user WHERE id=" + u.getId();
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
