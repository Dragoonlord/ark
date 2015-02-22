package Location;

import java.awt.Point;
import java.util.ArrayList;

public class Location {
	private String coords;
	Location(String pnt){
		setLocation(pnt);
	}
/**
 * set the coords datamember to the value passed in.
 * @param pnt
 */
	private void setLocation(String pnt) {
		// TODO Auto-generated method stub
		if(pnt != null && !pnt.isEmpty()){
			coords = pnt;
		}else{
			throw new IllegalArgumentException("Location string was null or empty");
		}
	}
	/**
	 * return the string representation of the this object.
	 */
	public String toString(){
		String s = coords;
		return s;
	}
}
