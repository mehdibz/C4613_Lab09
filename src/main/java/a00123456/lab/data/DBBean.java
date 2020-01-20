package a00123456.lab.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class DBBean {
	private String queryString = "empty";
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet queryResults = null;
	
	private DataSource dataSource;
	
	public DBBean( DataSource ds ) {
		this.dataSource = ds;
	}
	
	
	public DBBean() {
	}

	
	public void connect() throws SQLException {
		connection = dataSource.getConnection();
	}
	
	public void connect(String driver, String url, String user, String pass) {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String qs) {

		queryString = qs;
	}

	public void generateResultSet() {
		try {
			statement = connection.createStatement();
			queryResults = statement.executeQuery(queryString);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void setResultSet(ResultSet rs) {
		queryResults = rs;
	}
	
	public ResultSet getResultSet() {
		return queryResults;
	}

	
	public void cleanUp() {
		try {
			connection.close();
			statement.close();
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}
}
