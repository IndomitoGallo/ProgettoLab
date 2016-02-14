package it.surveys.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import it.surveys.domain.User;
import it.surveys.model.UserDAO;
import it.surveys.util.UtilDB;

public class UserDAOTest {

    /**
     * Test of insert method, of class UserDAO.
     * @throws Exception 
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        User u = new User();
        u.setUsername("Username1");
        u.setName("Name1");
        u.setSurname("Surname1");
        u.setPassword("Password1");
        u.setEmail("example@gmail.com");
        String expResult = "success";
        String result = UserDAO.insert(u);
        assertEquals(expResult, result);
       // fail("The test case is a prototype.");
    }

    /**
     * Test of retrieve method, of class UserDAO.
     * @throws Exception 
     */
    @Test
    public void testRetrieve() {
        System.out.println("retrieve");
        User u = new User();
        u.setId(1);
        String expResult = "success";
        String result = UserDAO.retrieve(u);
        assertEquals(expResult, result);
        System.out.println(u.getUsername() + " " + u.getName() + " "
        									 	 + u.getSurname() + " "
        										 + u.getPassword() + " "
        										 + u.getEmail());        
       //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class UserDAO.
     * @throws Exception 
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        User u = new User();
        u.setId(1);
        u.setUsername("New_Username");
        u.setName("New_Name1");
        u.setSurname("New_Surname1");
        u.setPassword("New_Pwd1");
        u.setEmail("example@new.it");
        String expResult = "success";
        String result = UserDAO.update(u);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class UserDAO.
     * @throws Exception 
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        User u = new User();
        u.setId(1);
        String expResult = "success";
        String result = UserDAO.delete(u);
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
            String query = "alter table user auto_increment = 1";
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
