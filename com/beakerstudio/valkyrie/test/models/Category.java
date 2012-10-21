package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.Model;

/**
 * Category Test Model
 * @author Evan Byrne
 */
public class Category extends Model {
	
	@Column(primary=true)
	public Integer id;
	
	@Column
	public String name;

}
