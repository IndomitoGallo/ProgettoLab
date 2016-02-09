package it.surveys.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import it.surveys.domain.Survey;
import it.surveys.model.SurveyDAO;
import it.surveys.util.UtilDB;

public class SurveyDAOTest {

    /**
     * Test of insert method, of class SurveyDAO.
     * @throws Exception 
     */
    @Test
    public void testInsert() throws Exception {
        System.out.println("insert");
        Survey s = new Survey();
        s.setId(1);
        s.setQuestion("Question1");
        String[] answers=new String[4];
        answers[0]="Answer1";
        answers[1]="Answer2";
        answers[2]="Answer3";
        answers[3]="Answer4";
        s.setAnswers(answers);
        String expResult = "success";
        String result = SurveyDAO.insert(s);
        assertEquals(expResult, result);
       // fail("The test case is a prototype.");
    }

    /**
     * Test of retrieve method, of class SurveyDAO.
     * @throws Exception 
     */
    @Test
    public void testRetrieve() throws Exception {
        System.out.println("retrive");
        Survey s = new Survey();
        s.setId(1);
        String expResult = "success";
        String result = SurveyDAO.retrieve(s);
        assertEquals(expResult, result);
        System.out.println(s.getQuestion() + " " + s.getAnswers()[0] + " "
        										 + s.getAnswers()[1] + " "
        										 + s.getAnswers()[2] + " "
        										 + s.getAnswers()[3]);        
       //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class SurveyDAO.
     * @throws Exception 
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Survey s = new Survey();
        s.setId(1);
        s.setQuestion("New_Question1");
        String[] answers=new String[4];
        answers[0]="New_Answer1";
        answers[1]="New_Answer2";
        answers[2]="Answer3";
        answers[3]="Answer4";
        s.setAnswers(answers);
        String expResult = "success";
        String result = SurveyDAO.update(s);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SurveyDAO.
     * @throws Exception 
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Survey s = new Survey();
        s.setId(1);
        String expResult = "success";
        String result = SurveyDAO.delete(s);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
	@After
    public void tearDown() throws Exception {
		System.out.println("auto_increment to 1");
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn = null;
        Statement stm = null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String query="alter table survey auto_increment = 1";
            utl.manipulate(stm, query);
	    }catch(ClassNotFoundException e){
	        System.err.println("Driver Not Found!");
	        e.printStackTrace();
	    }catch(SQLException e){
	        System.err.println("Database Error!");
	        e.printStackTrace();
	    }finally{
	        if(stm!=null)
	            utl.closeStatement(stm);
	        if(conn!=null)
	            utl.closeConnection(conn);
	    }
    }

}
