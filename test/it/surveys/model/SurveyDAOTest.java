/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.surveys.model;

import it.surveys.form.Survey;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author L.Camerlengo
 */
public class SurveyDAOTest {
    
    public SurveyDAOTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of insert method, of class SurveyDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Survey s = new Survey();
        s.setId("1");
        s.setQuestion("Che bel giorno");
        String[] answers=new String[4];
        answers[0]="Si";
        answers[1]="No";
        answers[2]="Okay";
        answers[3]="Certo";
        s.setAnswers(answers);
        SurveyDAO instance = new SurveyDAO();
        String expResult = "success";
        String result = instance.insert(s);
        assertEquals(expResult, result);
       // fail("The test case is a prototype.");
    }

    /**
     * Test of retrive method, of class SurveyDAO.
     */
    @Test
    public void testRetrive() {
        System.out.println("retrive");
        Survey s = new Survey();
        s.setId("1");
        SurveyDAO instance = new SurveyDAO();
        String expResult = "success";
        String result = instance.retrive(s);
        assertEquals(expResult, result);
       //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class SurveyDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Survey s = new Survey();
        s.setId("1");
        s.setQuestion("ciao beluuuu");
        String[] answers=new String[2];
        answers[0]="forse";
        answers[1]="vorrai";
        s.setAnswers(answers);
        SurveyDAO instance = new SurveyDAO();
        String expResult = "success";
        String result = instance.update(s);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class SurveyDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Survey s = new Survey();
        s.setId("1");
        SurveyDAO instance = new SurveyDAO();
        String expResult = "success";
        String result = instance.delete(s);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
}
