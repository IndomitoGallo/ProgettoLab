package it.surveys.model;

import it.surveys.util.UtilDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDCS {

    public static String verifyCategory(String name) {
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

    public static String displayListCategories() {

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

    public static String displayRadioCategories() {
        it.surveys.util.UtilDB utl = it.surveys.util.UtilDB.getUtilDB();
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
            radioCategories = "<form action=\"..\\CategoryAction\">";

            rs.beforeFirst();
            String categories;
            while (rs.next()) {
                categories = rs.getString(1);
                radioCategories = radioCategories + "<input type=\"radio\" name=\"" + categories + "\" value=\"" + categories + "\">" + categories + "<br>";
            }
            radioCategories = radioCategories + "<input type=\"submit\" value=\"Submit\"></form>";;
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
            checkBoxCategories = "<form action=\"..\\CategoryAction\">";

            rs.beforeFirst();
            String categories;
            while (rs.next()) {
                categories = rs.getString(1);
                checkBoxCategories = checkBoxCategories + "<input type=\"checkbox\" name=\"" + categories + "\" value=\"" + categories + "\">" + categories + "<br>";
            }
            checkBoxCategories = checkBoxCategories + "<input type=\"submit\" value=\"Submit\"></form>";
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

    public static String displayCheckBoxCategories(ArrayList<Integer> userCategories) {
        it.surveys.util.UtilDB utl = it.surveys.util.UtilDB.getUtilDB();
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
            checkBoxCategories = "<form action=\"..\\CategoryAction\">";

            rs.beforeFirst();
            String categories;
            while (rs.next()) {
                categories = rs.getString(1);
                checkBoxCategories = checkBoxCategories + "<input type=\"checkbox\" name=\"" + categories + "\" value=\"" + categories + "\">" + categories + "<br>";
            }
            checkBoxCategories = checkBoxCategories + "<input type=\"submit\" value=\"Submit\"></form>";
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
