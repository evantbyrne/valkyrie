package com.beakerstudio.valkyrie;

import java.io.File;
import java.util.Vector;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

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
	
	/**
	 * Prepare
	 * @param String SQL
	 * @param Vector<String> Parameters to bind
	 * @return SQLiteStatement
	 * @throws SQLiteException
	 */
	public SQLiteStatement prepare(String sql, Vector<String> params) throws SQLiteException {
		
		SQLiteStatement st = this.sqlite.prepare(sql);
		
		if(params != null) {
		
			int i = 1;
			for(String param : params) {
				
				st.bind(i, param);
				i++;
				
			}
		
		}
		
		return st;
		
	}
	
	/**
	 * Prepare
	 * @param String SQL
	 * @return SQLiteStatement
	 * @throws SQLiteException
	 */
	public SQLiteStatement prepare(String sql) throws SQLiteException {
		
		return this.prepare(sql, null);
		
	}
	
	/**
	 * Execute
	 * @param String SQL
	 * @return this
	 * @throws SQLiteException
	 */
	public Connection execute(String sql) throws SQLiteException {
		
		this.sqlite.exec(sql);
		return this;
		
	}
	
}
