package org.vigneshb.restaurants.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.vigneshb.restaurants.constants.SQLConstants;

public class Connectivity {

	public Connection connect(Connection connection) throws SQLException, ClassNotFoundException {// connects to the
																									// database
		try {
			if (connection == null) {
				DataSource dataSource = null;
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:comp/env");
				dataSource = (DataSource) envContext.lookup("jdbc/restaurantDB");
				connection = dataSource.getConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public Connection close(Connection connection, ResultSet resultSet, PreparedStatement statement,
			PreparedStatement statement1) throws SQLException {// closes the database
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (statement1 != null) {
			statement1.close();
		}
		if (connection != null) {
			connection.close();
			connection = null;
		}
		return connection;
	}
}
