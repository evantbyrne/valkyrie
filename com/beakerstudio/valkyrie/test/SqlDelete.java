package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.Delete;

/**
 * SQL Delete Tests
 * @author Evan Byrne
 */
public class SqlDelete {

	/**
	 * Test All
	 */
	@Test
	public void test_all() {
		
		Delete s = new Delete("mytable");
		assertEquals("DELETE FROM \"mytable\"", s.build().sql());
		
	}
	
	/**
	 * Test Where
	 */
	@Test
	public void test_where() {
		
		Delete s = new Delete("mytable");
		s.eql("id", "123");
		assertEquals("DELETE FROM \"mytable\" WHERE \"mytable\".\"id\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		// AND
		s.eql("name", "Evan");
		assertEquals("DELETE FROM \"mytable\" WHERE \"mytable\".\"id\" = ? AND \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		assertTrue(s.params().indexOf("Evan") == 1);
		
		// OR
		s = new Delete("mytable");
		s.eql("id", "321").or().eql("name", "Byrne");
		assertEquals("DELETE FROM \"mytable\" WHERE \"mytable\".\"id\" = ? OR \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("321") == 0);
		assertTrue(s.params().indexOf("Byrne") == 1);
		
	}

}
