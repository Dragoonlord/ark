package Location;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class LocationFactory {
	private static HashMap<String, Location> locations = new HashMap<String,Location>();
	/**
	 * if the location does not exist create a new one and insert it into the locations hashmap.
	 * If the location does exist, pull it out of the hashmap and return it.
	 * @param coords
	 * @return
	 * @throws IOException
	 */
	public static Location createLocation(ArrayList<Point> coords) throws IOException{
		String s = setPoints(coords);
		if(!locations.containsKey(s)){
			Location l = new Location(s);
			
			locations.put(l.toString(),l);
			return locations.get(l.toString());
		}else{
			if(locations.containsKey(s)){
				return locations.get(s);
			}
			return null;
		}
	}
	/**
	 * Takes an array of points as input and turns that array of points into a 
	 * string which will be later inserted into the database.
	 * @param pnt
	 * @return
	 */
	private static String setPoints(ArrayList<Point> pnt) {
		if(pnt.isEmpty() || pnt == null){
			throw new IllegalArgumentException("The point array is empty or null");
		}
		String coords = "";
		for(int i = 0; i < pnt.size(); i++){
			int x = (int) pnt.get(i).getX();
			int y = (int) pnt.get(i).getY();
			if(i == pnt.size() -1 ){
				coords +=  x + "," + y;
			}else{
				coords +=  x + "," + y + " ";	
			}
		}
		
		return coords;
	}
}
