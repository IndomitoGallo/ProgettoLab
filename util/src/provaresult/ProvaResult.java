/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provaresult;

import it.surveys.util.UtilDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author L.Camerlengo
 */
public class ProvaResult {
    
    
    public static void main(String[] args) throws Exception {
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=utl.createConnection();
        Statement stm=utl.createStatement(conn);
        String query="select * from survey";
        ResultSet result=utl.query(stm, query);
        ArrayList<String> res=new ArrayList<>();
        res=utl.resultSetToString(result);
        String record=res.get(0);
        String[] campi=record.split("\\*");
        for(String s:campi){
            System.out.println(s);
        }
    }
    
}
