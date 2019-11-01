import javax.swing.*;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
		frame.setBounds(100, 100, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				JOptionPane.showMessageDialog(null, "show rules here - temp", "Rules", JOptionPane.OK_OPTION);
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
				if(option == 0) {
					System.exit(0);
				}
			}
		});
		
		returnMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the main menu?", "Return to Maine Menu", JOptionPane.YES_NO_OPTION);
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
		JPanel level = new JPanel();
		level.setLayout(new GridLayout(5, 5));
		//WIP
		
		
	}
}
