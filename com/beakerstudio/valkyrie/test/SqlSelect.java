package com.beakerstudio.valkyrie.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.beakerstudio.valkyrie.sql.Select;

/**
 * SQL Select Tests
 * @author Evan Byrne
 */
public class SqlSelect {

	/**
	 * Test All
	 */
	@Test
	public void test_all() {
		
		Select s = new Select("mytable");
		assertEquals("SELECT * FROM \"mytable\"", s.build().sql());
		
	}
	
	/**
	 * Test Where
	 */
	@Test
	public void test_where() {
		
		Select s = new Select("mytable");
		s.eql("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.not("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" <> ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.lt("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" < ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.lte("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" <= ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.gt("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" > ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.gte("id", "123");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" >= ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		// AND
		s.eql("name", "Evan");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" >= ? AND \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		assertTrue(s.params().indexOf("Evan") == 1);
		
		// OR
		s = new Select("mytable");
		s.eql("id", "321").or().eql("name", "Byrne");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"id\" = ? OR \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("321") == 0);
		assertTrue(s.params().indexOf("Byrne") == 1);
		
	}
	
	/**
	 * Test Limit
	 */
	@Test
	public void test_limit() {
		
		Select s = new Select("mytable");
		s.limit(1);
		assertEquals("SELECT * FROM \"mytable\" LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("1") == 0);
		
		s.limit(5);
		assertEquals("SELECT * FROM \"mytable\" LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("5") == 0);
		
		s.eql("foo", "bar");
		assertEquals("SELECT * FROM \"mytable\" WHERE \"mytable\".\"foo\" = ? LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("bar") == 0);
		assertTrue(s.params().indexOf("5") == 1);
		
	}

}
