import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.event.*;


public class View implements ActionListener{

	private JFrame frame;
	private int levelNumber;
	private Board board;
	private JPanel startLevel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		levelNumber = 0;
		frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		CardLayout cards = new CardLayout();
		JPanel gamePanel = new JPanel(); //panel that stores other panels
		gamePanel.setLayout(cards);
		frame.setContentPane(gamePanel);
		
		startMenu();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		//JMenuItem save = new JMenuItem("Save");
		//JMenu load = new JMenu("Load");
		JMenuItem rules = new JMenuItem("Rules");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem returnMain = new JMenuItem("Return to Main Menu");
		
		frame.setJMenuBar(menuBar);
		menuBar.add(optionMenu);
		//optionMenu.add(save);
		//optionMenu.add(load);
		optionMenu.add(rules);
		optionMenu.add(quit);
		optionMenu.add(returnMain);
		
		rules.addActionListener(this);
		quit.addActionListener(this);
		returnMain.addActionListener(this);
	}
	
	private void startMenu() {
		JPanel startMenu = new JPanel();
		startMenu.setLayout(null);
		frame.getContentPane().add(startMenu);
		
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		int logoWidth = 500 ,logoHeight = 200;
		logo.setBounds(frame.getWidth()/2-logoWidth/2, frame.getHeight()/10, logoWidth, logoHeight);
		startMenu.add(logo);
		
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		int buttonWidth = 180 ,buttonHeight = 40;
		newGame.setBounds(frame.getWidth()/2-buttonWidth/2, frame.getHeight()/10+logoHeight+buttonHeight, buttonWidth, buttonHeight);
		startMenu.add(newGame);
		
		newGame.addActionListener(this);
	}
	
	private void levelSelect() {
		JPanel levelSelect = new JPanel();
		levelSelect.setLayout(null);
		frame.getContentPane().add(levelSelect);
		
		int buttonWidth = 180, buttonHeight = 40;
		
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level1.setBounds(frame.getWidth()/2-30-buttonWidth, frame.getHeight()/2, buttonWidth, buttonHeight);
		levelSelect.add(level1);
		
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level2.setBounds(frame.getWidth()/2+30, frame.getHeight()/2, buttonWidth, buttonHeight);
		levelSelect.add(level2);
		
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		start.setBounds(frame.getWidth()/2-buttonWidth/2, frame.getHeight()/2+buttonHeight+buttonHeight, buttonWidth, buttonHeight);
		levelSelect.add(start);
		
		level1.addActionListener(this);		
		level2.addActionListener(this);
		start.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		String text;
		if(e.getSource().getClass() == JButton.class) {
			JButton tempButton = (JButton) e.getSource();
			text = tempButton.getText();
			switch(text) {
			case "New Game":
				levelSelect();
				((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
				break;
			case "Level 1":
				levelNumber = 1;
				break;
			case "Level 2":
				levelNumber = 2;
				break;
			case "Start":
				if(levelNumber > 0) {
					startLevel(); 
					((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
					
				}
				break;
			default:
				//wip
				break;
			}
		}
		else if(e.getSource().getClass() == JMenuItem.class) {
			JMenuItem tempMenuItem = (JMenuItem) e.getSource();
			text = tempMenuItem.getText();
			switch(text) {
			case "Rules":
				JOptionPane.showMessageDialog(frame, "-Rabbits can only jump over other game pieces and they are allowed to jump over multiple pieces\n"
						+ "-Only one rabbit per hole\n"
						+ "-Foxes move either vertically or horizontally\n"
						+ "-Game is won once all rabbits are in the hole", "Rules", JOptionPane.OK_OPTION, 
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				break;
			case "Quit":
				int optionQuit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if(optionQuit == 0) {
					System.exit(0);
				}
				break;
			case "Return to Main Menu":
				int optionReturn = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the main menu?", 
						"Return to Main Menu", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if(optionReturn == 0) {
					levelNumber = 0;
					((CardLayout)frame.getContentPane().getLayout()).removeLayoutComponent(startLevel);
					((CardLayout) frame.getContentPane().getLayout()).first(frame.getContentPane());
				}
				break;
			}
			
		}
	}
	
	public void startLevel() {
		
		startLevel = new JPanel();
		board = new Board(levelNumber);
		GridBagConstraints gbc = new GridBagConstraints();
		Slot[][] tempBoard = board.getBoard();
		Dimension square = new Dimension(100, 100);
		
		startLevel.setLayout(new GridBagLayout());
		frame.getContentPane().add(startLevel);
		
		for (int x = 0; x < 5; x++) {
			
			for (int y = 0; y < 5; y++) {
			
				JButton tempButton;
				ImageIcon imageIcon;
				Image image;
				//String buttonName = "";
				String fileName = "";
				Class<? extends Slot> tempClass = tempBoard[x][y].getClass();
				//resetting constraints
				gbc.gridx = y;
				gbc.gridy = x;
				
				gbc.gridheight = 1;
				gbc.gridwidth = 1;
			
				if(tempClass == Rabbit.class) {
					Color rabbitColour = ((Rabbit) tempBoard[x][y]).getColor();
					if(rabbitColour == Color.WHITE) {
						//buttonName = "Wrabbit";
						fileName = "Wrabbit.png"; //change to white rabbit image file
					}
					else if (rabbitColour == Color.ORANGE) {
						//buttonName = "Orabbit";
						fileName = "Orabbit.png"; //change to orange rabbit image file
					}
					else if (rabbitColour == Color.GRAY) {
					//	buttonName = "Grabbit";
						fileName = "Grabbit.png"; //change to grey rabbit image file
					}
				}
				else if(tempClass == Fox.class) {
					Fox tempFox = (Fox) tempBoard[x][y];
					
					
					if(tempFox.getX() == x && tempFox.getY() == y) {
						if(tempFox.getVertical()) {
							if(tempFox.getTailX() == x - 1) {
								fileName = "Fox Hdown.png"; //change to vertical head down fox file
								gbc.gridy = x - 1;
							}
							else {
								fileName = "Fox Hup.png"; //change to vertical head up fox file
							}
						//	buttonName = "VFox";
							gbc.gridheight = 2; //overriding height dimension
						}
						else {
							
							if(tempFox.getTailY() == y - 1) {
								
								gbc.gridx = y - 1;
								fileName = "Fox Hright.png"; //change to horizontal head left fox file
								
							}
							else {
								
								fileName = "Fox Hleft.png"; //change to horizontal head right fox file
							}
							//buttonName = "HFox";
							gbc.gridwidth = 2; //overriding width dimension
						}
					}
					else {
						continue;
					}
				}
				else if(tempClass == Mushroom.class) {
				//	buttonName = "Shroom";
					fileName = "mushroom image.png";
				}
				else if(tempClass == Hole.class) {
				//	buttonName = "Hole";
					fileName = "hole.png";
				}
				else {
			//		buttonName = "Slot";
					fileName = "empty slot.png";
				}
				
				//System.out.println("fox add"+x+y);
				tempButton = new JButton();
				imageIcon = new ImageIcon(getClass().getResource(fileName));
				image = imageIcon.getImage();
				image = image.getScaledInstance(gbc.gridwidth*100, gbc.gridheight*100 , Image.SCALE_SMOOTH);
				imageIcon = new ImageIcon(image);
				
				tempButton.setIcon(imageIcon);
				tempButton.setSize(square);
				tempButton.addActionListener(this);
				tempButton.setBorder(BorderFactory.createEmptyBorder());
				
				startLevel.add(tempButton,gbc);
			}
		}
	}
}