package com.beakerstudio.valkyrie.test;

import java.io.File;
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
