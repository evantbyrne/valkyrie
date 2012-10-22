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
	public T model;
	
	/**
	 * Constructor
	 * @param <T> Foreign key model instance
	 * @param Integer Primary key
	 */
	public ForeignKey(T model) {
		
		this.set(model);
		
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
	public ForeignKey<T> set(T model) {
		
		this.model = model;
		return this;
		
	}
	
	/**
	 * Set
	 * @param Object Foreign key model instance
	 * @return this
	 */
	public ForeignKey<T> set(Object model) {
		
		this.model = (T) model;
		return this;
		
	}
	
	/**
	 * Get
	 * @return <T>
	 * @throws SQLiteException
	 * @throws Exception
	 */
	public T get() throws SQLiteException, Exception {
        
        this.model.get();
		return this.model;
		
	}

}
