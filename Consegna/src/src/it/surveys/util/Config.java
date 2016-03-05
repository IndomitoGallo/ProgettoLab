package it.surveys.util;

import java.util.Properties;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
* La classe Config, al momento del suo caricamento, legge i dati di configurazione del
* database da un file "db.conf" che si trova nella cartella "util". Per memorizzarli
* sfrutta un oggetto Properties che rappresenta un insieme di proprietà.
* @author Luca Talocci, Lorenzo Bernabei
* @version 1.0 04/03/2016
*/
public class Config {
	
	/* Blocco statico in modo da leggere una sola volta i dati da file
	 * invece che ad ogni connessione col DB. Quello che verrà fatto
	 * di volta in volta sarà solo la get della proprietà desiderata. 
	 */
	static {
		data = new Properties();
		load();
	}

	/** l'insieme dei dati di configurazione */
	private static Properties data;
	
	/** 
 	 * Il metodo load() ha il compito principale di aprire il file di configuazione
 	 * e di leggerne le righe costituite da coppie key e value separate da "=".
 	 * Inoltre inserisce queste coppie in un oggetto Properties che rappresenta un insieme
 	 * di proprietà.
	 */
	private static void load() {
		
		String line;			
		InputStream configfile = Config.class.getResourceAsStream("db.conf");
		BufferedReader inputStream = null;
		
		try {
			inputStream = new BufferedReader(new InputStreamReader(configfile,"UTF-8"));
			while((line=inputStream.readLine()) != null) {
					StringTokenizer t = new StringTokenizer(line,"=");
					String key = t.nextToken();
					String value;
					if(t.hasMoreElements())
						value = t.nextToken();
					else
						value = ""; //se il value è vuoto viene inserita una stringa vuota
					setProperty(key, value);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/** 
	 * Get del dato con chiave "key".
	 * @param key String la chiave che individua il dato
	 * @return String il dato
	 */
	public static String getProperty(String key) {
		return data.getProperty(key);
	}

	/** 
	 * Set del dato, quindi della coppia key e value.
	 * @param key String la chiave
	 * @param value String il dato
	 */
	private static void setProperty(String key, String value) {
		data.setProperty(key,value);
	}
	
}

