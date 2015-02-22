package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Location.Location;

public class LocationSQL {
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://csc394.cpqprb9r3nly.us-west-2.rds.amazonaws.com/CSC394";
	private final static String USER = "awsCSC";
	private final static String PASS = "thisisremoteroot";

	private static Connection conn = null;
	private static boolean driverLoaded = false;
	/**
	 * loads in the driver for a MySQL server.
	 * @throws ClassNotFoundException
	 */
	private static void loadDriver() throws ClassNotFoundException{
		Class.forName(JDBC_DRIVER);
	}
	/**
	 * Connects to a MySQL server
	 * @throws SQLException
	 */
	private static void connect() throws SQLException{
		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	}
	/**
	 * Takes a string of coordinates as input. It parses the string for the first and last coordinate pair.
	 * then inserts the first pair, the last pair, and the string of coordinates in the the Location table in the
	 * database.
	 * @param coords
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertLocation(Location coords) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		connect();
		String[] split = coords.toString().split("\\s");
		String first = split[0];
		String last = split[split.length - 1];
		Statement stmt = null;
		stmt = conn.createStatement();
		String points = coords.toString();
		if (points.length() > 250) {
			points = points.substring(0, 249);
		}
		
		String sql = "INSERT INTO Location VALUES (" + "'" + first + "'" + "," + "'" + last + "'" + "," + "'" + points + "'" + ");";
		boolean rs = stmt.execute(sql);
		stmt.close();
		conn.close();
	}
	/**
	 * Checks to see if a string of coordinates has already been inserted in the database. 
	 * @param coords
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean queryLocation(Location coords) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Coordinates FROM Location WHERE Coordinates= " + "'" + coords + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String match = null;
		while(rs.next()){
			match = rs.getString("Coordinates");
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

}
