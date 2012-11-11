package com.beakerstudio.valkyrie;

import java.lang.reflect.Field;

import com.beakerstudio.valkyrie.sql.Select;

/**
 * Many To Many
 * @author Evan Byrne
 * @param <T extends Model>
 */
public class ManyToMany <T extends Model> {
	
	/**
	 * String Child model class
	 */
	protected String child_model_class;
	
	/**
	 * Model Parent model
	 */
	protected Model parent_model;
	
	/**
	 * Model Middleman model
	 */
	protected Model middleman_model;
	
	/**
	 * Constructor
	 */
	public ManyToMany() {
		
		this.child_model_class = null;
		this.parent_model = null;
		this.middleman_model = null;
		
	}
	
	/**
	 * Set Child Model Class
	 * @param String Canonical class name
	 * @return this
	 */
	public ManyToMany<T> set_child_model_class(String name) {
		
		this.child_model_class = name;
		return this;
		
	}
	
	/**
	 * Set Parent Model
	 * @param Model Parent model
	 * @return this
	 */
	public ManyToMany<T> set_parent_model(Model parent_model) {
		
		this.parent_model = parent_model;
		return this;
		
	}
	
	/**
	 * Set Middleman Model
	 * @param Model Middleman model
	 * @return this
	 */
	public ManyToMany<T> set_middleman_model(Model middleman_model) {
		
		this.middleman_model = middleman_model;
		return this;
		
	}
	
	/**
	 * Add
	 * @return <T> Model to be added
	 * @throws Exception
	 */
	public ManyToMany<T> add(T model) throws Exception {
		
		Model middleman = this.middleman_model.getClass().newInstance();
		
		
		// Get foreign key field for associated model
		Field fk = middleman.getClass().getField(model.getClass().getSimpleName().toLowerCase());
		ForeignKey<?> fk_field = (ForeignKey<?>) fk.get(middleman);
		
		// Set parent model on foreign key for associated model
		fk_field.getClass().getField("model").set(fk_field, model);
		
		
		// Get foreign key field for parent model
		fk = middleman.getClass().getField(this.parent_model.getClass().getSimpleName().toLowerCase());
		fk_field = (ForeignKey<?>) fk.get(middleman);
		
		// Set parent model on foreign key for parent model
		fk_field.getClass().getField("model").set(fk_field, this.parent_model);
		
		
		middleman.insert();
		return this;
		
	}
	
	/**
	 * Select
	 * @return Select
	 * @throws Exception
	 */
	public Select select() throws Exception {
		
		Model child_model = (Model) Class.forName(this.child_model_class).newInstance();
		String child_name = child_model.getClass().getSimpleName().toLowerCase();
		Select s = child_model.select().join(
				this.middleman_model.getClass().getCanonicalName(),
				child_name,
				child_model.get_pk_name());
		
		return s.eql(
				String.format("%s.%s", this.middleman_model.sqlite_table(), this.parent_model.getClass().getSimpleName().toLowerCase()),
				this.parent_model.get_pk().toString());
		
	}

}
