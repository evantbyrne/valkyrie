package com.beakerstudio.valkyrie;

import java.io.File;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

/**
 * Connection Class
 * @author Evan Byrne
 */
public class Connection {
	
	/**
	 * Single shared instance
	 */
	private static Connection instance;
	
	/**
	 * Open
	 * @param String SQLite database name
	 */
	public static void open(String database) {
		
		if(instance != null) {
			
			Connection.close();
			
		}
		
		instance = new Connection(database);
		
		
	}
	
	/**
	 * Close
	 */
	public static void close() {
		
		instance.close_connection();
		
	}
	
	/**
	 * Get
	 * @return Connection
	 */
	public static Connection get() throws Exception {
		
		if(instance == null) {
			
			throw new Exception("Connection must be opened with Connection.open() before Connection.get() can be used.");
			
		}
		
		return instance;
		
	}
	
	
	
	/**
	 * SQLite connection
	 */
	private SQLiteConnection sqlite;
	
	/**
	 * Constructor
	 * @throws SQLiteException 
	 */
	protected Connection(String database) {
		
		this.sqlite = new SQLiteConnection(new File(database));
	    try {
			this.sqlite.open(true);
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Close Connection
	 */
	public void close_connection() {
		
		this.sqlite.dispose();
		
	}
	
}
