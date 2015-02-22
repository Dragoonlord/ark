package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserSQL {
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://csc394.cpqprb9r3nly.us-west-2.rds.amazonaws.com/CSC394";
	private final static String USER = "awsCSC";
	private final static String PASS = "thisisremoteroot";

	private static Connection conn = null;
	private static boolean driverLoaded = false;
	/**
	 * Connects to a MySQL server.
	 * @throws SQLException
	 */
	private static void connect() throws SQLException{
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	}
	/**
	 * loads in the driver for a MySQL server.
	 * @throws ClassNotFoundException
	 */
	private static void loadDriver() throws ClassNotFoundException{
		Class.forName(JDBC_DRIVER);
	}
	/**
	 * Checks to see if a user has already registered to use the program. If they have the function returns true. else it returns false.
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean checkForUser(String name) throws SQLException, ClassNotFoundException {
		if(!driverLoaded){
			loadDriver();
		}
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT User FROM Users WHERE User= " + "'" + name + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String match = null;
		while(rs.next()){
			match = rs.getString("User");
		}
		rs.close();
		stmt.close();
		conn.close();
		if(match == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Checks to see if a user and their corresponding password exists within the database.
	 * @param pass
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean checkForPassword(String pass, String name) throws SQLException, ClassNotFoundException {
		if(!driverLoaded){
			loadDriver();
		}
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Password FROM Users WHERE User= " + "'" + name + "' AND Password= "  + "'" + pass + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String match = null;
		while(rs.next()){
			match = rs.getString("Password");
		}
		rs.close();
		stmt.close();
		conn.close();
		if(match == null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Adds a new user to the Users table in the database.
	 * @param name
	 * @param pass
	 * @param firstName
	 * @param lastName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void addNewUser(String email, String pass, String firstName, String lastName) throws ClassNotFoundException, SQLException {
		if(!driverLoaded){
			loadDriver();
		}
		connect();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql = "INSERT INTO Users VALUES (" + "'" + email + "'" + "," + "'" + pass + "'" + "," + "'" + firstName + "'" + "," + "'" + lastName + "'" + ");";
		boolean rs = stmt.execute(sql);
		stmt.close();
		conn.close();
	}
	
	public static void updateUser(String email, String pass, String firstName, String lastName) throws ClassNotFoundException, SQLException {
		if(!driverLoaded){
			loadDriver();
		}
		connect();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sql = "UPDATE Users SET Password='" + pass + "', Firstname='" + firstName + "', Lastname='" + lastName + "' WHERE User='" + email + "';";
		boolean rs = stmt.execute(sql);
		stmt.close();
		conn.close();
	}
	
}

