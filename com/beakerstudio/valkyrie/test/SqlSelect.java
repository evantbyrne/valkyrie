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
		assertEquals("SELECT \"mytable\".* FROM \"mytable\"", s.build().sql());
		
	}
	
	/**
	 * Test Join
	 */
	@Test
	public void test_join() {
		
		Select s = new Select("mytable");
		s.join("com.beakerstudio.valkyrie.test.models.Article", "mytable_id", "id");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" JOIN \"article\" ON \"mytable\".\"id\" = \"article\".\"mytable_id\"", s.build().sql());
		
	}
	
	/**
	 * Test Where
	 */
	@Test
	public void test_where() {
		
		Select s = new Select("mytable");
		s.eql("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.not("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" <> ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.lt("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" < ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.lte("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" <= ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.gt("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" > ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		s = new Select("mytable");
		s.gte("id", "123");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" >= ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		
		// AND
		s.eql("name", "Evan");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" >= ? AND \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("123") == 0);
		assertTrue(s.params().indexOf("Evan") == 1);
		
		// OR
		s = new Select("mytable");
		s.eql("id", "321").or().eql("name", "Byrne");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"id\" = ? OR \"mytable\".\"name\" = ?", s.build().sql());
		assertTrue(s.params().indexOf("321") == 0);
		assertTrue(s.params().indexOf("Byrne") == 1);
		
	}
	
	/**
	 * Test Order
	 */
	@Test
	public void test_order() {
		
		Select s = new Select("mytable");
		s.order_asc("foo");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" ORDER BY \"foo\" ASC", s.build().sql());
		
		s.order_desc("bar");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" ORDER BY \"foo\" ASC, \"bar\" DESC", s.build().sql());
		
	}
	
	/**
	 * Test Limit
	 */
	@Test
	public void test_limit() {
		
		Select s = new Select("mytable");
		s.limit(1);
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("1") == 0);
		
		s.limit(5);
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("5") == 0);
		
		s.eql("foo", "bar");
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"foo\" = ? LIMIT ?", s.build().sql());
		assertTrue(s.params().indexOf("bar") == 0);
		assertTrue(s.params().indexOf("5") == 1);
		
		s.offset(10);
		assertEquals("SELECT \"mytable\".* FROM \"mytable\" WHERE \"mytable\".\"foo\" = ? LIMIT ? OFFSET ?", s.build().sql());
		assertTrue(s.params().indexOf("bar") == 0);
		assertTrue(s.params().indexOf("5") == 1);
		assertTrue(s.params().indexOf("10") == 2);
		
	}

}
