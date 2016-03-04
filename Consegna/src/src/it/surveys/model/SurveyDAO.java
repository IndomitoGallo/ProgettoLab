package it.surveys.model;

import it.surveys.domain.Survey;
import it.surveys.util.UtilDB;

import java.io.IOException;
import java.sql.*;

/**
 * La classe SurveyDAO (Data Access Object) contiene i metodi che eseguono 
 * le operazioni CRUD riguardanti la corrispondente classe di dominio Survey.
 * @author L.Camerlengo
 * @version 1.0,6/02/2016
 */
public class SurveyDAO {
    
	/**
     * Effettua l'operazione di inserimento nel database del sondaggio passato come argomento.
     * Inoltre, viene prelevato l'id e settato l'oggetto Survey passato come parametro,
	 * per un uso successivo dell'identificatore nel {@link SurveyManager}.
	 * Restituisce "success" se l'inserimento e' andato a buon fine, "fail" altrimenti.
     * @param s Survey
     * @return String esito dell'inserimento
     * @author L.Camerlengo
     */
    public static String insert(Survey s){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String[] answers=s.getAnswers();
            String ins;
            if(answers[2].isEmpty()){
                ins="insert into Survey(id,question,answer1,answer2) values(null," +
                                                 "'" + s.getQuestion() + "'," +
                                                 "'" + answers[0] + "'," +
                                                 "'" + answers[1] + "')";
            } else
            if(answers[3].isEmpty()){
                ins="insert into Survey(id,question,answer1,answer2,answer3) values(null,"+
                                                 "'"+s.getQuestion()+"',"+
                                                 "'"+answers[0]+"',"+
                                                 "'"+answers[1]+"',"+
                                                 "'"+answers[2]+"')";           
            } else {
                ins="insert into Survey(id,question,answer1,answer2,answer3,answer4) values(null,"+
                                                 "'"+s.getQuestion()+"',"+
                                                 "'"+answers[0]+"',"+
                                                 "'"+answers[1]+"',"+
                                                 "'"+answers[2]+"',"+
                                                 "'"+answers[3]+"')";       
            }
            int rows=utl.manipulate(stm, ins);
            if(rows!=1){
                System.err.println("Insert Database Error!");
                return "fail";
            }
			String sql = "SELECT id FROM survey WHERE question='" + s.getQuestion() + "'";
			ResultSet rs = utl.query(stm, sql);
			rs.next();
			s.setId(rs.getInt(1)); //set del campo id del sondaggio per poi usarlo nell'inserimento delle categorie scelte
       } catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
       } catch(IOException e){
            System.err.println("Reading Configuration File Error!");
            e.printStackTrace();
            return "fail";
       } finally{
            try{
	            if(stm!=null)
	                utl.closeStatement(stm);
	            if(conn!=null)
	                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    /**
     * Effettua l'operazione di retrieve, ovvero il recupero dei dati nel database del Survey 
     * passato come argomento settando tutti i parametri di esso.
     * Restituisce "success" se il recupero e il settaggio dei dati e' andato a buon fine, "fail" altrimenti.
     * @param s Survey
     * @return String esito del recupero dei dati
     * @author L.Camerlengo
     */
    public static String retrieve(Survey s){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn =null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String query="select * from survey where id="+s.getId();
            ResultSet result=utl.query(stm, query);
            if(result.next()){
               String[] answers=null; 
               s.setId(result.getInt(1));
               s.setQuestion(result.getString(2));
               if(result.getString(5) == null){
                    answers=new String[2];
                    answers[0]=result.getString(3);
                    answers[1]=result.getString(4);
               }
               else if(result.getString(6) == null) {
                    answers=new String[3];
                    answers[0]=result.getString(3);
                    answers[1]=result.getString(4);
                    answers[2]=result.getString(5);
               }
               else {
                    answers=new String[4];
                    answers[0]=result.getString(3);
                    answers[1]=result.getString(4);
                    answers[2]=result.getString(5);
                    answers[3]=result.getString(6);
               }
               s.setAnswers(answers);
            }
       } catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
       } catch(IOException e){
            System.err.println("Reading Configuration File Error!");
            e.printStackTrace();
            return "fail";
       } finally{
            try{
	            if(stm!=null)
	                utl.closeStatement(stm);
	            if(conn!=null)
	                utl.closeConnection(conn);
            }catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    /**
     * Effettua l'operazione di aggiornamento dei dati nel database del Survey passato come argomento.
     * Restituisce "success" se l'aggiornamento dei dati e' andato a buon fine, "fail" altrimenti.
     * @param s Survey
     * @return String esito dell'aggiornamento  
     * @author L.Camerlengo
     */
    public static String update(Survey s){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String[] answers=s.getAnswers();
            String update;
            if(answers.length==2){
                update="update survey set question='"+s.getQuestion()+"',"+
                                      "answer1='"+answers[0]+"',"+
                                      "answer2='"+answers[1]+"'"+
                                      "where id="+s.getId();                                       
            }
            else if(answers.length==3){
               update="update survey set question='"+s.getQuestion()+"',"+
                                      "answer1='"+answers[0]+"',"+
                                      "answer2='"+answers[1]+"',"+
                                      "answer3='"+answers[2]+"'"+
                                      "where id="+s.getId();
            }
            else if(answers.length==4){
                update="update survey set question='"+s.getQuestion()+"',"+
                                      "answer1='"+answers[0]+"',"+
                                      "answer2='"+answers[1]+"',"+
                                      "answer3='"+answers[2]+"',"+
                                      "answer4='"+answers[3]+"'"+
                                      "where id="+s.getId();
            }else{
                System.err.println("Survey answer error!");
                return "fail";
            }
            int rows=utl.manipulate(stm, update);
            if(rows!=1){
                System.err.println("Update Database Error!");
                return "fail";
            }
       } catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
       } catch(IOException e){
            System.err.println("Reading Configuration File Error!");
            e.printStackTrace();
            return "fail";
       } finally{
            try{
	            if(stm!=null)
	                utl.closeStatement(stm);
	            if(conn!=null)
	                utl.closeConnection(conn);
            }catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }
    
    /**
     * Effettua l'operazione di cancellazione nel database del Survey passato come argomento.
     * Restituisce "success" se e' stata effettuata ed e' andata a buon fine la cancellazione, fail altrimenti.
     * @param s Survey
     * @return String esito della cancellazione  
     * @author L.Camerlengo
     */
    public static String delete(Survey s){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String delete="delete from survey where id="+s.getId();
            int rows=utl.manipulate(stm, delete);
            if(rows!=1){
                System.err.println("Delete Database Error!");
                return "fail";
            }
       } catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
            e.printStackTrace();
            return "fail";
       } catch(SQLException e){
            System.err.println("Database Error!");
            e.printStackTrace();
            return "fail";
       }catch(IOException e){
            System.err.println("Reading Configuration File Error!");
            e.printStackTrace();
            return "fail";
       } finally{
            try{
	            if(stm!=null)
	                utl.closeStatement(stm);
	            if(conn!=null)
	                utl.closeConnection(conn);
            }catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }
}