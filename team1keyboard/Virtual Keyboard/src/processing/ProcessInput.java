package processing;

import java.awt.Point;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import sokgraph.Sokgraph;
import sokgraph.SokgraphFactory;
import words.Word;
import words.WordFactory;
import Location.Location;
import Location.LocationFactory;

public class ProcessInput {
	/**
	 * Gathers all the necessary information required to input a sokgraph into the Database.
	 * Once all the information has been gathered pass it to ProcessSQL for insertion into the database.
	 * @param str
	 * @param sokPnt
	 * @param coords
	 * @param userName
	 * @throws IOException
	 */
	public static void processSokgraph(String str, ArrayList<Point> sokPnt, ArrayList<Point> coords) throws IOException{
		Sokgraph sok = SokgraphFactory.createSokgraph(str, sokPnt);
		Word word = null;
		try {
			word = WordFactory.createWord(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Location loc = LocationFactory.createLocation(coords);
		try {
			ProcessSQL.processSok(sok,word,loc);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Gathers all the necessary information for inserting raw data into the Database. Once
	 * the information has been gathered pass it to ProcessSQL for insertion.
	 * @param str
	 * @param pnt
	 * @param user
	 */
	public static void processUserInput(ArrayList<String> str,ArrayList<Point> pnt, String desiredWord){	
		Location location = null;
		try {
			location = LocationFactory.createLocation(pnt);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Word rawText = null;
		Word word = null;
		
		try {
			rawText = WordFactory.createWord(str);
			word = WordFactory.createWord(desiredWord);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(rawText != null && location != null && word != null){
			try {
				ProcessSQL.process(rawText,location, word);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	


}
