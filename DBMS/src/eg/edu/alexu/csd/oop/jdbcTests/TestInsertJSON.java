package eg.edu.alexu.csd.oop.jdbcTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import eg.edu.alexu.csd.oop.jdbc.Driver;

public class TestInsertJSON {
	private static String protocol = "altdb";
	private static String temp = System.getProperty("java.io.tmpdir");

	private Connection createUseDatabase(String databaseName) throws SQLException {
		Driver driver = new Driver();
		Properties info = new Properties();
		File dbDir = new File(temp + "/jdbc/" + Math.round((((float) Math.random()) * 100000)));
		info.put("path", dbDir.getAbsoluteFile());

		Connection connection = driver.connect("jdbc:" + protocol + "://localhost", info);
		Statement statement = connection.createStatement(); 	
		statement.execute("CREATE DATABASE " + databaseName); 
		statement.execute("USE " + databaseName); 
		statement.close();
		return connection;
	}

	 @Test 
		public void testInsertWithoutColumnNames() throws SQLException {
			Connection connection = createUseDatabase("Home");
			try {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE Family(name varchar, age int, weight float)");
				int count = statement.executeUpdate("INSERT INTO Family VALUES ('Ahmed', 10, 1.4)");
				Assert.assertEquals("Insert returned a number != 1", 1, count);
				statement.close();
			} catch (SQLException e) {
			}
			connection.close();
		}
	 @Test 
		public void testInsertWithColumnNames() throws SQLException {
			Connection connection = createUseDatabase("Company");
			try {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE Employee(name varchar, id int, numberOfHours float)");
				int count = statement.executeUpdate("INSERT INTO Employee (name, numberOfHours, id) VALUES ('Ahmed', 8.3, 3)");
				Assert.assertEquals("Insert returned a number != 1", 1, count);
				statement.close();
			} catch (SQLException e) {
			}
			connection.close();
		}
	 @Test 
		public void testInsertWithWrongColumnNames() throws SQLException {
			Connection connection = createUseDatabase("School");
			try {
				Statement statement = connection.createStatement();
				statement.execute("CREATE TABLE Student(subject varchar, Totalgrade int, lecturer varchar)");
				statement.executeUpdate(
						"INSERT INTO Student(Name, lecturer, Totalgrade) VALUES ('english', 'Mohamed', 60)");
				Assert.fail("Inserted with invalid column name!!");
				statement.close();
			} catch (SQLException e) {
			}
			connection.close();
		}

}
