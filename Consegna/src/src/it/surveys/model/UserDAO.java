package it.surveys.model;

import java.io.IOException;
import java.sql.*;

import it.surveys.domain.User;
import it.surveys.util.UtilDB;

/**
 * La classe UserDAO (Data Access Object) contiene i metodi che eseguono le operazioni 
 * CRUD riguardanti la corrispondente classe di dominio User.
 * @author Luca Talocci, Lorenzo Bernabei
 * @version 1.0 08/02/2016
 */
public class UserDAO {
	
	/**
	 * Il metodo insert(User u) sfrutta i metodi forniti dalla classe UtilDB
	 * per inserire i dati di un utente nel Database.
	 * Inoltre, viene prelevato l'id e settato l'oggetto User passato come parametro,
	 * per un uso successivo dell'identificatore nello {@link UserManager}.
	 * @param u User
	 * @return String esito dell'inserimento
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
			sql = "SELECT id FROM user WHERE username='" + u.getUsername() + "'";
			ResultSet rs = utl.query(stmt, sql);
			rs.next();
			u.setId(rs.getInt(1)); //set del campo id dell'utente per poi usarlo nell'inserimento delle categorie scelte
	    } catch (SQLException e) {	//il metodo intercetta un'eccezione proveniente dal DB	    	 
	    	System.err.println("Database Error!");
	    	e.printStackTrace();
	    	return "fail";
	    } catch (ClassNotFoundException e) { //il metodo intercetta un'eccezione proveniente dal driver del DB	    	 
		    System.err.println("Driver Not Found!");
		    e.printStackTrace();
		    return "fail";
	    } catch(IOException e){
             System.err.println("Reading Configuration File Error!");
             e.printStackTrace();
             return "fail";
        } finally {
             try {
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
	 * Viene, quindi settato l'oggetto User passato come parametro.
	 * @param u User
	 * @return String esito della SELECT SQL
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
	    } catch(IOException e){
             System.err.println("Reading Configuration File Error!");
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
	 * @return String esito dell'aggiornamento
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
	    } catch(IOException e){
             System.err.println("Reading Configuration File Error!");
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
	 * @return String esito della cancellazione
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
	    } catch(IOException e){
             System.err.println("Reading Configuration File Error!");
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
