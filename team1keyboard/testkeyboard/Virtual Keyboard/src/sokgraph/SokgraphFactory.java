package sokgraph;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SokgraphFactory {
	private static HashMap<String,Sokgraph> sokgraphs = new HashMap<String,Sokgraph>();
	/**
	 * Checks to see if the sokgraph already exists. If so, it returns the copy already stored in the sokgraphs
	 * hashmap. If it has not been created yet, the sokgraph is created, inserted into the hashmap, then returned to the user.
	 * @param s
	 * @param pnt
	 * @return
	 * @throws IOException
	 */
	public static Sokgraph createSokgraph(String s,ArrayList<Point> pnt) throws IOException{
		s= s.toLowerCase();
		if(!sokgraphs.containsKey(s)){
			Sokgraph w = new Sokgraph(s,pnt);
			sokgraphs.put(s,w);
			return sokgraphs.get(s);
		}else{
			if(sokgraphs.containsKey(s)){
				return sokgraphs.get(s);
			}
			return null;
		}
	}

}
