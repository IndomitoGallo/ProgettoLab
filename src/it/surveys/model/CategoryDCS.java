package it.categories.model;

import it.categories.util.UtilDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoryDCS {

    public String verifyCategory(String name) {
        it.surveys.util.UtilDB utl = it.surveys.util.UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        String surveys = null;
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

    public String displayListCategories() {

        it.surveys.util.UtilDB utl = it.surveys.util.UtilDB.getUtilDB();
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
    

    public String displayRadioCategories() {

    }

    public String displayCheckBoxCategories() {

    }

    public String displayCheckBoxCategories(int userCategories[]) {

    }
}
