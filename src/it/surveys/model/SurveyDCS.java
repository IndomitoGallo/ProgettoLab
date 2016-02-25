package it.surveys.model;

import it.surveys.util.UtilDB;
import java.sql.*;

/**
 * La funzionalità della classe DCS (Domain Control Service) è simile a quella della classe
 * DAO; a differenza di essa realizza delle feature addizionali che non riguardano un
 * particolare oggetto del dominio.
 * La classe DCS è accessibile esclusivamente tramite i manager.
 * @author L.Camerlengo
 * @version 1.0,11/02/2016
 */
public class SurveyDCS {
    
    /**
     * Effettua una formattazione in una tabella dei risultati del sondaggio passato in ingresso;
     * la tabella contiene per ogni riga una determinata risposta tra le possibili e una percentuale che esprime 
     * il numero di volte che la risposta e' stata scelta dagli utenti;
     * se una risposta non e' mai stata selezionata da un utente la sua percentuale è 0%.
     * Restituisce una tabella sotto forma di stringa contenente i risultati di un determinato sondaggio.
     * @param idSurvey int
     * @return String risultati di un determinato sondaggio
     */
    public static String displayResults(int idSurvey){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        Statement stm2=null;
        String results=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            stm2=utl.createStatement(conn);
            String query1="select answer1,answer2,answer3,answer4 from survey "+ 
                          "where id="+idSurvey;
            String query2="select answer,count(answer) numAnswer"+
                           "from answer where idSurvey="+idSurvey+" group by answer";
            ResultSet result1=utl.query(stm, query1);
            ResultSet result2=utl.query(stm2, query2);
            int numAnswer=0;
            results="<table><tr>"+ 
                        "<th>Risposta</th>"+
                        "<th>Risultato</th>"+
                    "</tr>";                        
            if(!result2.next()){
                results=results+"<tr><td>"+result1.getString(1)+"</td><td>0%</td></tr>"+
                        "<tr><td>"+result1.getString(2)+"</td><td>0%</td></tr>"+
                        "<tr><td>"+result1.getString(3)+"</td><td>0%</td></tr>"+
                        "<tr><td>"+result1.getString(4)+"</td><td>0%</td></tr>";
                return results+"</table>";
            }
            result2.beforeFirst();
            while(result2.next()){
                numAnswer=numAnswer+result2.getInt(2);
            }
            result2.beforeFirst();
            int percentage;
            while(result2.next()){
                results=results+"<tr>";
                percentage=Math.round((result2.getInt(2)*100)/numAnswer);
                results=results+"<td>"+result2.getString(1)+"</td>"+"<td>"+percentage+"%</td>";
                results=results+"</tr>";
            }
            result1.next();
            for(int i=1; i<=4; i++) {	//ricerca di risposte mai selezionate dagli utenti
            	String answer = result1.getString(i);
            	if(answer == null)
            		break;
            	if(!results.contains(answer)) {
                    results=results+"<tr>";
                    results=results+"<td>"+answer+"</td>"+"<td>0%</td>";
                    results=results+"</tr>";
            	}
            }
            results=results+"</table>";
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
                return "fail";
            }
        }
        return results;
    }
    
    /**
     * Effettua una formattazione in una tabella dei sondaggi presenti nel database;
     * la tabella contiene per ogni riga la domanda del sondaggio, un pulsante visualizza che permette al
     * responsabile di visionare l'andamento delle risposte e un pulsante cancella che consente di cancellare
     * il sondaggio dal database.
     * Restituisce una tabella sotto forma di stringa contenente i sondaggi presenti nel database, altrimenti
     * se non è presente alcun sondaggio nel database viene restiuito un messaggio sotto forma di stringa
     * che notifica al responsabile che non sono presenti sondaggi.
     * @return String sondaggi presenti nel database.
     */
    public static String displayCreatedSurveys(){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        String surveys=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String query="select question,id from survey";
            ResultSet result=utl.query(stm, query);
             if(!result.next()){
                return "<p>Non sono presenti sondaggi.</p>";
            }
            surveys="<table><tr>"+
                        "<th>Sondaggio</th>"+
                        "<th>Risultati</th>"+
                        "<th>Cancellazione</th>"+
                    "</tr>";                   
           
            result.beforeFirst();
            while(result.next()){
                surveys=surveys+"<tr>";
                surveys=surveys+"<td>"+result.getString(1)+"</td>"+
                        "<td><a href=\"displayResults.action?id="+result.getString(2)+"\">Visualizza</a></td>"+
                        "<td><a onclick=\"confirmation("+result.getString(2)+")\">Cancella</a></td>";                      
                surveys=surveys+"</tr>";
            }
            surveys=surveys+"</table>";
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
                return "fail";
            }
        }
        return surveys;
    }
    
    /**
     * Effettua una formattazione in una tabella dei sondaggi appartenenti alle categorie di interesse per l'utente passato in ingresso;
     * la tabella contiene per ogni riga la domanda del sondaggio e un pulsante visualizza che permette all'utente di
     * visualizzare le risposte alla domanda e di selezionarne una tra quelle possibili.
     * Restituisce una tabella sotto forma di stringa contenente i sondaggi appartenenti alle categoire di interesse per l'utente presenti nel 
     * database, altrimenti se non è presente alcun sondaggio associato alle categorie di interesse dell'utente restiuisce un
     * messaggio sotto forma di stringa, che invita l'utente ad aggiungere nuove categorie di interesse.
     * @param idUser int
     * @return String Sondaggi delle relative categorie preferite dell'utente. 
     */
    public static String displayAllowedSurvey(int idUser){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        String surveys=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String query="select s.question,s.id from categoriesSurvey c1,survey s,categoriesUser c"+
                         "where c.idUser="+idUser+"and c.idCategory=c1.idCategory and c1.idSurvey=s.id";
            ResultSet res=utl.query(stm, query);
            if(!res.next()){
                return "<p>Attenzione: o non sono presenti sondaggi per le categorie scelte oppure sono state "+
                		"cancellate tutte le categorie scelte, in questo caso vai su 'Modifica profilo' per sceglierne altre.</p>";
            }
            surveys="<table><tr>"+
                        "<th>Sondaggio</th>"+
                        "<th>Rispondi</th>"+
                    "</tr>";
            res.beforeFirst();
            while(res.next()){
                surveys=surveys+"<tr>";
                surveys=surveys+"<td>"+res.getString(1)+"</td>"+
                        "<td><a href=\"displaySurvey.action?id="+res.getString(2)+"\">Visualizza</a></td>";
                surveys=surveys+"</tr>";
            }
            surveys=surveys+"</table>";                   
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
                return "fail";
            }
        }
        return surveys;
    }
    
    
    /**
     * Effettua l'operazione di inserimento nel database di una risposta ad un determinato sondaggio 
     * da parte di un determinato utente.
     * Restituisce "success" se l'inserimento è andato a buon fine, "fail" altrimenti.
     * @param idSurvey int
     * @param idUser int
     * @param answer String
     * @return String esito dell'inserimento della risposta
     */
    public static String insertAnswer(int idSurvey,int idUser,String answer){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            String insert="insert into answer values("+idUser+","+idSurvey+",'"+answer+"')";
            int rows=utl.manipulate(stm, insert);
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
        }finally{
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
     * Effettua l'operazione di cancellazione nel database dei sondaggi che non hanno categorie associate.
     * Restituisce "success" se la cancellazione è andata a buon fine o non esistono sondaggi da cancellare,
     * "fail" altrimenti.
     * @return String esito della cancellazione
     */
    public static String deleteSurveysWithoutCategories(){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        Statement stm2=null;
        try{
            conn=utl.createConnection();
            stm=utl.createStatement(conn);
            stm2=utl.createStatement(conn);
            String query="select s.id from survey s where (s.id) not in "+
                         "(select cs.idSurvey from categoriessurvey cs)";
            String delete;
            ResultSet result=utl.query(stm, query);
            while(result.next()){
                delete="delete from survey where id="+result.getString(1);
                int rows=utl.manipulate(stm2, delete);
                if(rows!=1){
                    System.err.println("Delete Database Error!");
                    return "fail";
                }
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
            if(stm2!=null)
                utl.closeStatement(stm2);
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
     * Effettua un inserimento nel database delle associazioni del sondaggio con le categorie passate in
     * ingresso.
     * restituisce "success" se l'inserimento delle associazioni è andato a buon fine, "fail" altrimenti.
     * @param idSurvey int
     * @param categories int[]
     * @return String esito dell'inserimento
     */
    public static String insertCategoryAssociation(int idSurvey,int[] categories){
        UtilDB utl=UtilDB.getUtilDB();
        Connection conn=null;
        Statement stm=null;
        try{
        conn=utl.createConnection();
        stm=utl.createStatement(conn);
        String query;
        for(int c:categories){
            query="insert into categoriesSurvey values("+idSurvey+","+c+")";
            int rows=utl.manipulate(stm, query);
            if(rows!=1){
                System.err.println("Insert Database Error!");
                return "fail";
            }
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
