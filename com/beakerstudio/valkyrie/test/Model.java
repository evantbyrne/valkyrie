package com.beakerstudio.valkyrie.test;

import java.util.Vector;

import static org.junit.Assert.*;
import org.junit.Test;

import com.almworks.sqlite4java.SQLiteException;
import com.beakerstudio.valkyrie.Connection;
import com.beakerstudio.valkyrie.sql.TextColumn;
import com.beakerstudio.valkyrie.sql.IntegerColumn;
import com.beakerstudio.valkyrie.test.models.Foo;
import com.beakerstudio.valkyrie.test.models.FooBar;

public class Model {

	/**
	 * Test SQLite Table
	 */
	@Test
	public void test_sqlite_table() {
		
		Foo f = new Foo();
		assertEquals("foo", f.sqlite_table());
		
		FooBar fb = new FooBar();
		assertEquals("foobar", fb.sqlite_table());
		
	}
	
	/**
	 * Test Column Name
	 */
	@Test
	public void test_column_name() {
		
		TextColumn bar = new TextColumn("bar");
		assertEquals("bar", bar.get_name());
		
	}
	
	/**
	 * Test Column Builds
	 */
	@Test
	public void test_column_builds() {
		
		// Integer
		IntegerColumn _int = new IntegerColumn("foo");
		assertEquals("\"foo\" INTEGER", _int.build());
		
		// Text
		TextColumn _text = new TextColumn("foo");
		assertEquals("\"foo\" TEXT", _text.build());
		
	}
	
	/**
	 * Test Insert, Get, and Delete
	 * @throws Exception
	 */
	@Test
	public void test_insert_get_and_delete() throws Exception {
		
		Connection.open("testdb");
		FooBar f1 = new FooBar();
		
		f1.create_table();
		f1.id = 123;
		f1.name = "Evan";
		f1.insert();
		
		FooBar f2 = new FooBar();
		f2.id = 123;
		f2.get();
		assertEquals("Evan", f2.name);
		
		f2.delete();
		FooBar f3 = new FooBar();
		f3.id = 123;
		f3.get();
		assertNull(f3.name);
		
		f1.drop_table();
		Connection.close();
		
	}
	
	/**
	 * Test Select
	 * @throws Exception
	 */
	@Test
	public void test_select() throws SQLiteException, Exception {
		
		Connection.open("testdb");
		FooBar f1 = new FooBar();
		
		f1.create_table();
		f1.id = 1;
		f1.name = "Foo";
		f1.insert();
		
		Vector<FooBar> res = f1.select().fetch();
		assertEquals(1, res.size());
		
		f1.id = 2;
		f1.name = "Bar";
		f1.insert();
		
		// All
		res = f1.select().fetch();
		assertEquals(2, res.size());
		
		// =
		res = f1.select().eql("name", "Foo").fetch();
		assertEquals(1, res.size());
		res = f1.select().eql("name", "Bob").fetch();
		assertEquals(0, res.size());
		
		// or
		res = f1.select().eql("name", "Bob").or().eql("name", "Bar").fetch();
		assertEquals(1, res.size());
		res = f1.select().eql("name", "Foo").or().eql("name", "Bob").fetch();
		assertEquals(1, res.size());
		res = f1.select().eql("name", "Foo").or().eql("name", "Bar").fetch();
		assertEquals(2, res.size());
		
		// <>
		res = f1.select().not("name", "Foo").fetch();
		assertEquals(1, res.size());
		res = f1.select().not("name", "Foo").not("name", "Bar").fetch();
		assertEquals(0, res.size());
		
		// <
		res = f1.select().lt("id", "1").fetch();
		assertEquals(0, res.size());
		res = f1.select().lt("id", "2").fetch();
		assertEquals(1, res.size());
		res = f1.select().lt("id", "3").fetch();
		assertEquals(2, res.size());
		
		// <=
		res = f1.select().lte("id", "1").fetch();
		assertEquals(1, res.size());
		res = f1.select().lte("id", "2").fetch();
		assertEquals(2, res.size());
		res = f1.select().lte("id", "3").fetch();
		assertEquals(2, res.size());
		
		// >
		res = f1.select().gt("id", "0").fetch();
		assertEquals(2, res.size());
		res = f1.select().gt("id", "1").fetch();
		assertEquals(1, res.size());
		res = f1.select().gt("id", "2").fetch();
		assertEquals(0, res.size());
		
		// >=
		res = f1.select().gte("id", "0").fetch();
		assertEquals(2, res.size());
		res = f1.select().gte("id", "1").fetch();
		assertEquals(2, res.size());
		res = f1.select().gte("id", "2").fetch();
		assertEquals(1, res.size());
		
		// Order
		res = f1.select().order_asc("name").fetch();
		assertEquals(2, res.size());
		assertEquals("Bar", res.firstElement().name);
		res = f1.select().order_desc("name").fetch();
		assertEquals(2, res.size());
		assertEquals("Foo", res.firstElement().name);
		res = f1.select().order_asc("name").order_desc("id").fetch();
		assertEquals(2, res.size());
		assertEquals("Bar", res.firstElement().name);
		
		f1.drop_table();
		Connection.close();
		
	}
	
	/**
	 * Test Update
	 * @throws Exception
	 */
	@Test
	public void test_update() throws Exception {
		
		Connection.open("testdb");
		FooBar f1 = new FooBar();
		
		f1.create_table();
		f1.id = 123;
		f1.name = "Evan";
		f1.insert();
		
		f1.name = "Bob";
		f1.save();
		
		FooBar f2 = new FooBar();
		f2.id = 123;
		f2.get();
		assertEquals("Bob", f2.name);
		
		f1.drop_table();
		Connection.close();
		
	}

}
