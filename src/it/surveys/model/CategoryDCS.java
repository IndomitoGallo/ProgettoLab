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
 * @version 1.1,28/02/2016
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
                return "true";
            } else {
                return "false";
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

    public static String displayListCategories() {

        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String categories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT name FROM category";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "<p>Non sono presenti categorie.</p>";
            }
            categories = "<ul>";

            rs.beforeFirst();
            while (rs.next()) {
                categories = categories + "<li>";
                categories = categories + rs.getString(1);
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
     * le categorie presenti nel DataBase
     * La formattazione in questione consiste in una lista di elementi '<s:radio>' 
     * secondo la sintassi di Struts2
     * dove ogni elemento e' una coppia numero:nomedellacategoria
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

            String sql = "SELECT name FROM category";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "<p>Non sono presenti categorie.</p>";
            }
            int i = 1;
            radioCategories = "<s:radio class=\"form-control\" id=\"categories\" name=\"id\" label=\"Categorie\" list=\"# {'"+i+"':'"+rs.getString(1)+"'";
        
            //String categories;   A CHE SERVE?
            while (rs.next()) {
                i++;
                //categories = rs.getString(1); A CHE SERVE?
                radioCategories = radioCategories + ",'"+i+"':'"+rs.getString(1)+"'";
            }
            radioCategories = radioCategories + "'}\" value=\"1\"/>";
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
     * La formattazione in questione consiste in una lista di elementi checkbox,
     * ovvero '<s:checkboxlist>' secondo la sintassi di Struts2,
     * dove ogni elemento e' una coppia numero:nomedellacategoria ed 
     * il numero in question e' un valore intero incrementale che parte da 1
     * @return String tutte le categorie dei sondaggi formattate opportunamente
     */
    public static String displayCheckBoxCategories() {
        it.surveys.util.UtilDB utl = it.surveys.util.UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String checkBoxCategories = null;
        try {
            conn = utl.createConnection();
            stmt = utl.createStatement(conn);

            String sql = "SELECT name FROM category";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "<p>Non sono presenti categorie.</p>";
            }
            int i = 1;
            checkBoxCategories = "<s:checkboxlist class=\"form-control\" id=\"categories\" name=\"categories\" label=\"Categorie\" list=\"# {'"+i+"':'"+rs.getString(1)+"'";

            
            //String categories;   A CHE SERVE?
            while (rs.next()) {
                i++;
                //categories = rs.getString(1);   A CHE SERVE?
                checkBoxCategories = checkBoxCategories + ",'"+i+"':'"+rs.getString(1)+"'";
            }
            checkBoxCategories = checkBoxCategories + "'}\" value=\"1\"/>";
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
     * Il metodo displayCheckBoxCategories() restituisce una formattazione dei nomi delle
     * categorie presenti nel DataBase che hanno come ID (chiave primaria) uno dei
     * numeri presenti nell'ArrayList in ingresso
     * La formattazione in questione consiste in una lista di elementi checkbox,
     * ovvero '<s:checkboxlist>' secondo la sintassi di Struts2,
     * dove ogni elemento e' una coppia numero:nomedellacategoria. 
     * Il numero e' un valore intero incrementale che parte da 1.
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

            String sql = "SELECT name FROM category WHERE id IN (";
            int i = 0;
            for (; i < userCategories.size() - 1; i++) {
                sql = sql + userCategories.get(i) + ",";
            }
            sql = sql + userCategories.get(i) + ");";
            //memorizzazione del risultato della query in un ResultSet
            ResultSet rs = utl.query(stmt, sql);
            if (!rs.next()) {
                return "<p>Non sono presenti categorie.</p>";
            }
            int j = 1;
            checkBoxCategories = "<s:checkboxlist class=\"form-control\" id=\"categories\" name=\"categories\" label=\"Categorie\" list=\"# {'"+j+"':'"+rs.getString(1)+"'";

            
            //String categories;     A CHE SERVE?
            while (rs.next()) {
                i++;
                //categories = rs.getString(1);    A CHE SERVE?
                checkBoxCategories = checkBoxCategories + ",'"+j+"':'"+rs.getString(1)+"'";
            }
            checkBoxCategories = checkBoxCategories + "'}\" value=\"1\"/>";
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
