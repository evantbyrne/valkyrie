package com.beakerstudio.valkyrie.sql;

/**
 * Text Column Class
 * @author Evan Byrne
 */
public class TextColumn extends Column {
	
	/**
	 * @inheritdoc
	 */
	public TextColumn(String name) {
		
		super(name);
		
	}

	/**
	 * @inheritdoc
	 */
	public String build() {
		
		return String.format("`%s` TEXT", this.get_name());
		
	}

}
