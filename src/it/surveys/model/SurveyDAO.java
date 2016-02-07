package it.surveys.model;

import it.surveys.form.Survey;
import it.surveys.util.UtilDB;
import java.sql.*;
/**
 * La classe SurveyDAO contiene i metodi di interrogazione e manipolazione della corrispondente
 * classe di dominio. 
 * Essa, si fa carico di gestire il codice SQL, mentre tutto ciò è
 * trasparente rispetto alla corrispondente classe di dominio.
 * In pratica contiene le funzionalità di base (CRUD).
 * I Data Access Object sono accessibili esclusivamente tramite i Manager.
 * @author L.Camerlengo
 * @version 1.0,6/02/2016
 */
public class SurveyDAO {
    /**
     * Effettua l'operazione di inserimento nel database del sondaggio passato come argomento.
     * Restituisce "success" se l'inserimento è andato a buon fine, "fail" altrimenti. 
     * @param s Survey
     * @return String esito dell'inserimento
     * 
     */
    public String insert( Survey s){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String[] answers=s.getAnswers();
            String ins;
            if(answers.length==2){
                ins="insert into Survey values(" +s.getId()+","+
                                                 "'"+s.getQuestion()+"',"+
                                                 "'"+answers[1]+"',"+
                                                 "'"+answers[2]+"')";
            } else
            if(answers.length==3){
                ins="insert into Survey values(" +s.getId()+","+
                                                 "'"+s.getQuestion()+"',"+
                                                 "'"+answers[1]+"',"+
                                                 "'"+answers[2]+"',"+
                                                 "'"+answers[3]+"')";           
            } else
            if(answers.length==4){
                ins="insert into Survey values(" +s.getId()+","+
                                                 "'"+s.getQuestion()+"',"+
                                                 "'"+answers[1]+"',"+
                                                 "'"+answers[2]+"',"+
                                                 "'"+answers[3]+"',"+
                                                 "'"+answers[4]+"')";       
            } else {
                System.err.println("Survey answer error!");
                return "fail";
        }
            int rows=utl.manipulate(stm, ins);
            if(rows!=1){
            System.err.println("Insert Database Error!");
            return "fail";
        }
        }catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
	    e.printStackTrace();
            return "fail";
        }catch(SQLException e){
            System.err.println("Database Error!");
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
            }
        }
        return "success";
    }

    /**
     * Effettua l'operazione di retrive ovvero il recupero dei dati nel database del Survey passato come argomento
     * settando tutti i parametri di esso.
     * Restituisce "success" se il recupero e il settaggio dei dati è andato a buon fine, "fail" altrimenti.
     * @param s Survey
     * @return String esito del recupero dei dati
     */
    public String retrive(Survey s){
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
               s.setId(Integer.parseInt(result.getString(1)));
               s.setQuestion(result.getString(2));
               if(result.getInt(5)==0){
                    answers=new String[2];
                    answers[1]=result.getString(3);
                    answers[2]=result.getString(4);
               }
               else if(result.getInt(5)!=0){
                   if(result.getInt(6)==0){
                        answers=new String[3];
                        answers[1]=result.getString(3);
                        answers[2]=result.getString(4);
                        answers[3]=result.getString(5);
                   }else{
                        answers=new String[4];
                        answers[1]=result.getString(3);
                        answers[2]=result.getString(4);
                        answers[3]=result.getString(5);
                        answers[4]=result.getString(6);
                   }
               }
               s.setAnswers(answers);
            }
        }catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
	    e.printStackTrace();
            return "fail";
        }catch(SQLException e){
            System.err.println("Database Error!");
	    e.printStackTrace();
            return "fail";
        }finally{
            try{
            if(stm!=null)
                utl.closeStatement(stm);
            if(conn!=null)
                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
            }
        }
        return "success";
    }

    /**
     * Effettua l'operazione di aggiornamento dei dati nel database del Survey passato come argomento.
     * Restituisce "success" se l'aggiornamento dei dati è andato a buon fine, "fail" altrimenti.
     * @param s Survey
     * @return String esito dell'aggiornamento 
     */
    public String update(Survey s){
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
                                      "answer1='"+answers[1]+"',"+
                                      "answer2='"+answers[2]+"'"+
                                      "where id="+s.getId();                                       
            }
            if(answers.length==3){
               update="update survey set question='"+s.getQuestion()+"',"+
                                      "answer1='"+answers[1]+"',"+
                                      "answer2='"+answers[2]+"',"+
                                      "answer3='"+answers[3]+"'"+
                                      "where id="+s.getId();
            }
            if(answers.length==4){
                update="update survey set question='"+s.getQuestion()+"',"+
                                      "answer1='"+answers[1]+"',"+
                                      "answer2='"+answers[2]+"',"+
                                      "answer3='"+answers[3]+"',"+
                                      "answer4='"+answers[4]+"'"+
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
        }catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
	    e.printStackTrace();
            return "fail";
        }catch(SQLException e){
            System.err.println("Database Error!");
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
            }
        }
        return "success";
    }
    /**
     * Effettua l'operazione di cancellazione nel database del Survey passato come argomento.
     * Restituisce "success" se è stata effettuata ed è andata a buon fine la cancellazione, fail altrimenti.
     * @param s Survey
     * @return String esito della cancellazione 
     */
    public String delete(Survey s){
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
        }catch(ClassNotFoundException e){
            System.err.println("Driver Not Found!");
	    e.printStackTrace();
            return "fail";
        }catch(SQLException e){
            System.err.println("Database Error!");
	    e.printStackTrace();
            return "fail";
        }finally{
            try{
            if(stm!=null)
                utl.closeStatement(stm);
            if(conn!=null)
                utl.closeConnection(conn);
            } catch(SQLException e){
                System.err.println("Close Resource Error!");
                e.printStackTrace();
            }
        }
        return "success";
    }
}