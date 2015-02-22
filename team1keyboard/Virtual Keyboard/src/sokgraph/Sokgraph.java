package sokgraph;

import java.awt.Point;
import java.util.ArrayList;

public class Sokgraph {
	private String word;
	private ArrayList<Point> coords = new ArrayList<Point>();
	public Sokgraph(String s, ArrayList<Point> pnt){
		setWord(s);
		setCoords(pnt);
	
	}
	/**
	 * A method that takes a word, searches for apostrhe's and returns the word
	 * with the apostrhe's removed.
	 * @param s
	 */
	private void setWord(String s) {
		// TODO Auto-generated method stub
		if(s == "" || s == null){
			throw new IllegalArgumentException("The word is empty or null");
		}else{
			String[] splitword = s.split("'");
			String finalword = "";
			for (String k : splitword) {
				finalword = finalword + k;
			}
			word = finalword;
		}
	}
	/**
	 * sets the coords datamember to the point array passed in so long as it is not empty
	 * or null.
	 * @param pnt
	 */
	private void setCoords(ArrayList<Point> pnt) {
		// TODO Auto-generated method stub
		if(pnt.isEmpty() || pnt == null){
			throw new IllegalArgumentException("The pnt array is empty or null");
		}else{
			coords = pnt;
		}
	}
	/**
	 * returns the coordinates of a sokgraph in the form of a string.
	 * @return
	 */
	public String getCoords(){
		String coordinates = "";
		for(int i = 0; i < coords.size(); i++){
			int x = (int) coords.get(i).getX();
			int y = (int) coords.get(i).getY();
			if(i == coords.size() -1 ){
				coordinates += x + "," + y;
			}else{
				coordinates += x + "," + y + " ";	
			}
		}
		return coordinates;
	}
	/**
	 * returns the string representation of a word.
	 * @return
	 */
	public String getWord(){
		return word;
	}
}
