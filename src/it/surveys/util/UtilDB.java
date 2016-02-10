package it.surveys.util;

import java.sql.*;
import java.util.ArrayList;

/**
 * L'utility di accesso al DataBase implementata come una factory con il pattern
 * singleton. Questa utility viene istanziata ed utilizzata dal model.
 * Nell'architettura dell'applicazione si pone tra il model e il JDBC.
 * @author Team Talocci
 * @version 4.0 10/02/2016
 */
public class UtilDB {
	
	private static UtilDB utl;
	
	/**
	 * Il metodo e' l'implementazione del pattern singleton per avere una sola
	 * istanza dell'utility. Quando viene chiamato per la prima volta, istanzia
	 * e ritorna un nuovo oggetto di tipo UtilDB, mentre le volte successive,
	 * ritorna l'oggetto che e' stato istanziato la prima volta e che e' stato
	 * salvato nell'attributo, privato e statico, {@link #utl}
	 * @return utl l'oggetto singleton UtilDB 
	 */  
	public static UtilDB getUtilDB() {
		if(utl == null)
			utl = new UtilDB();
		return utl;
	}
	
	/**
	 * Il metodo createConnection() crea una connessione ad un DB 
	 * caricando innanzitutto il Driver e poi sfruttando il metodo privato
	 * {@link #createConnection(java.lang.String, java.lang.String, java.lang.String)}
         * L'esistenza di questo metodo permette di cambiare il database a cui si accede in modo semplice:
         * e' l'unico metodo a contenere i riferimenti al database utilizzato.
	 * @return conn Connection to DB
         * @see com.mysql.jdbc.Driver
	 */
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = createConnection("surveys", "root", "");
        return conn;
    }

	/**
	 * Il metodo crea una connessione con il DataBase
	 * attraverso la classe DriverManager gia' presente nel package java.sql.
	 * @param db String nome del database
	 * @param username String username dell'utente mySQL
	 * @param password String password dell'utente mySQL
	 * @return Connection to DB
	 * @throws SQLException 
         * @see java.sql.DriverManager
	 */
	private Connection createConnection(String db, String username, String password)
			throws SQLException {		
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/" + db,
				username,
				password);
	}
	
	/**
	 * Il metodo createStatement permette di creare
	 * uno Statement associato alla connessione passata in ingresso.
         * Lo Statement e' un oggetto necessario per poter eseguire query
         * mysql.
	 * @param conn Connection to DB
	 * @return Statement associato alla connessione in ingresso
	 * @throws SQLException 
         * @see java.sql.Statement
	 */
	public Statement createStatement(Connection conn) throws SQLException {
		return conn.createStatement();
	}

	/**
	 * Il metodo query ha il compito di eseguire una query sul DB, il cui risultato
	 * e' un oggetto di tipo ResultSet che contiene i record restituiti dalla query.
	 * @param stmt oggetto Statement associato ad una connessione al database
	 * @param qry query in linguaggio MySQL 
	 * @return oggetto di tipo ResultSet che contiene insieme dei record risultanti dalla query qry
	 * @throws SQLException 
         * @see java.sql.Statement
	 */
	public ResultSet query(Statement stmt, String qry) throws SQLException {
		return stmt.executeQuery(qry);
	}

	/**
	 * Il metodo manipulate gestisce le operazioni di Insert, Update e Delete
	 * sul DB.
	 * @param stmt oggetto di tipo Statement associato ad una connessione al database
	 * @param qry query in linguaggio MySQL e in formato String
	 * @return int numero di righe(tuple/records) coinvolte nella query
         * @throws SQLException 
         * @see java.sql.Statement#executeUpdate(java.lang.String) 
	 */
	public int manipulate(Statement stmt, String qry) throws SQLException {
		return stmt.executeUpdate(qry);
	}
	
	/**
	 * Il metodo closeStatement permette di chiudere lo 
	 * Statement passato in ingresso.
	 * @param stmt oggetto di tipo Statement che si vuole chiudere
	 * @throws SQLException 
         * @see java.sql.Statement#close() 
	 */
	public void closeStatement(Statement stmt) throws SQLException {
		stmt.close();
	}
	
	/**
	 * Il metodo closeConnection ha il compito di chiudere la connessione al DB.
	 * Chiudere la connessione puo' essere necessario poiche' un DBMS puo' gestire soltanto un pool
	 * predefinito di connessioni in parallelo.
	 * @param conn Connessione al DataBase
	 * @throws SQLException 
         * @see java.sql.Connection#close()
	 */
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	/**
	 * Il metodo printResultSet stampa a schermo il resultSet in ingresso.
	 * @param rs un oggetto di tipo ResultSet
	 * @throws SQLException 
         * @see java.sql.ResultSet
	 */
	public void printResultSet(ResultSet rs) throws SQLException{
		//memorizzo nell'oggetto resM i MetaData (ovvero le meta-informazioni) sul ResultSet
		ResultSetMetaData resM=rs.getMetaData();
		//in questo esempio prendiamo dalle meta-informazioni il numero di colonne del ResultSet
		int colonne= resM.getColumnCount();
		for(int i = 0; i < colonne; i++) {
			System.out.print(resM.getColumnName(i+1) + " | ");
		}
		System.out.println();
		while(rs.next()){
			for(int i = 0; i < colonne; i++){
				System.out.print(rs.getString(i+1) + " | ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Il metodo resultSetToArrayString prende in ingresso un oggetto di tipo ResultSet
         * e restituisce un ArrayList di oggetti di tipo String, dove ogni Stringa e' un record del ResultSet
	 * L'esistenza del metodo e' finalizzata a rendere la gestione dei risultati delle query
         * sul DB piu' semplici da parte dell'applicazione
         * Il metodo ottiene in primo luogo un oggetto di tipo ResultSetMetaData a partire dal ResultSet rs in ingresso
         * Esso permette di conoscere il numero di colonne del ResultSet rs, in modo da separare
         * ,per ogni stringa nell'ArrayList di ritorno, i campi del record (tramite '*')
	 * @param rs oggetto di tipo ResultSet 
         * @return un oggetto di tipo ArrayList<String> , ovvero una collezione di oggetti di tipo String
	 * @throws SQLException 
         * @see java.sql.ResultSet#getMetaData() 
         * @see java.sql.ResultSetMetaData  
	 */
	public ArrayList<String> resultSetToArrayString(ResultSet rs) throws SQLException{
		ArrayList<String> result=new ArrayList<>();
                String str="";
		ResultSetMetaData resM=rs.getMetaData();
		int colonne= resM.getColumnCount();
		for(int i = 0; i < colonne; i++) {
			str = str +resM.getColumnName(i+1) + "*";
		}
                result.add(str);
		while(rs.next()){
                        str="";
			for(int i = 0; i < colonne; i++){
				str = str +rs.getString(i+1) + "*";
			}
			result.add(str);
		}
		return result;
	}

	/**
	 * Stampa a schermo la lista di tutte le tabelle del DataBase, una per riga
	 * @param conn La connessione al DataBase
         * @see java.sql.ResultSet#getString(int) 
	 * @throws SQLException
	 */
	public void printTables(Connection conn) throws SQLException{
		//memorizziamo nell'oggetto dbmb le meta-informazioni sul database
		DatabaseMetaData dbmd = conn.getMetaData();

		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = null;
		String[] types = null;
		
		ResultSet rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);
		
		System.out.println("Tables:");
		while(rs.next()) {
			/* ad ogni iterazione tableName conterra' la colonna
			 * di indice 3 di result, ovvero, quella contenente
			 * il nome della tabella del db
			 */
		    String tableName = rs.getString(3);
		    System.out.println(tableName);
		}
	}
		
    /**
     * Metodo che, presa in input la connessione ad un Database ed il nome di
     * una tabella in formato String, stampa a schermo i nomi di tutte le colonne
     * presenti in quella tabella 
     * @param conn La connessione al DataBase
     * @param tableName Il nome esatto della tabella
     * @throws SQLException 
     * @see java.sql.DatabaseMetaData#getColumns(java.lang.String, java.lang.String, java.lang.String, java.lang.String) 
     */
	public void printTableColumnsNames(Connection conn, String tableName) throws SQLException{
		//memorizziamo nell'oggetto dbmb le meta-informazioni sul database
		DatabaseMetaData dbmd = conn.getMetaData();
		
		String catalog = null;
		String schemaPattern = null;
		String tableNamePattern = tableName;
		String columnNamePattern = null;
		
		ResultSet rs =  dbmd.getColumns(catalog, schemaPattern,
												tableNamePattern, columnNamePattern);
		System.out.println("Columns of " + tableName + ":");
		while (rs.next()) {
			String columnName = rs.getString(4); // il nome della colonna
			System.out.println(columnName);
		}
	}

}
