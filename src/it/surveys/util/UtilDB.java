package it.surveys.util;

import java.sql.*;

/**
 * L'utility di accesso al DataBase implementata come una factory con il pattern
 * singleton. Questa utility viene istanziata ed utilizzata dal model.
 * Nell'architettura dell'applicazione si pone tra il model e il JDBC.
 * @author Team Talocci
 * @version 3.0 13/12/2015
 */
public class UtilDB {
	
	private static UtilDB utl;
	
	/**
	 * Il metodo è l'implementazione del pattern singleton per avere una sola
	 * istanza dell'utility. Quando viene chiamato per la prima volta istanzia
	 * e ritorna un nuovo oggetto di tipo UtilDB, mentre le volte successive,
	 * ritorna l'oggetto che è stato istanziato la prima volta e che è stato
	 * salvato in un attrivuto privato e statico.
	 * @return utl l'oggetto singleton UtilDB 
	 */  
	public static UtilDB getUtilDB() {
		if(utl == null)
			utl = new UtilDB();
		return utl;
	}
	
	/**
	 * Il metodo createConnection() crea una connessione ad un DB qualsiasi
	 * caricandone innanzitutto il Driver e poi sfruttando il metodo privato
	 * createConnection(String, String, String). L'utilità di tutto questo sta
	 * nel fatto che in futuro cambiare il database a cui si accede è molto
	 * semplice. I riferimenti al database utilizzato sono soltanto in questo metodo.
	 * @return conn Connection to DB
	 */
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = createConnection("autori", "root", "aeg20e");
        return conn;
    }

	/**
	 * Il metodo crea una connessione con il DB
	 * attraverso la classe DriverManager gia' presente nel package java.sql.
	 * @param db String nome del database
	 * @param username String username dell'utente mySQL
	 * @param password String password dell'utente mySQL
	 * @return conn Connection to DB
	 * @throws SQLException 
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
	 * @param conn Connection to DB
	 * @return Statement
	 * @throws SQLException 
	 */
	public Statement createStatement(Connection conn) throws SQLException {
		return conn.createStatement();
	}

	/**
	 * Il metodo query ha il compito di eseguire una query sul DB, il cui risultato
	 * sara' un ResultSet dei record risultanti.
	 * @param conn Connessione al database
	 * @param qry String query scritta per esteso
	 * @return ResultSet insieme dei record risultanti dalla query qry
	 * @throws SQLException 
	 */
	public ResultSet query(Statement stmt, String qry) throws SQLException {
		return stmt.executeQuery(qry);
	}

	/**
	 * Il metodo manipulate gestisce le operazioni di Insert, Update e Delete
	 * sul DB.
	 * @param conn Connessione al database
	 * @param qry String query scritta per esteso
	 * @throws SQLException 
	 * @return int numero di righe coinvolte nella query
	 */
	public int manipulate(Statement stmt, String qry) throws SQLException {
		return stmt.executeUpdate(qry);
	}
	
	/**
	 * Il metodo closeStatement permette di chiudere il singolo 
	 * Statement passato in ingresso.
	 * @param stmt oggetto di tipo Statement
	 * @throws SQLException 
	 */
	public void closeStatement(Statement stmt) throws SQLException {
		stmt.close();
	}
	
	/**
	 * Il metodo closeConnection ha il compito di chiudere la connession al DB,
	 * cosa molto importante poiche' un DBMS puo' gestire soltanto un pool
	 * predefinito di connessioni in parallelo.
	 * @param conn Conncection to DB
	 * @throws SQLException 
	 */
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	/**
	 * Il metodo printResultSet stampa un resultSet indipendentemente dal ResultSet stesso.
	 * @param rs ResultSet qualunque
	 * @throws SQLException 
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
	 * Il metodo resultSetToString restituisce un ResultSet sotto forma di stringa,
	 * in modo che possa essere più facilmente gestito per la stampa da parte di 
	 * un'applicazione.
	 * @param rs oggetto di tipo ResultSet 
	 * @throws SQLException 
	 */
	public String resultSetToString(ResultSet rs) throws SQLException{
		String str = "<table><tr>";
		ResultSetMetaData resM=rs.getMetaData();
		int colonne= resM.getColumnCount();
		for(int i = 0; i < colonne; i++) {
			str = str + "<th>" + resM.getColumnName(i+1) + "</th>";
		}
		str = str + "</tr>";
		while(rs.next()){
			str = str + "<tr>";
			for(int i = 0; i < colonne; i++){
				str = str + "<td>" + rs.getString(i+1) + "</td>";
			}
			str = str + "</tr>";
		}
		return str + "</table>";
	}

	/**
	 * Stampa a schermo la lista di tutte le tabelle del db, una per riga
	 * @param conn Connection La connessione al DataBase
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
     * Metodo che, preso in input la connessione ad un Database ed il nome di
     * una tabella, restituisce un ResultSet contenente i nomi di tutte le colonne
     * presenti in quella tabella e li stampa a schermo
     * @param conn Connection La connessione al DataBase
     * @param tableName String il nome esatto della tabella
     * @throws SQLException 
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
			String columnName = rs.getString(4); // dà il nome della colonna
			System.out.println(columnName);
		}
	}

}
