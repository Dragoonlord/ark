package gui;

import java.sql.SQLException;
import java.util.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import driver.Driver;
import login.EditUser;
import login.User;
import login.UserSignup;
import processing.ProcessInput;
import sokgraph.TemplatePrune;
import Location.Location;
import words.Word;


public class Keyboard extends JFrame {
	private boolean wordAdded = false;
	private boolean punctAdded = false;
	private boolean caps = false;
	private boolean mouseDown = false;
	private JPanel NBest;
	private JButton[] keyList = new JButton[26];
	
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<String> keynames = new ArrayList<String>();
	private ArrayList<Point> sokPoints = new ArrayList<Point>();
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("user");
	private JMenuItem logout;
	private JMenuItem edit;
	
	private JButton A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,space,backspace;
	private JTextField textField;
	private JList list;
	private User user;
	private Driver driver;
	/**
	 * A constructor which sets up the Keyboard JFrame. It loads all the Jpanels and JButtons into
	 * the frame, and also handles all the event-driven actions such as mouse clicks and swipes. All the raw
	 * data the the users inputs is pushed into ProcessInput.
	 * @param d the driver that created the keyboard
	 * @param u the user who is currently signed in
	 */
	public Keyboard(Driver d, User u) {
		
		super("Virtual Keyboard: " + u.getUser().toLowerCase() );
		user = u;
		driver = d;
		textField = new JTextField(60);
	    textField.setEditable(true);
	    
	    menuBar.add(menu);
	    setJMenuBar(menuBar);
	    logout = new JMenuItem("logout");
	    menu.add(logout);
	    edit = new JMenuItem("edit account");
	    menu.add(edit);
		
		final JPanel keys = new JPanel();
		keys.setLayout(new GridLayout(3,10));
		
		Q = new JButton("q");
		keys.add(Q); keyList[0] = Q;
		W = new JButton("w");
		keys.add(W); keyList[1] = W;
		E = new JButton("e");
		keys.add(E); keyList[2] = E;
		R = new JButton("r");
		keys.add(R); keyList[3] = R;
		T = new JButton("t");
		keys.add(T); keyList[4] = T;
		Y = new JButton("y");
		keys.add(Y); keyList[5] = Y;
		U = new JButton("u");
		keys.add(U); keyList[6] = U;
		I = new JButton("i");
		keys.add(I); keyList[7] = I;
		O = new JButton("o");
		keys.add(O); keyList[8] = O;
		P = new JButton("p");
		keys.add(P); keyList[9] = P;
		
		A = new JButton("a");
		keys.add(A); keyList[10] = A;
		S = new JButton("s");
		keys.add(S); keyList[11] = S;
		D = new JButton("d");
		keys.add(D); keyList[12] = D;
		F = new JButton("f");
		keys.add(F); keyList[13] = F;
		G = new JButton("g");
		keys.add(G); keyList[14] = G;
		H = new JButton("h");
		keys.add(H); keyList[15] = H;
		J = new JButton("j");
		keys.add(J); keyList[16] = J;
		K = new JButton("k");
		keys.add(K); keyList[17] = K;
		L = new JButton("l");
		keys.add(L); keyList[18] = L;
		JButton Padder1 = new JButton("");
		keys.add(Padder1);
		
		final JButton capsLock = new JButton("\u2191");
		keys.add(capsLock);
		Z = new JButton("z");
		keys.add(Z); keyList[19] = Z;
		X = new JButton("x");
		keys.add(X); keyList[20] = X;
		C = new JButton("c");
		keys.add(C); keyList[21] = C;
		V = new JButton("v");
		keys.add(V); keyList[22] = V;
		B = new JButton("b");
		keys.add(B); keyList[23] = B;
		N = new JButton("n");
		keys.add(N); keyList[24] = N;
		M = new JButton("m");
		keys.add(M); keyList[25] = M;
		JButton Padder3 = new JButton("");
		keys.add(Padder3);
		JButton Punct = new JButton(".");
		keys.add(Punct);
	    
	    JPanel spaceBars = new JPanel();
	    spaceBars.setLayout(new GridLayout(1,2));
	    
	    space = new JButton("space");
	    spaceBars.add(space);
	    backspace = new JButton("backspace");
	    spaceBars.add(backspace);
	    
	    add(textField, BorderLayout.NORTH);
	    add(keys, BorderLayout.CENTER);
	    add(spaceBars, BorderLayout.SOUTH);
		
	    for (final JButton key : keyList) {
	    	key.addMouseListener(new java.awt.event.MouseListener() {
	            public void mousePressed(java.awt.event.MouseEvent evt) {
	            	if (SwingUtilities.isLeftMouseButton(evt)) {
	            		NBest.setVisible(false);
		            	sokPoints.clear();
						points.clear();
						keynames.clear();
		            	
		            	points.add(SwingUtilities.convertPoint(key,evt.getPoint(),keys));
		            	Point loc = key.getLocation();
						int x = loc.x + (key.getWidth()/ 2);
						int y = loc.y + (key.getHeight()/ 2);
						sokPoints.add(new Point(x,y));
		            	
		            	keynames.add(key.getText().toUpperCase());
		            	mouseDown = true;
		            	String newText = (textField.getText() + key.getText());
						//textField.setText(newText);
	            	}
	            }
	            /**
	             * when the mouse is released take the raw data contained in the points and keynames arrays, and push it into
	             * ProcessInput, next clear the arrays.
	             */
	            @Override
				public void mouseReleased(MouseEvent evt) {
	            	if (SwingUtilities.isLeftMouseButton(evt)) {
	            		wordAdded = false;
						mouseDown = false;
						System.out.println(points);
						System.out.println(keynames);
						if (keynames.size() == 1) {
							String newText = (textField.getText() + key.getText());
							textField.setText(newText);
							points.clear();
							keynames.clear();
						}
						else {
							//ProcessInput.processUserInput(keynames, points, user.getUser());
							String[] matchResults = new String[4];
							try {
								matchResults = TemplatePrune.findMatch(keynames);
							} catch (ClassNotFoundException | SQLException
									| IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							if(matchResults[0] != null){
								for (String f : matchResults) {
									
									if (f == null) {
										//f = new String("");
									}
									System.out.print(f + " ");
								}
								System.out.print("\n");
								//textField.setText(textField.getText() + matchResults[0] + " ");
								list.setListData(matchResults);
								NBest.setVisible(true);
								list.addListSelectionListener(new ListSelectionListener() {
									/**
									 * Display the N-Best drop down J-List, and allow the user to choose a word from it.
									 */
									@Override
									public void valueChanged(ListSelectionEvent arg){
										
										 if (!arg.getValueIsAdjusting()) {
											 if(list.getSelectedValue() != null){
												 if (wordAdded == false) {
													String newText = (textField.getText() + list.getSelectedValue().toString());
													textField.setText(newText);
													try {
														//System.out.println(sokPoints);
														ProcessInput.processUserInput(keynames, points, user.getUser(),list.getSelectedValue().toString());
														ProcessInput.processSokgraph(list.getSelectedValue().toString(),sokPoints, points, user.getUser());
														sokPoints.clear();
														points.clear();
														keynames.clear();
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													wordAdded = true;
												 }
											 }
							              }
										 NBest.setVisible(false);
										 //list.clearSelection();
									}
								});
							}
						}
	            	}
	            	
				}

	            @Override
				public void mouseClicked(MouseEvent evt) {}
	            /**
	             * When the mouse cursor enters a key, get the letter of the key, the coordinate of the cursor, and the center point
	             * of the key relative to the keyboard and each into its own array for later processing.
	             */
				@Override
				public void mouseEntered(MouseEvent evt) {
					if (mouseDown) {
						Point p = SwingUtilities.convertPoint(key, evt.getPoint(), keys);
						points.add(p);
						keynames.add(key.getText().toUpperCase());
						Point loc = key.getLocation();
						int x = loc.x + (key.getWidth()/ 2);
						int y = loc.y + (key.getHeight()/ 2);
						sokPoints.add(new Point(x,y));
					}
				}

				@Override
				public void mouseExited(MouseEvent arg0) {}
	        });
	    }
	    
	    space.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mouseDown = true;
            }
            
            @Override
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String newText = (textField.getText() + " ");
				textField.setText(newText);
			}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
        });
	    
	    backspace.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mouseDown = true;
            }
            
            @Override
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String text = textField.getText();
				if (text.length() > 0) {
					String newText = text.substring(0, text.length() - 1);
					textField.setText(newText);
				}
			}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
        });
	    
	  //temporary shift key
	    capsLock.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mouseDown = true;
            }
            
            @Override
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (caps == true) {
					for (final JButton key : keyList) {
						key.setText(key.getText().toLowerCase());
					}
					caps = false;
					capsLock.setText("\u2191");
				}
				else if (caps == false) {
					for (final JButton key : keyList) {
						key.setText(key.getText().toUpperCase());
					}
					caps = true;
					capsLock.setText("\u2193");
				}
			}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
        });
	    
	    Punct.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	mouseDown = true;
            }
            
            @Override
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				punctAdded = false;
				String[] punctuation = {".", ",", "!", "?"};
				list.setListData(punctuation);
				NBest.setVisible(true);
				list.addListSelectionListener(new ListSelectionListener() {
					
					@Override
					public void valueChanged(ListSelectionEvent arg){
						
						 if (!arg.getValueIsAdjusting()) {
							 if(list.getSelectedValue() != null){
								 if (punctAdded == false) {
									String newText = (textField.getText() + list.getSelectedValue().toString());
									textField.setText(newText);
									punctAdded = true;
								 }
							 }
			              }
						 NBest.setVisible(false);
						 //list.clearSelection();
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
        });
	    
	    logout.addMouseListener(new java.awt.event.MouseListener() {
	    	@Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	logout();
            }
            
            @Override
			public void mouseReleased(MouseEvent arg0) {}

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
        });
	    
	    edit.addMouseListener(new java.awt.event.MouseListener() {
	    	public void mousePressed(java.awt.event.MouseEvent evt) {
	    		EditUser e = new EditUser(user);
		    	//s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        e.setSize(320, 170);
		        e.setVisible(true);
		        e.setResizable(false);
		        e.setLocation(150,150);
	    	}
	    	
	    	@Override
			public void mouseReleased(MouseEvent arg0) {}

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent evt) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}
	    	
	    });
	    
	}
	/**
	 * takes the N-Best list generated from the users input and puts it into the 
	 * JList component.
	 * @param l
	 * @param j
	 */
	public void setList(JList l, JPanel j){
		list = l;
		NBest = j;
		NBest.setVisible(false);
	}
	/**
	 * Asks the user if they wish to logout. If so, it closes the keyboard display and returns to the login screen. 
	 * Otherwise, nothing happens.
	 */
	public void logout() {
		int logout = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
		if (logout == JOptionPane.YES_OPTION) 
		{
		  JOptionPane.showMessageDialog(null, "Logout Successful", "Logout", JOptionPane.INFORMATION_MESSAGE);
		  driver.logout(user);
		  setVisible(false);
		  dispose();
		}
	}
}