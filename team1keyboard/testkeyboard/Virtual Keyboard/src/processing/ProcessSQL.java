package processing;


import java.awt.Point;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import sokgraph.Sokgraph;
import sql.LocationSQL;
import sql.SokgraphSQL;
import sql.WordSQL;
import Location.Location;
import Location.LocationFactory;
import words.Word;

public class ProcessSQL {
	/**
	 * A utility function that calls to other functions, which in turn query and insert information into the database.
	 * @param w
	 * @param location
	 * @param user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void process(Word rawText,Location location, String user, Word word) throws ClassNotFoundException, SQLException{
		processLocation(location);
		//processTextInput(w,location,user);
		processWord(rawText, location, word);
	}
	
	private static void processWord(Word w, Location l, Word word) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		if(!WordSQL.queryWord(word)){
			WordSQL.insertWord(w, l, word);
		}
	}
	

	/**
	 * Takes the users raw coordinates input, and passes it to LocationSQL, which queries and inserts the information
	 * into the Database.
	 * @param l
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static void processLocation(Location l) throws SQLException, ClassNotFoundException{
		if(!LocationSQL.queryLocation(l)){
			LocationSQL.insertLocation(l);
		}
	}
	/**
	 * Takes all the information required to make a sokgraph, and passes it to SokgraphSQL for insertion into the Database.
	 * @param sok
	 * @param word
	 * @param coords
	 * @param user
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void processSok(Sokgraph sok, Word word, Location coords, String user) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		if(!SokgraphSQL.querySokgraphWord(sok)){
			SokgraphSQL.insertSokgraph(sok, user);
		}
	}

	
	
}
