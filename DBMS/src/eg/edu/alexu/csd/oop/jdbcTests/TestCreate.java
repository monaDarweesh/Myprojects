package eg.edu.alexu.csd.oop.jdbcTests;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;

import eg.edu.alexu.csd.oop.jdbc.Driver;


public class TestCreate {

	private String protocol = "xmldb";
	private String tmp = System.getProperty("java.io.tmpdir");
	private Connection createUseDatabase(String databaseName) throws SQLException {
		Driver driver = new Driver();
		Properties info = new Properties();
		File dbDir = new File(tmp + "/jdbc/" + Math.round((((float) Math.random()) * 100000)));
		info.put("path", dbDir.getAbsoluteFile());

		Connection connection = driver.connect("jdbc:" + protocol + "://localhost", info);
		Statement statement = connection.createStatement(); 	
		statement.execute("CREATE DATABASE " + databaseName); 
		statement.execute("USE " + databaseName); 
		statement.close();
		return connection;
	}
	@Test //
	public void testCreateTable() throws SQLException {
		Connection connection = createUseDatabase("School");
		try {
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE Student(Name varchar, Id int, DateOfBirth date)");
			statement.close();
		} catch (SQLException e) {
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE Student(Name varchar, Id int, DateOfBirth date)");
			Assert.fail("Created existing table successfully!");
		} catch (SQLException e) {
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE incomplete_Student");
			Assert.fail("Create invalid table succeed");
		} catch (SQLException e) {
		}
		connection.close();
	}
}