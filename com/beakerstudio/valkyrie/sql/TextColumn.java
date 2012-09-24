package com.beakerstudio.valkyrie.sql;

/**
 * Text Column Class
 * @author Evan Byrne
 */
public class TextColumn extends Column {
	
	/**
	 * {@inheritDoc}
	 */
	public TextColumn(String name) {
		
		super(name);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public String build() {
		
		return String.format("\"%s\" TEXT", this.get_name());
		
	}

}
