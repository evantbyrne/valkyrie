package com.beakerstudio.valkyrie;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Vector;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.beakerstudio.valkyrie.sql.Column;
import com.beakerstudio.valkyrie.sql.CreateTable;
import com.beakerstudio.valkyrie.sql.Delete;
import com.beakerstudio.valkyrie.sql.DropTable;
import com.beakerstudio.valkyrie.sql.Insert;
import com.beakerstudio.valkyrie.sql.IntegerColumn;
import com.beakerstudio.valkyrie.sql.Select;
import com.beakerstudio.valkyrie.sql.TextColumn;
import com.beakerstudio.valkyrie.sql.Update;

/**
 * Model Class
 * @author Evan Byrne
 */
public abstract class Model {

	/**
	 * Columns
	 */
	protected static LinkedHashMap<Class<?>, LinkedHashMap<String, Column>> columns = new LinkedHashMap<Class<?>, LinkedHashMap<String, Column>>();
	
	/**
	 * Primary Keys
	 */
	protected static LinkedHashMap<Class<?>, String> primary_keys = new LinkedHashMap<Class<?>, String>();
	
	/**
	 * Column
	 * @param Class<?>
	 * @param Column
	 * @return this
	 */
	public static void add_column(Class<?> klass, Column column) {
		
		columns.get(klass).put(column.get_name(), column);
		
	}
	
	/**
	 * Get Column
	 * @param Class<?>
	 * @param String Column name
	 * @return Column
	 */
	public static Column get_column(Class<?> klass, String name) {
		
		return columns.get(klass).get(name);
		
	}
	
	/**
	 * Constructor
	 */
	public Model() {
		
		Class<?> klass = this.getClass();
		
		// Populate schema?
		boolean build_schema = (!columns.containsKey(klass));
		if(build_schema) {
		
			columns.put(klass, new LinkedHashMap<String, Column>());
		
		}
			
		for(Field f : klass.getDeclaredFields()) {
			
			if(f.isAnnotationPresent(com.beakerstudio.valkyrie.Column.class)) {
				
				com.beakerstudio.valkyrie.Column annotation = f.getAnnotation(com.beakerstudio.valkyrie.Column.class);
				String t = f.getType().getSimpleName();
				
				// Populate foreign keys
				if(t.equals("ForeignKey")) {
					
					add_column(klass, new IntegerColumn(f.getName()));
					
					try {
						
						Class <?> ft = f.getType();
						f.set(this, ft.newInstance());
				
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				// Populate schema?
				if(build_schema) {
				
					// Integer
					if(t.equals("Integer") || t.equals("ForeignKey")) {
						
						add_column(klass, new IntegerColumn(f.getName()));
						
					// String
					} else if(t.equals("String")) {
						
						add_column(klass, new TextColumn(f.getName()));
						
					}
					
					
					// Primary key
					if(annotation.primary()) {
						
						primary_keys.put(klass, f.getName());
						
					}
				
				}
				
			}
			
		}
		
	}
	
	/**
	 * SQLite Table
	 * @return String
	 */
	public String sqlite_table() {
		
		return getClass().getSimpleName().toLowerCase();
		
	}
	
	/**
	 * Create Table
	 * @return this
	 * @throws SQLiteException
	 * @throws Exception
	 */
	public Model create_table() throws SQLiteException, Exception {
		
		CreateTable t = new CreateTable(this.sqlite_table());
		Class<?> klass = this.getClass();
		for(String col_name : columns.get(klass).keySet()) {
			
			t.add_column(get_column(klass, col_name));
			
		}
		
		Connection.get().execute(t.build().sql());
		
		return this;
		
	}
	
	/**
	 * Drop Table
	 * @return this
	 * @throws SQLiteException
	 * @throws Exception
	 */
	public Model drop_table() throws SQLiteException, Exception {
		
		DropTable t = new DropTable(this.sqlite_table());
		Connection.get().execute(t.build().sql());
		
		return this;
		
	}
	
	/**
	 * Get Value of Primary Key
	 * @return String Name of primary key column
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public Object get_pk() throws Exception {
		
		Class<?> klass = this.getClass();
		return klass.getField(primary_keys.get(klass)).get(this);
		
	}
	
	/**
	 * Set Value of Primary Key
	 * @param Object Value
	 * @return String Name of primary key column
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @return this
	 */
	public Model set_pk(Object value) throws Exception {
		
		Class<?> klass = this.getClass();
		klass.getField(primary_keys.get(klass)).set(this, value);
		
		return this;
		
	}
	
	/**
	 * Insert
	 * @return this
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public Model insert() throws SQLiteException, Exception {
		
		Insert ins = new Insert(this.sqlite_table());
		
		// Build where
		Class<?> klass = this.getClass();
		for(String col : columns.get(klass).keySet()) {
			
			try {
				
				Field f = klass.getDeclaredField(col);
				
				// Foreign keys
				if(f.getType().getSimpleName().equals("ForeignKey")) {
				
					ForeignKey<?> value = (ForeignKey<?>) f.get(this);
					if(value.model != null) {
						
						ins.set(col, value.model.get_pk().toString());
						
					}
					
				// Other columns
				} else {
					
					Object value = f.get(this);
					if(value != null) {
						
						ins.set(col, value.toString());
						
					}
					
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Insert row
		ins.build();
		SQLiteStatement st = Connection.get().prepare(ins.sql(), ins.params());
		st.step();
		
		return this;
		
	}
	
	/**
	 * Select
	 * @return Select
	 */
	public Select select() {
		
		return new Select(this.sqlite_table(), this.getClass());
		
	}
	
	/**
	 * Get
	 * @return this
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public <T> Model get() throws SQLiteException, Exception {
		
		Class<?> klass = this.getClass();
		Select s = this.select().limit(1);
		
		// Build where
		for(String col : columns.get(klass).keySet()) {
			
			try {
				
				Field f = klass.getDeclaredField(col);
				
				// Foreign keys
				if(f.getType().getSimpleName().equals("ForeignKey")) {
				
					ForeignKey<?> value = (ForeignKey<?>) f.get(this);
					if(value != null && value.model != null) {
						
						s.eql(col, value.model.toString());
						
					}
					
				// Other columns
				} else {
					
					Object value = f.get(this);
					if(value != null) {
						
						s.eql(col, value.toString());
						
					}
					
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Fetch row
		Vector<T> res = s.fetch();
		if(res.size() == 1) {
			
			T row = res.firstElement();
			
			// Populate model object
			for(String col : columns.get(klass).keySet()) {
				
				Field col_field = klass.getField(col);
				col_field.set(this, col_field.get(row));
				
			}
			
		}
		
		return this;
		
	}
	
	/**
	 * Delete
	 * @return this
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public Model delete() throws SQLiteException, Exception {
		
		Delete d = new Delete(this.sqlite_table());
		
		// Build where
		Class<?> klass = this.getClass();
		for(String col : columns.get(klass).keySet()) {
			
			try {
				
				Field f = klass.getDeclaredField(col);
				
				// Foreign keys
				if(f.getType().getSimpleName().equals("ForeignKey")) {
				
					ForeignKey<?> value = (ForeignKey<?>) f.get(this);
					if(value != null && value.model != null) {
						
						d.eql(col, value.model.get_pk().toString());
						
					}
					
				// Other columns
				} else {
					
					Object value = f.get(this);
					if(value != null) {
						
						d.eql(col, value.toString());
						
					}
					
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Delete row
		d.build();
		SQLiteStatement st = Connection.get().prepare(d.sql(), d.params());
		st.step();
		
		return this;
		
	}
	
	/**
	 * Save
	 * @return this
	 * @throws Exception 
	 * @throws SQLiteException 
	 */
	public Model save() throws SQLiteException, Exception {
		
		Update u = new Update(this.sqlite_table());
		Class<?> klass = this.getClass();
		String primary_key = primary_keys.get(klass);
		
		for(String col : columns.get(klass).keySet()) {
			
			try {
				
				Field f = klass.getDeclaredField(col);
				
				// Primary key
				if(col == primary_key) {
					
					Object value = f.get(this);
					if(value != null) {
					
						u.eql(col, value.toString());
						
					}
				
				// Value to be updated
				} else {
				
					// Foreign keys
					if(f.getType().getSimpleName().equals("ForeignKey")) {
					
						ForeignKey<?> value = (ForeignKey<?>) f.get(this);
						if(value != null && value.model != null) {
							
							u.set(col, value.model.get_pk().toString());
							
						}
						
					// Other columns
					} else {
						
						Object value = f.get(this);
						if(value != null) {
							
							u.set(col, value.toString());
							
						}
						
					}
					
				}	
				
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// Update row
		u.build();
		SQLiteStatement st = Connection.get().prepare(u.sql(), u.params());
		st.step();
		
		return this;
		
	}
	
}
