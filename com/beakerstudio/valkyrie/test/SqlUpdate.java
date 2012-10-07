package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.Update;

/**
 * SQL Update Tests
 * @author Evan Byrne
 */
public class SqlUpdate {
	
	/**
	 * Test Where
	 */
	@Test
	public void test_basic() {
		
		Update u = new Update("mytable");
		u.set("name", "Evan");
		assertEquals("UPDATE \"mytable\" SET \"name\" = ?", u.build().sql());
		assertTrue(u.params().indexOf("Evan") == 0);
		
		u.set("bio", "I like bacon.");
		assertEquals("UPDATE \"mytable\" SET \"name\" = ?, \"bio\" = ?", u.build().sql());
		assertTrue(u.params().indexOf("Evan") == 0);
		assertTrue(u.params().indexOf("I like bacon.") == 1);
		
		u.eql("id", "123");
		assertEquals("UPDATE \"mytable\" SET \"name\" = ?, \"bio\" = ? WHERE \"mytable\".\"id\" = ?", u.build().sql());
		assertTrue(u.params().indexOf("Evan") == 0);
		assertTrue(u.params().indexOf("I like bacon.") == 1);
		assertTrue(u.params().indexOf("123") == 2);
		
	}

}
