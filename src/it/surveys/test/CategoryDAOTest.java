package it.surveys.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import it.surveys.domain.Category;
import it.surveys.model.CategoryDAO;
import it.surveys.util.UtilDB;

public class CategoryDAOTest {

    /**
     * Test of insert method, of class CategoryDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Category c = new Category();
        c.setName("Category1");
        String expResult = "success";
        String result = CategoryDAO.insert(c);
        assertEquals(expResult, result);
       // fail("The test case is a prototype.");
    }

    /**
     * Test of retrieve method, of class CategoryDAO.
     */
    @Test
    public void testRetrieve() {
        System.out.println("retrieve");
        Category c = new Category();
        c.setId(1);
        String expResult = "success";
        String result = CategoryDAO.retrieve(c);
        assertEquals(expResult, result);
        System.out.println(c.getName());        
       //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class CategoryDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Category c = new Category();
        c.setId(1);
        c.setName("New_Category");
        String expResult = "success";
        String result = CategoryDAO.update(c);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class CategoryDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Category c = new Category();
        c.setId(1);
        String expResult = "success";
        String result = CategoryDAO.delete(c);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
	@After
    public void tearDown() {
		System.out.println("auto_increment to 1");
        UtilDB utl = UtilDB.getUtilDB();
        Connection conn = null;
        Statement stmt = null;
        try{
            conn=utl.createConnection();
            stmt=utl.createStatement(conn);
            String query = "alter table category auto_increment = 1";
            utl.manipulate(stmt, query);
	    }catch(ClassNotFoundException e){
	        System.err.println("Driver Not Found!");
	        e.printStackTrace();
	    }catch(SQLException e){
	        System.err.println("Database Error!");
	        e.printStackTrace();
	    }finally{
            try{
	            if(stmt!=null)
	                utl.closeStatement(stmt);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Closing Resources Error!");
                e.printStackTrace();
            }
	    }
    }

}
