package hust.soict.globalict.miniproject.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import hust.soict.globalict.miniproject.GenerticAlgorithm.GAexecution;
import hust.soict.globalict.miniproject.component.City;
import hust.soict.globalict.miniproject.component.Map;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;

public class VisualScreen extends JFrame {
	private JPanel contentPane;
	private GAexecution GA ;
	private GraphDrawer displayGraph;
	private File inputFile;
	private JSpinner spinnerRandomdata;
	private JTextField textGeneration;
	private JTextField textFitness;
	private JLabel lblPath;
	private HelpScreen helpFrame;
	private int Speed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualScreen frame = new VisualScreen();
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
	public VisualScreen() {
		setTitle("Visual Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Help");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (helpFrame == null) {
					helpFrame = new HelpScreen();
					helpFrame.setLocationRelativeTo(null);
					helpFrame.setVisible(true);
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you want to quit", "Quit", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == reply) { 
					dispose();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel innerPanel  = new JPanel();
		innerPanel.setBackground(new Color(204, 255, 255));
		innerPanel.setBounds(121, 101, 450, 420);
		contentPane.add(innerPanel);
		innerPanel.setLayout(null);
		
		//Start buuton listener
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(new Color(0, 255, 0));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GA.start();
			}
		});
		btnStart.setBounds(114, 532, 89, 23);
		contentPane.add(btnStart);
		
		//stop
		JButton btnStop = new JButton("Stop");
		btnStop.setBackground(new Color(255, 102, 102));
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GA.stop();
				GA.getDisplayGraph().drawVertices();
				GA.getDisplayGraph().drawEdges();
			}
		});
		btnStop.setBounds(234, 532, 89, 23);
		contentPane.add(btnStop);
		
		//choose file
		JButton btnChooseFile = new JButton("Choose dataset from file");
		btnChooseFile.setBackground(new Color(127, 255, 212));
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				final JFileChooser fc = new JFileChooser("C:\\Users\\ASUS\\eclipse-workspace\\GA_TSP_offical\\src\\hust\\soict\\globalict\\miniproject\\datas");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "txt");
				fc.setFileFilter(filter);
				if (e.getSource() == btnChooseFile) {
					int returnVal = fc.showOpenDialog(contentPane);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				    	File file = fc.getSelectedFile();
				        //This is where a real application would open the file.
				        inputFile = file;
				        btnChooseFile.setText(file.getName());
				        Map map = new Map();
						try {
							map.loadFromFile(inputFile);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//clear old Graph
						if (GA != null)
							if(GA.getDisplayGraph() != null )
								GA.getDisplayGraph().clear();
				
				        displayGraph = new GraphDrawer(null, null, map, innerPanel);
				        GA = new GAexecution(innerPanel, map, displayGraph, textGeneration, lblPath, textFitness);
						displayGraph.drawVertices();
				    } 
				}
			}
		});
		
		//random dataset
		JButton btnRandomDataset = new JButton("Load map");
		btnRandomDataset.setBackground(new Color(127, 255, 212));
		btnRandomDataset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map map = new Map();
				int nbCities = (int) spinnerRandomdata.getValue();
				Random random = new Random();
				ArrayList<City> cities = new ArrayList<>();
				for (int i=0; i<nbCities;i++) {
					City city = new City(i,10+random.nextFloat(400),10+random.nextFloat(400));
					cities.add(city);
				}
				map.setCities(cities);
				if (GA != null)
					if(GA.getDisplayGraph() != null )
						GA.getDisplayGraph().clear();
				displayGraph = new GraphDrawer(null, null, map, innerPanel);
		        GA = new GAexecution(innerPanel, map, displayGraph, textGeneration, lblPath, textFitness);
				displayGraph.drawVertices();
			}
		});
		btnRandomDataset.setBounds(603, 229, 102, 23);
		contentPane.add(btnRandomDataset);
		
		btnChooseFile.setBounds(603, 140, 177, 23);
		contentPane.add(btnChooseFile);
		
		JLabel lblRandomdata = new JLabel("Random dataset:");
		lblRandomdata.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRandomdata.setForeground(new Color(255, 255, 255));
		lblRandomdata.setBackground(Color.DARK_GRAY);
		lblRandomdata.setHorizontalAlignment(SwingConstants.LEFT);
		lblRandomdata.setBounds(606, 195, 99, 23);
		contentPane.add(lblRandomdata);
		
		spinnerRandomdata = new JSpinner();
		spinnerRandomdata.setModel(new SpinnerNumberModel(10, 2, 100, 1));
		spinnerRandomdata.setBounds(715, 196, 45, 20);
		contentPane.add(spinnerRandomdata);
		
		JLabel lblGeneration = new JLabel("Generation:");
		lblGeneration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGeneration.setForeground(new Color(255, 255, 255));
		lblGeneration.setBounds(603, 375, 107, 23);
		contentPane.add(lblGeneration);
		
		textGeneration = new JTextField();
		textGeneration.setBounds(722, 375, 102, 23);
		contentPane.add(textGeneration);
		textGeneration.setColumns(10);
		
		JLabel lblCurrentPath = new JLabel("Current Path:");
		lblCurrentPath.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCurrentPath.setForeground(Color.WHITE);
		
		lblCurrentPath.setBackground(Color.WHITE);
		lblCurrentPath.setBounds(33, 39, 79, 23);
		contentPane.add(lblCurrentPath);
		
		lblPath = new JLabel("");
		lblPath.setForeground(new Color(255, 255, 255));
		lblPath.setBounds(121, 11, 450, 79);
		contentPane.add(lblPath);
		
		JLabel lblCurrentDistance = new JLabel("Current Distance:");
		lblCurrentDistance.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCurrentDistance.setForeground(new Color(255, 255, 255));
		lblCurrentDistance.setBounds(603, 434, 99, 14);
		contentPane.add(lblCurrentDistance);
		
		textFitness = new JTextField();
		textFitness.setBounds(722, 431, 102, 20);
		contentPane.add(textFitness);
		textFitness.setColumns(10);
		
		//bacground
		ImageIcon background=new ImageIcon("back_ground_blue.jpg");
	    Image img=background.getImage();
	    Image temp=img.getScaledInstance(1920,900,Image.SCALE_SMOOTH);
	    background=new ImageIcon(temp);
	    
	    //speed
	    JLabel lblSpeed = new JLabel("Speed:");
	    lblSpeed.setForeground(Color.WHITE);
	    lblSpeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);
	    lblSpeed.setBounds(611, 285, 79, 14);
	    contentPane.add(lblSpeed);
	    
	    JSlider sliderSpeed = new JSlider();
	    sliderSpeed.setForeground(new Color(255, 0, 0));
	    sliderSpeed.setPaintTicks(true);
	    sliderSpeed.setBackground(new Color(0, 0, 205));
	    sliderSpeed.setValue(80);
	    sliderSpeed.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e) {
	    		if (GA!=null)
	    			GA.setSleepTime(sliderSpeed.getValue());
	    	}
	    });
	    sliderSpeed.setBounds(700, 285, 129, 25);
	    contentPane.add(sliderSpeed);
	    
	    JLabel back=new JLabel(background);
	    back.setForeground(new Color(255, 255, 255));
	    back.setLayout(null);
	    back.setBounds(0,0, 850, 650);
	    contentPane.add(back);
	}
}
