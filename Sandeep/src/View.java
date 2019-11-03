import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.event.*;

public class View {

	private JFrame frame;
	private int levelNumber;

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
		frame.setBounds(100, 100, 1000, 1000);
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
		
		rules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//print rules
				JOptionPane.showMessageDialog(frame, "-Rabbits can only jump over other game pieces and they are allowed to jump over multiple pieces\n"
						+ "-Only one rabbit per hole\n"
						+ "-Foxes move either vertically or horizontally\n"
						+ "-Game is won once all rabbits are in the hole", "Rules", JOptionPane.OK_OPTION, 
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if(option == 0) {
					System.exit(0);
				}
			}
		});
		
		returnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the main menu?", 
						"Return to Main Menu", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, 
						new ImageIcon(getClass().getResource("Jump In Logo.jpg")));
				if(option == 0) {
					levelNumber = 0;
					((CardLayout) frame.getContentPane().getLayout()).first(frame.getContentPane());
				}
			}
		});
		
	}
	
	private void startMenu() {
		JPanel startMenu = new JPanel();
		startMenu.setLayout(null);
		frame.getContentPane().add(startMenu);
		
		ImageIcon logoImage = new ImageIcon(getClass().getResource("Jump In Logo.jpg"));
		JLabel logo = new JLabel(logoImage);
		logo.setBounds(97, 60, 500, 200);
		startMenu.add(logo);
		
		JButton newGame = new JButton("New Game");
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newGame.setBounds(250, 350, 180, 40);
		startMenu.add(newGame);
		
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelSelect();
				((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
			}
		});
	}
	
	private void levelSelect() {
		JPanel levelSelect = new JPanel();
		levelSelect.setLayout(null);
		frame.getContentPane().add(levelSelect);
		
		JButton level1 = new JButton("Level 1");
		level1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level1.setBounds(50, 500, 180, 40);
		levelSelect.add(level1);
		
		JButton level2 = new JButton("Level 2");
		level2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level2.setBounds(250, 500, 180, 40);
		levelSelect.add(level2);
		
		JButton level3 = new JButton("Level 3");
		level3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		level3.setBounds(450, 500, 180, 40);
		levelSelect.add(level3);
		
		JButton start = new JButton("Start");
		start.setFont(new Font("Tahoma", Font.PLAIN, 20));
		start.setBounds(250, 350, 180, 40);
		levelSelect.add(start);
		
		level1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelNumber = 1;
			}
		});
		
		level2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelNumber = 2;
			}
		});
		
		level3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				levelNumber = 3;
			}
		});
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(levelNumber > 0) {
					startLevel();
					((CardLayout) frame.getContentPane().getLayout()).next(frame.getContentPane());
				}
			}
		});
	}
	
	private void startLevel() {
		JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, 
		b15, b16, b17, b18, b19, b20, b21, b22, b23;
		Dimension square = new Dimension(50, 50);
		
		ImageIcon emptyImage = new ImageIcon(getClass().getResource("empty slot.png"));
		Image img = emptyImage.getImage();
		Image newImg = img.getScaledInstance(57, 50, java.awt.Image.SCALE_SMOOTH);
		emptyImage = new ImageIcon(newImg);
		
		ImageIcon foxImage = new ImageIcon(getClass().getResource("Fox logo.png"));
		Image img2 = foxImage.getImage();
		Image newImg2 = img2.getScaledInstance(57, 100, java.awt.Image.SCALE_SMOOTH);
		foxImage = new ImageIcon(newImg2);
		
		ImageIcon holeImage = new ImageIcon(getClass().getResource("hole.png"));
		Image img3 = holeImage.getImage();
		Image newImg3 = img3.getScaledInstance(57, 75, java.awt.Image.SCALE_SMOOTH);
		holeImage = new ImageIcon(newImg3);
		
		ImageIcon rabbitImage = new ImageIcon(getClass().getResource("rabbit pic.png"));
		Image img4 = rabbitImage.getImage();
		Image newImg4 = img4.getScaledInstance(57, 100, java.awt.Image.SCALE_SMOOTH);
		rabbitImage = new ImageIcon(newImg4);
		
		ImageIcon mushroomImage = new ImageIcon(getClass().getResource("mushroom image.png"));
		Image img5 = mushroomImage.getImage();
		Image newImg5 = img5.getScaledInstance(57, 75, java.awt.Image.SCALE_SMOOTH);
		rabbitImage = new ImageIcon(newImg5);
		
		JPanel startLevel = new JPanel();
		startLevel.setLayout(new GridBagLayout());
		frame.getContentPane().add(startLevel);
		GridBagConstraints gbc = new GridBagConstraints();
	
		
		b1 = new JButton("Hole");
		b1.setIcon(holeImage);
		b1.setPreferredSize(square);
		gbc.gridx = 0;
		gbc.gridy = 0;
		startLevel.add(b1, gbc);
		
		b2 = new JButton("FOX");
		b2.setIcon(foxImage);
		b2.setPreferredSize(square);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.VERTICAL;
		startLevel.add(b2, gbc); 
		
		b3 = new JButton("Rabbit");
		b3.setIcon(rabbitImage);
		b3.setPreferredSize(square);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		startLevel.add(b3, gbc);
		
		b4 = new JButton("EE");
		b4.setIcon(emptyImage);
		b4.setPreferredSize(square);
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		startLevel.add(b4, gbc);
		
		b5 = new JButton("Hole");
		b5.setIcon(holeImage);
		b5.setPreferredSize(square);
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		startLevel.add(b5, gbc);
		
		b6 = new JButton("EE");
		b6.setIcon(emptyImage);
		b6.setPreferredSize(square);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		startLevel.add(b6, gbc);
		
		b7 = new JButton("EE");
		b7.setIcon(emptyImage);
		b7.setPreferredSize(square);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		startLevel.add(b7, gbc);
		
		b8 = new JButton("EE");
		b8.setIcon(emptyImage);
		b8.setPreferredSize(square);
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		startLevel.add(b8, gbc);
		
		b9 = new JButton("MR");
		b9.setIcon(mushroomImage);
		b9.setPreferredSize(square);
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		startLevel.add(b9, gbc);
		
		b10 = new JButton("EE");
		b10.setIcon(emptyImage);
		b10.setPreferredSize(square);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		startLevel.add(b10, gbc);
		
		b11 = new JButton("EE");
		b11.setIcon(emptyImage);
		b11.setPreferredSize(square);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		startLevel.add(b11, gbc);
		
		b12 = new JButton("Hole");
		b12.setIcon(holeImage);
		b12.setPreferredSize(square);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		startLevel.add(b12, gbc);
		
		b13 = new JButton("EE");
		b13.setIcon(emptyImage);
		b13.setPreferredSize(square);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		startLevel.add(b13, gbc);
		
		b14 = new JButton("Rabbit");
		b14.setIcon(rabbitImage);
		b14.setPreferredSize(square);
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		startLevel.add(b14, gbc);
		
		b15 = new JButton("EE");
		b15.setIcon(emptyImage);
		b15.setPreferredSize(square);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		startLevel.add(b15, gbc);
		
		b16 = new JButton("EE");
		b16.setIcon(emptyImage);
		b16.setPreferredSize(square);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		startLevel.add(b16, gbc);
		
		b17 = new JButton("EE");
		b17.setIcon(emptyImage);
		b17.setPreferredSize(square);
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		startLevel.add(b17, gbc);
		
		b18 = new JButton("FOX");
		b18.setIcon(foxImage);
		b18.setPreferredSize(square);
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		startLevel.add(b18, gbc);
		
		b19 = new JButton("Hole");
		b19.setIcon(holeImage);
		b19.setPreferredSize(square);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		startLevel.add(b19, gbc);
		
		b20 = new JButton("EE");
		b20.setIcon(emptyImage);
		b20.setPreferredSize(square);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		startLevel.add(b20, gbc);
		
		b21 = new JButton("MR");
		b21.setIcon(mushroomImage);
		b21.setPreferredSize(square);
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		startLevel.add(b21, gbc);
		
		b22 = new JButton("EE");
		b22.setIcon(emptyImage);
		b22.setPreferredSize(square);
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		startLevel.add(b22, gbc);
		
		b23 = new JButton("Hole");
		b23.setIcon(holeImage);
		b23.setPreferredSize(square);
		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		startLevel.add(b23, gbc);
		
	}
}
