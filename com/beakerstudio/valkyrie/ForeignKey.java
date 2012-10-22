package com.beakerstudio.valkyrie;

import com.almworks.sqlite4java.SQLiteException;

/**
 * Foreign Key
 * @author Evan Byrne
 * @param <T extends Model>
 */
public class ForeignKey <T extends Model> {
	
	/**
	 * <T> Foreign key model instance
	 */
	public T belongs_to;
	
	/**
	 * Constructor
	 * @param <T> Foreign key model instance
	 * @param Integer Primary key
	 */
	public ForeignKey(T belongs_to) {
		
		this.set(belongs_to);
		
	}
	
	/**
	 * Constructor
	 */
	public ForeignKey() {
		
		this(null);
		
	}
	
	/**
	 * Set
	 * @param <T> Foreign key model instance
	 * @return this
	 */
	public ForeignKey<T> set(T belongs_to) {
		
		this.belongs_to = belongs_to;
		return this;
		
	}
	
	/**
	 * Set
	 * @param Object Foreign key model instance
	 * @return this
	 */
	public ForeignKey<T> set(Object belongs_to) {
		
		this.belongs_to = (T) belongs_to;
		return this;
		
	}
	
	/**
	 * Get
	 * @return <T>
	 * @throws SQLiteException
	 * @throws Exception
	 */
	public T get() throws SQLiteException, Exception {
        
        this.belongs_to.get();
		return this.belongs_to;
		
	}

}
