package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import sokgraph.Sokgraph;
import words.Word;

public class SokgraphSQL {
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
	 * Loads in the proper driver so that java can interface and connect to a MySQL server.
	 * @throws ClassNotFoundException
	 */
	private static void loadDriver() throws ClassNotFoundException{
		Class.forName(JDBC_DRIVER);
	}
	/**
	 * Checks to see if a sokgraph exists within the Sokgraphs table in the database. If it does,
	 * it returns the sokgraph to the calling function. Else it returns null.
	 * @param word
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static String getSokgraph(Word word) throws SQLException, ClassNotFoundException{
		
		if(!driverLoaded){
			loadDriver();
		}
		HashMap<String,String> pair = new HashMap<String,String>();
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Sokgraph FROM Sokgraphs WHERE Word= " + "'" + word + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String sok = null;
		while(rs.next()){
			sok = rs.getString("Sokgraph");
			
		}
		rs.close();
		stmt.close();
		conn.close();
		return sok;
	}
	/**
	 * Inserts a given sokgraph into the Sokgraphs table in the database.
	 * @param sok
	 * @param user
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static void insertSokgraph(Sokgraph sok, String user) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		connect();
		Statement stmt = null;
		stmt = conn.createStatement();
		String sokgraph = sok.getCoords();
		if (sokgraph.length() > 250) {
			sokgraph = sokgraph.substring(0, 249);
		}
		
		String sql = "INSERT INTO Sokgraph VALUES (" + "'" + sokgraph + "'"  + "," + "'" + sok.getWord() + "'" + "," + "'" + user + "'" + ");";
		boolean rs = stmt.execute(sql);
		stmt.close();
		conn.close();
	}
	/**
	 * Checks to see if a word has a corresponding sokgraph associated with it. If it does this functin returns true, else it returns
	 * false.
	 * @param sok
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static boolean querySokgraphWord(Sokgraph sok) throws SQLException, ClassNotFoundException{
		if(!driverLoaded){
			loadDriver();
		}
		ArrayList<String> words = new ArrayList<String>();
		Statement stmt = null;
		connect();
		stmt = conn.createStatement();
		String sql = "SELECT Word FROM Sokgraph WHERE Word= " + "'" + sok.getWord() + "'";
		ResultSet rs = stmt.executeQuery(sql);
		String w = null;
		while(rs.next()){
			w = rs.getString("Word");
			words.add(w);
		}
		rs.close();
		stmt.close();
		conn.close();
		if(w == null){
			return false;
		}else{
			return true;
		}
	}

}
