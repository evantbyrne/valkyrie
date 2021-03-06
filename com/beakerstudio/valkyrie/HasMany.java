package com.beakerstudio.valkyrie;

import java.lang.reflect.Field;

import com.beakerstudio.valkyrie.sql.Select;

/**
 * Has Many
 * @author Evan Byrne
 * @param <T extends Model>
 */
public class HasMany <T extends Model> {
	
	/**
	 * Model Child model
	 */
	public Model child_model;
	
	/**
	 * Model Parent model
	 */
	protected Model parent_model;
	
	/**
	 * String Reference field name
	 */
	protected String field_name;
	
	/**
	 * Constructor
	 */
	public HasMany() {
		
		this.child_model = null;
		this.parent_model = null;
		this.field_name = null;
		
	}
	
	/**
	 * Set Child Model
	 * @param Model Foreign key model instance
	 * @return this
	 */
	public HasMany<T> set_child_model(Model model) {
		
		this.child_model = model;
		return this;
		
	}
	
	/**
	 * Set Field
	 * @param Object Reference field
	 * @return this
	 */
	public HasMany<T> set_field_name(String field_name) {
		
		this.field_name = field_name;
		return this;
		
	}
	
	/**
	 * Set Parent Model
	 * @param Model Parent model
	 * @return this
	 */
	public HasMany<T> set_parent_model(Model parent_model) {
		
		this.parent_model = parent_model;
		return this;
		
	}
	
	/**
	 * Insert
	 * @return <T> Model to be inserted
	 * @throws Exception
	 */
	public HasMany<T> insert(T model) throws Exception {
		
		// Get foreign key field
		Field fk = model.getClass().getField(this.field_name);
		ForeignKey<?> fk_field = (ForeignKey<?>) fk.get(model);
		
		// Set parent model on foreign key
		fk_field.getClass().getField("model").set(fk_field, this.parent_model);
		
		model.insert();
		return this;
		
	}
	
	/**
	 * Select
	 * @return Select
	 * @throws Exception
	 */
	public Select select() throws Exception {
		
		Select s = this.child_model.select();
		s.eql(this.field_name, this.parent_model.get_pk().toString());
		return s;
		
	}
	
	/**
	 * Delete
	 * @return <T> Model
	 * @throws Exception
	 */
	public HasMany<T> delete(T model) throws Exception {
		
		// Get foreign key field
		Field fk = model.getClass().getField(this.field_name);
		ForeignKey<?> fk_field = (ForeignKey<?>) fk.get(model);
		
		// Set parent model on foreign key
		fk_field.getClass().getField("model").set(fk_field, this.parent_model);
		
		model.delete();
		return this;
		
	}

}