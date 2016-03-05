package it.surveys.util;

import java.util.Properties;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
* La classe Config legge i dati di configurazione del database da un file "db.conf"
* che si trova nella root del progetto.
* @author Luca Talocci, Lorenzo Bernabei
* @version 1.0 04/03/2016
*/
public class Config {

	/** Configuration data */
	protected static Properties data;
	
	/** new properties */
	protected static Properties newData;
	
	/** Configuration file */
	protected static InputStream configfile;
	
	/** 
 	 * Il metodo load(String filename) ha il compito principale di aprire il file di configuazione
 	 * e di leggerne i campi costituiti da key e value.
 	 * @param String nome del file di configurazione
 	 * @return boolean esito della lettura
 	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void load() throws IOException, ClassNotFoundException{
		
		BufferedReader r = null;
		boolean comment=false;
		boolean singlecomm=false;
		String line;
			
		configfile = Config.class.getResourceAsStream("db.conf");
		data = new Properties();
		newData = new Properties();
		
		r = new BufferedReader(new InputStreamReader(configfile,"UTF-8"));
		while((line=r.readLine()) != null) {
			if(line.startsWith("//") || line.startsWith("#") || line.length()==0) {
				singlecomm=true;
				comment=true;
			}
			if(line.startsWith("/*")) {
				singlecomm=false;
				comment=true;
			}
			if(!comment) {
				StringTokenizer t=new StringTokenizer(line,"=");
				String key = t.nextToken();
				String value;
				if(t.hasMoreElements())
					value = t.nextToken();
				else
					value = "";
				data.setProperty(key, value);
			}
			if(line.endsWith("*/") || singlecomm) {
				comment = false;
			}
		}
	}

	/** Return properties */
	public static String getProperty(String key) {

		return data.getProperty(key);

	}

	/** Set properties */
	public static void setProperty(String key, String value) {

		if(data.getProperty(key) == null) {
			newData.setProperty(key,value);
		} else {
			data.setProperty(key,value);
		}

	}
	
}

