package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import Location.Location;
import words.Word;

public class WordSQL {
	private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://csc394.cpqprb9r3nly.us-west-2.rds.amazonaws.com/CSC394";
	private final static String USER = "awsCSC";
	private final static String PASS = "thisisremoteroot";

	private static Connection conn = null;
	private static boolean driverLoaded = false;
	/**
	 * Connects to a MySQL server
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
	 * Checks to see if a word has been entered into the Words table in the Database.
	 * @param word
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean queryWord(Word word) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Word FROM Words WHERE Word= " + "'" + word + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String match = null;
		while(rs.next()){
			match = rs.getString("Word");
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
	 * Inserts a word into the Words table in the database.
	 * @param word
	 * @param coords
	 * @param user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertWord(Word word, Location coords, Word desiredWord) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		connect();
		Statement stmt = null;
		stmt = conn.createStatement();
		String points = coords.toString();
		if (points.length() > 250) {
			points = points.substring(0, 249);
		}
		String sql = "INSERT INTO Words VALUES (" + "'" + word + "'" + "," + "'" + desiredWord + "'" + "," + "'" + points + "'" + ");";
		boolean rs = stmt.execute(sql);
		stmt.close();
		conn.close();
	}
	/**
	 * retrieves a word from the Words table in the database.
	 * @param word
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static HashMap<String,String> getWord(Word word) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		HashMap<String,String> pair = new HashMap<String,String>();
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Word,Coordinates FROM Words WHERE Word= " + "'" + word + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String w = null;
		String l = null;
		while(rs.next()){
			w = rs.getString("Word");
			l = rs.getString("Coordinates");
			pair.put(w, l);
		}
		rs.close();
		stmt.close();
		conn.close();
		return pair;
	}

}
