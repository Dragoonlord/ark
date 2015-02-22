package login;

import gui.Keyboard;

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

import sql.UserSQL;


public class EditUser extends JFrame {
	
	
	public EditUser(User u) {
		super("Edit User Account");
		
		JPanel entryField = new JPanel();
		entryField.setLayout(new GridLayout(5,2));

		final JLabel uName = new JLabel(" username *");
		entryField.add(uName);
		final JTextField userName = new JTextField(u.getUser());
		userName.setEditable(false);
		entryField.add(userName);
		final JLabel fName = new JLabel(" first name");
		entryField.add(fName);
		final JTextField firstName = new JTextField();
		entryField.add(firstName);
		final JLabel lName = new JLabel(" last name");
		entryField.add(lName);
		final JTextField lastName = new JTextField();
		entryField.add(lastName);
		final JLabel pWord = new JLabel(" password *");
		entryField.add(pWord);
		final JPasswordField passWord = new JPasswordField();
		entryField.add(passWord);
		final JLabel pWord2 = new JLabel(" confirm password *");
		entryField.add(pWord2);
		final JPasswordField passWord2 = new JPasswordField();
		entryField.add(passWord2);
		
	    JButton updateButton = new JButton("update account");
	    add(updateButton, BorderLayout.SOUTH);
		add(entryField, BorderLayout.CENTER);
		
		updateButton.addMouseListener(new java.awt.event.MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String nameInput = userName.getText();
				String passInput = passWord.getText();
				String passInput2 = passWord2.getText();
				String fNameInput = firstName.getText();
				String lNameInput = lastName.getText();
				if (!(passInput.equals("")) && !(passInput2.equals("")) && passInput.equals(passInput2)) {
					try {
						if (UserSQL.checkForUser(nameInput) && isEmailAddress(nameInput)) {
							UserSQL.updateUser(nameInput, passInput, fNameInput, lNameInput);
							JOptionPane.showMessageDialog(null,"Account updated.");
							//close login window
							setVisible(false);
							dispose();
						}
						else if(!isEmailAddress(nameInput)){
							JOptionPane.showMessageDialog(null,"UserName is not an email address.");
							userName.setText("");
						
						}
						else if(nameInput.length() > 50){
							JOptionPane.showMessageDialog(null,"UserName must be shorter than 50 characters.");
							userName.setText("");
						
						}
						else {
							JOptionPane.showMessageDialog(null,"A user with that username already exists.");
							userName.setText("");
							passWord.setText("");
							passWord2.setText("");
						}
					} catch (HeadlessException | ClassNotFoundException | SQLException e) {
						e.printStackTrace();
					}
				}
				else if (!passInput.equals(passInput2)) {
					JOptionPane.showMessageDialog(null,"Password fields do not match.");
					//userName.setText("");
					passWord.setText("");
					passWord2.setText("");
					firstName.setText("");
					lastName.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null,"Password cannot be blank.");
					//userName.setText("");
					passWord.setText("");
					passWord2.setText("");
					firstName.setText("");
					lastName.setText("");
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
	}
    public boolean isEmailAddress(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        return m.matches();
 }
}