package driver;

import java.io.IOException;
import java.sql.SQLException;

import gui.Keyboard;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import dictionary.Dictionary;

public class Driver	{
	
	private static Driver d; 
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	d = new Driver();
    	d.setupDictionary();
    	d.createKeyboard();
    }
    
    public void setupDictionary() {
    	try {
			Dictionary.getInstance().isSetup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void createKeyboard() {
    	Keyboard board = new Keyboard(d);
		JList list = new JList();
	    board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setSize(520, 224);
        //board.setSize(515, 225); //keys exactly 50x50 px
	    board.setVisible(true);
	    board.setResizable(false);
	    board.setLocation(100,100);
		JPanel NBest = new JPanel();
		NBest.setSize(50,100);
		NBest.setVisible(false);
		board.getLayeredPane().add(NBest, JLayeredPane.POPUP_LAYER);
	    NBest.add(list);
	    board.setList(list,NBest);
    }
    
}