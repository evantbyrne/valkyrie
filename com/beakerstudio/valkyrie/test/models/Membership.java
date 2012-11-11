package com.beakerstudio.valkyrie.test.models;

import com.beakerstudio.valkyrie.Column;
import com.beakerstudio.valkyrie.ForeignKey;
import com.beakerstudio.valkyrie.Model;

/**
 * Membership Test Model
 * @author Evan Byrne
 */
public class Membership extends Model {

	@Column(type="com.beakerstudio.valkyrie.test.models.User")
	public ForeignKey<User> user;
	
	@Column(type="com.beakerstudio.valkyrie.test.models.Group")
	public ForeignKey<Group> group;
	
}
