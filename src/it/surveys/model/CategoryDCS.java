package it.surveys.model;

import it.surveys.util.UtilDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * La funzionalita' della classe DCS (Domain Control Service) e' simile a quella della classe
 * DAO; a differenza di essa realizza delle feature addizionali che non riguardano un
 * particolare oggetto del dominio.
 * La classe DCS e' accessibile esclusivamente tramite i manager.
 * @author Davide Vitiello
 * @version 1.2,28/02/2016
 */

public class CategoryDCS {
	
	/**
	 * Il metodo verifyCategory(String name) verifica che la stringa in ingresso 
	 * corrisponda al nome di una categoria presente nel DataBase.	 
	 * Il valore di ritorno e': 'true' in caso di esito positivo,
	 * 'false' in caso di esito negativo,
     * 'fail' in caso di problemi di accesso al DataBase per la verifica
	 * @param name String nome della categoria
	 * @return String esito della verifica
	 */
    public static String verifyCategory(String name) {
        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT * FROM category WHERE name =" + name;
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (rs.next()) {
                return "false";
            } else {
                return "true";
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (stmt != null) {
                    utl.closeStatement(stmt);
                }
                if (conn != null) {
                    utl.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
    }
    
	/**
	 * Il metodo displayListCategories() restituisce la stringa formattata al tipo
	 * "unordered list <ul>" in HTML nella quale ogni elemento corrisponde ad una categoria.
	 * @return String la lista formattata in HTML delle categorie presenti nel DB
	 */
    public static String displayListCategories() {

        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String categories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT id,name FROM category ORDER BY id ASC";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "<p>Non sono presenti categorie.</p>";
            }
            
            categories = "<ul>";

            rs.beforeFirst();
            while (rs.next()) {
                categories = categories + "<li>";
                categories = categories + rs.getString(2);
                categories = categories + "</li>";
            }
            categories = categories + "</ul>";
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (stmt != null) {
                    utl.closeStatement(stmt);
                }
                if (conn != null) {
                    utl.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return categories;
    }
    
    /**
     * Il metodo displayRadioCategories() restituisce una formattazione di tutte
     * le categorie presenti nel DataBase.
     * La formattazione in questione consiste in una lista di elementi '<s:radio>' 
     * secondo la sintassi di Struts2 dove ogni elemento e' una coppia 
     * numero:nomedellacategoria
     * @return String tutte le categorie dei sondaggi formattate opportunamente
     */
    public static String displayRadioCategories() {
        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String radioCategories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT id,name FROM category ORDER BY id ASC";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "Categorie:<br>";
            }
            radioCategories = "<s:radio class=\"form-control\" id=\"categories\" name=\"id\" label=\"Categorie\" value=\"" + rs.getString(1) + "\" list=\"# {'" + rs.getString(1) + "':'" + rs.getString(2) + "'";

            while (rs.next()) 
                radioCategories = radioCategories + ",'"+rs.getString(1)+"':'"+rs.getString(2)+"'";
            
            radioCategories = radioCategories + "}\" />";
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (stmt != null) {
                    utl.closeStatement(stmt);
                }
                if (conn != null) {
                    utl.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return radioCategories;
    }
    
    /**
     * Il metodo displayCheckBoxCategories() restituisce una formattazione dei nomi
     * di tutte le categorie presenti nel DataBase
     * La stringa dei nomi viene formattata come una '<s:checkboxlist>' secondo la sintassi di Struts2,
     * dove ogni elemento e' una coppia idCategoria:nomeCategoria.
     * @return String tutte le categorie dei sondaggi formattate opportunamente
     */
    public static String displayCheckBoxCategories() {
        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String checkBoxCategories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT id,name FROM category ORDER BY id ASC";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "Categorie:<br>";
            }
            checkBoxCategories = "<s:checkboxlist class=\"form-control\" id=\"categories\" name=\"categories\" label=\"Categorie\" list=\"# {'"+rs.getString(1)+"':'"+rs.getString(2)+"'";

            while (rs.next()) {
                checkBoxCategories = checkBoxCategories + ",'"+rs.getString(1)+"':'"+rs.getString(2)+"'";
            }
            checkBoxCategories = checkBoxCategories + "}\" />";
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (stmt != null) {
                    utl.closeStatement(stmt);
                }
                if (conn != null) {
                    utl.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return checkBoxCategories;
    }
    
    /**
     * Il metodo displayCheckBoxCategories(ArrayList<Integer> userCategories) restituisce una formattazione dei nomi delle
     * categorie presenti nel DataBase formattate in una '<s:checkboxlist>' secondo la sintassi di Struts2,
     * dove ogni elemento e' una coppia idCategoria:nomeCcategoria. 
     * Si noti che le categorie passate in ingresso al metodo, ovvero quelle già associate all'utente,
     * nella checkboxlist saranno già checked.
     * @param userCategories ArrayList<Integer> Una collezione di numeri corrispondenti agli ID delle categorie nel DB
     * @return String i nomi di tutte le categorie dei sondaggi formattate opportunamente
     */
    public static String displayCheckBoxCategories(ArrayList<Integer> userCategories) {
        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String checkBoxCategories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);
            String sql = "SELECT id,name FROM category ORDER BY id ASC";

            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "Categorie:<br>";
            }
            
            //memorizzo in una stringa i valori di default della checkboxlist
            String defaultCategories = "value=\"{";
            int count = 1;
            for(Integer id : userCategories) {
            	if(count == userCategories.size())
            		defaultCategories = defaultCategories + "'" + id + "'";
            	else
            		defaultCategories = defaultCategories + "'" + id + "',";
            	count++;
            }
            defaultCategories = defaultCategories + "}\"";
            
            checkBoxCategories = "<s:checkboxlist class=\"form-control\" id=\"categories\" name=\"categories\" label=\"Categorie\" " + defaultCategories + " list=\"# {'"+rs.getString(1)+"':'"+rs.getString(2)+"'";

            while (rs.next()) 
                checkBoxCategories = checkBoxCategories + ",'"+rs.getString(1)+"':'"+rs.getString(2)+"'";
            
            checkBoxCategories = checkBoxCategories + "}\" />";
        } catch (ClassNotFoundException e) {
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
        } catch (SQLException e) {
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
        } finally {
            try {
                if (stmt != null) {
                    utl.closeStatement(stmt);
                }
                if (conn != null) {
                    utl.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return checkBoxCategories;
    }
}
