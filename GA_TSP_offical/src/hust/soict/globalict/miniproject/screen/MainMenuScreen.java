package hust.soict.globalict.miniproject.screen;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class MainMenuScreen extends JFrame {
	
	private VisualScreen visualFrame;
	private HelpScreen helpFrame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuScreen frame = new MainMenuScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenuScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("TSP Visualization");
		setBounds(100, 100, 342, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnVisualize = new JButton("Visualize TSP problem");
		btnVisualize.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVisualize.setBackground(Color.CYAN);
		btnVisualize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualFrame = new VisualScreen();
				visualFrame.setVisible(true);
				setVisible(false);
			}
		});
		btnVisualize.setBounds(57, 62, 205, 36);
		contentPane.add(btnVisualize);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnHelp.setBackground(Color.CYAN);
		
		//Help
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (helpFrame == null) {
					helpFrame = new HelpScreen();
					helpFrame.setLocationRelativeTo(null);
					helpFrame.setVisible(true);
				}
			}
		});
		btnHelp.setBounds(57, 103, 205, 36);
		contentPane.add(btnHelp);
		
		//exit
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnExit.setBackground(Color.CYAN);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you want to quit", "Quit", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == reply) { 
					dispose();
				}
			}
		});
		btnExit.setBounds(57, 146, 205, 36);
		contentPane.add(btnExit);
		
		ImageIcon background=new ImageIcon("backgroundMainmenu.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(342,302,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    JLabel back=new JLabel(background);
	    back.setLayout(null);
	    back.setBounds(0,0, 342, 302);
	    contentPane.add(back);
	}

}
