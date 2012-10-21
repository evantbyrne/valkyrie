package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.Model;
import com.beakerstudio.valkyrie.ForeignKey;;

/**
 * Article Test Model
 * @author Evan Byrne
 */
public class Article extends Model {
	
	@Column(primary=true)
	public Integer id;
	
	@Column
	public String name;
	
	@Column(type="com.beakerstudio.valkyrie.test.models.Category")
	public ForeignKey<Category> category;

}