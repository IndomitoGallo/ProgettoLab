package it.surveys.test;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

import it.surveys.model.UserDCS;

/*
 * La classe UserDCSTest, per essere avviata, presuppone l'inserimento nel database 
 * di un utente tramite la classe UserDAOTest e di una categoria tramite la classe CategoryDAOTest.
 */

public class UserDCSTest {

    /**
     * Test of verifyLoginData method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testVerifyLoginData() {
        System.out.println("verifyLoginData");
        String user = "Username1";
        String pwd = "password1";
        int expResult = 1;
        int result = UserDCS.verifyLoginData(user, pwd);
        assertEquals(expResult, result);
       // fail("The test case is a prototype.");
    }
    
    /**
     * Test of verifySignupData method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testVerifySignupData() {
        System.out.println("verifySignupData");
        String existingUser = "Username1";
        String existingMail = "example@gmail.com";
        String unexistingUser = "Username2";
        String unexistingMail = "example2@gmail.com";
        String expNegativeResult = "false";
        String expPositiveResult = "true";
        //Test username esistente
        String negativeResult1 = UserDCS.verifySignupData(existingUser, unexistingMail);
        assertEquals(expNegativeResult, negativeResult1);
        //test email esistente
        String negativeResult2 = UserDCS.verifySignupData(unexistingUser, existingMail);
        assertEquals(expNegativeResult, negativeResult2);
        //test campi validi
        String positiveResult = UserDCS.verifySignupData(unexistingUser, unexistingMail);
        assertEquals(expPositiveResult, positiveResult);
       // fail("The test case is a prototype.");
    }
    
    /**
     * Test of verifyUpdateData method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testVerifyUpdateData() {
    	System.out.println("verifyUpdateData");
    	int id = 2;
        String existingUser = "Username1";
        String existingMail = "example@gmail.com";
        String unexistingUser = "Username2";
        String unexistingMail = "example2@gmail.com";
        String expNegativeResult = "false";
        String expPositiveResult = "true";
        //Test username esistente
        String negativeResult1 = UserDCS.verifyUpdateData(id, existingUser, unexistingMail);
        assertEquals(expNegativeResult, negativeResult1);
        //test email esistente
        String negativeResult2 = UserDCS.verifyUpdateData(id, unexistingUser, existingMail);
        assertEquals(expNegativeResult, negativeResult2);
        //test campi validi
        String positiveResult = UserDCS.verifyUpdateData(id, unexistingUser, unexistingMail);
        assertEquals(expPositiveResult, positiveResult);
       // fail("The test case is a prototype.");
    }

    /**
     * Test of insertCategoriesAssociation method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testInsertCategoriesAssociation() {
        System.out.println("insertCategories");
        int id = 1;
        int[] categories = {1};
        String expResult = "success";
        String result = UserDCS.insertCategoriesAssociation(id,  categories);
        assertEquals(expResult, result);      
       //fail("The test case is a prototype.");
    }
    
    /**
     * Test of updateCategoriesAssociation method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testUpdateCategoriesAssociation() {
        System.out.println("updateCategories");
        int id = 1;
        int[] categories = {1};
        String expResult = "success";
        String result = UserDCS.updateCategoriesAssociation(id,  categories);
        assertEquals(expResult, result);      
       //fail("The test case is a prototype.");
    }
    
    /**
     * Test of retrieveCategoriesAssociation method, of class UserDCS.
     * @throws Exception 
     */
    @Test
    public void testRetrieveCategoriesAssociation() {
        System.out.println("retrieveCategories");
        int id = 1;
        ArrayList<Integer> expResult = new ArrayList<Integer>();
        expResult.add(1);
        ArrayList<Integer> result = UserDCS.retrieveCategoriesAssociation(id);
        assertEquals(expResult, result);      
       //fail("The test case is a prototype.");
    }

}
