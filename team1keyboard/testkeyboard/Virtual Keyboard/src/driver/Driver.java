package driver;

import java.io.IOException;
import java.sql.SQLException;

import gui.Keyboard;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import login.User;
import login.UserLogin;
import dictionary.Dictionary;

public class Driver	{
	
	private static Driver d; 
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	d = new Driver();
    	d.setupDictionary();
    	d.createLogin();
    }
    
    public void setupDictionary() {
    	try {
			Dictionary.getInstance().isSetup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void createLogin() {
    	//create user login window
    	UserLogin u = new UserLogin();
    	u.createGUI(this);
    	u.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        u.setSize(320, 100);
        u.setVisible(true);
        u.setResizable(false);
        u.setLocation(100,100);
    }
    
    //called by login when sign-in is complete
    public void completeLogin(String username, User user) {
    	createKeyboard(user);
    }
    
    public void createKeyboard(User user) {
    	Keyboard board = new Keyboard(d, user);
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
    
    public void logout(User user) {
    	createLogin();
    }
    
}