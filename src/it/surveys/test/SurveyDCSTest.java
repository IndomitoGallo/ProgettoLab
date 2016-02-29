/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.surveys.test;

import org.junit.Before;
import org.junit.Test;

import it.surveys.model.SurveyDCS;

import static org.junit.Assert.*;

/**
 *
 * @author L.Camerlengo
 */
public class SurveyDCSTest {
    
    public SurveyDCSTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of insertAnswer method, of class SurveyDCS.
     */
    //@Test
    public void testInsertAnswer() {
        System.out.println("insertAnswer");
        int idSurvey = 5;
        int idUser = 1;
        String answer = "BuonciornoAmico";
        String expResult = "success";
        String result = SurveyDCS.insertAnswer(idSurvey, idUser, answer);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of deleteSurveysWithoutCategories method, of class SurveyDCS.
     */
    //@Test
    public void testDeleteSurveysWithoutCategories() {
        System.out.println("deleteSurveysWithoutCategories");
        String expResult = "success";
        String result = SurveyDCS.deleteSurveysWithoutCategories();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of insertCategoryAssociation method, of class SurveyDCS.
     */
    @Test
    public void testInsertCategoryAssociation() {
        System.out.println("insertCategoryAssociation");
        int idSurvey = 10;
        int[] categories = new int[4];
        categories[0]=1;
        categories[1]=2;
        categories[2]=3;
        categories[3]=4;
        String expResult = "success";
        String result = SurveyDCS.insertCategoriesAssociation(idSurvey, categories);
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    }
    
   
    
}
