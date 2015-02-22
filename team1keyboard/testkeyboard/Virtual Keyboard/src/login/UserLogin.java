package login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import driver.Driver;
import sql.UserSQL;


public class UserLogin extends JFrame {
	
	
	public UserLogin() {
		super("User Login");
		
	}
	
	public void createGUI(Driver d){
	final Driver drive = d;
	User u = null;
	JPanel entryField = new JPanel();
	entryField.setLayout(new GridLayout(2,2));

	final JLabel uName = new JLabel(" username");
	entryField.add(uName);
	final JTextField userName = new JTextField();
	entryField.add(userName);
	final JLabel pWord = new JLabel(" password");
	entryField.add(pWord);
	final JPasswordField passWord = new JPasswordField();
	entryField.add(passWord);
	
	JPanel bottomBars = new JPanel();
    bottomBars.setLayout(new GridLayout(1,2));
	
    JButton newAccountButton = new JButton("create new account?");
    bottomBars.add(newAccountButton);
    JButton loginButton = new JButton("login");
    bottomBars.add(loginButton);
	
	add(entryField, BorderLayout.CENTER);
	add(bottomBars, BorderLayout.SOUTH);
	
	
	loginButton.addMouseListener(new java.awt.event.MouseListener() {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			String nameInput = userName.getText();
			String passInput = passWord.getText();
			try {
				if (UserSQL.checkForUser(nameInput) && UserSQL.checkForPassword(passInput, nameInput)) {
					if(isEmailAddress(userName.getText())){
						JOptionPane.showMessageDialog(null,"Login successful.");
						//tell driver login was successful, close login window
						User u = new User(userName.getText());
						drive.completeLogin(nameInput,u);
						setVisible(false);
						dispose();
						
					}else{
						JOptionPane.showMessageDialog(null,"UserName was not an email address");
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Username or password is not correct.");
					userName.setText("");
					passWord.setText("");
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
        public void mousePressed(java.awt.event.MouseEvent evt) {}
        
        @Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent evt) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}
    });
	
newAccountButton.addMouseListener(new java.awt.event.MouseListener() {
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			UserSignup s = new UserSignup();
	    	//s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        s.setSize(320, 170);
	        s.setVisible(true);
	        s.setResizable(false);
	        s.setLocation(150,150);
		}
		
		@Override
        public void mousePressed(java.awt.event.MouseEvent evt) {}
        
        @Override
		public void mouseReleased(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent evt) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}
    });
}
    public boolean isEmailAddress(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email.toLowerCase());
        return m.matches();
 }
}