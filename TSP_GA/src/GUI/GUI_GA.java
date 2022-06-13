package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import GA.Individual;
import GA.Map;
import GA.Population;
import GA.operations.Crossover;
import GA.operations.Mutation;
import GA.utils.Factors;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class GUI_GA extends JFrame {
	private static Individual bestFitnessIndividual;
	private JPanel contentPane;
	int stop = 0;
	private SwingWorker<Void, GUI_GA> worker;
	final DrawFlowGraph Graph = new DrawFlowGraph();
	private JLabel currentPathLabel = new JLabel();
	private File inpuFile = null;
	static Map map = new Map();
	private JTextField nbGenerationTextfield;
	private JTextField fitnessTexfield;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_GA frame = new GUI_GA();
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
	public GUI_GA() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Visualize GA Team 8");
		setBounds(100, 100, 800, 600);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu TSP_menu = new JMenu("Option ");
		menuBar.add(TSP_menu);
		
		JMenuItem help_mn = new JMenuItem("Help");
		TSP_menu.add(help_mn);
		
		//Hendle quit 
		JMenuItem mntmNewMenuItem = new JMenuItem("Quit");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you want to quit", "Quit", JOptionPane.YES_NO_OPTION);
				if (JOptionPane.YES_OPTION == reply) { 
					dispose();
				}
			}
		});
		TSP_menu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.white);
		// initial Graph
		Graph.setBounds(55, 93, 445, 422);
		Graph.setVisible(false);
		contentPane.add(Graph);
		
		Canvas canvas = new Canvas();
		Graph.add(canvas);
		JButton startButton = new JButton("Start");
		
		//handle start button
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeWorker();
				worker.execute();
			}
		});
		startButton.setBounds(65, 516, 63, 23);
		contentPane.add(startButton);
		
		//Handle Stop
		JButton StopButton = new JButton("Stop");
		StopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				worker.cancel(true);
			}
		});
		StopButton.setBounds(162, 516, 63, 23);
		contentPane.add(StopButton);
		
		//Handle Load
		JButton loadBotton = new JButton("Load");
		loadBotton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inpuFile != null) {
				try {
					map.loadFromFile(inpuFile);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Graph.setMap(map);
				Graph.setVisible(true);
				}
  			}
		});
		loadBotton.setBounds(267, 516, 63, 23);
		contentPane.add(loadBotton);
		
		JLabel arry_Vur_Label = new JLabel("Path:");
		arry_Vur_Label.setEnabled(false);
		arry_Vur_Label.setBounds(10, 31, 46, 31);
		contentPane.add(arry_Vur_Label);
		//path label
		currentPathLabel.setText("curent path");
		
		//virualize array label
		currentPathLabel.setBounds(55, 11, 445, 71);
		contentPane.add(currentPathLabel);
		
		final JButton chooseFileButton = new JButton("Choose a fille");
		chooseFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser("C:\\Users\\ASUS\\eclipse-workspace\\TSP_GA\\src");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "txt");
				fc.setFileFilter(filter);
				if (e.getSource() == chooseFileButton) {
					int returnVal = fc.showOpenDialog(contentPane);
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				    	File file = fc.getSelectedFile();
				        //This is where a real application would open the file.
				        inpuFile = file;
				        chooseFileButton.setText(file.getName());
				    } 
				}
				
			}
		});
		//choosefile
		chooseFileButton.setBounds(537, 93, 137, 23);
		contentPane.add(chooseFileButton);
		
		//
		JLabel generationLabel = new JLabel("Generation:");
		generationLabel.setHorizontalAlignment(SwingConstants.LEFT);
		generationLabel.setBounds(537, 281, 93, 23);
		contentPane.add(generationLabel);
		
		// number Generation Textfield
		nbGenerationTextfield = new JTextField();
		nbGenerationTextfield.setBounds(647, 282, 63, 20);
		contentPane.add(nbGenerationTextfield);
		nbGenerationTextfield.setColumns(10);
		
		//fitnessLabel
		JLabel fitnessLabel_1 = new JLabel("Current Distance:");
		fitnessLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		fitnessLabel_1.setBounds(537, 344, 93, 23);
		contentPane.add(fitnessLabel_1);
		
		//fitnessTexfield
		fitnessTexfield = new JTextField();
		fitnessTexfield.setBounds(647, 345, 63, 20);
		contentPane.add(fitnessTexfield);
		fitnessTexfield.setColumns(10);
		if(bestFitnessIndividual != null)
		currentPathLabel.setText(bestFitnessIndividual.toString());
	}
	
	 private void initializeWorker() {
	        worker = new SwingWorker<Void, GUI_GA>() {
	            @Override
	            protected Void doInBackground() throws Exception {
	    				Graphics graphics = Graph.getGraphics();
	    				int N = Factors.nbIndividual;//number of individual in a population
	    				//initial First population randomly
	    				Population population = new Population(N, GUI_GA.map);
	    				//sort the Individual by fitness
	    				population.sortIndividual();
	    				nbGenerationTextfield.setText("0");
	    				fitnessTexfield.setText(String.valueOf(population.getIndividual(0).getFitness()));
	    				//nummber of generation
	    				int nbGen = 0;
	    				//crossover and mutation
	    				while (!isCancelled()) {
	    					//calculate fitness
	    					for (Individual individual: population.getIndividuals()) {
	    						individual.calculateFitness(GUI_GA.map);
	    					}
	    					//Crossover
	    					int nbCrossover = GUI_GA.map.getCities().size(); // number of crossover is number of cities 
	    					for(int j=0; j < nbCrossover ;j++) {
	    						//choose parents 50% better fitness and 50% worse fitness
	    						Random random = new Random();
	    						int p1 = random.nextInt(N/2-1); //choose randomly 50% better fitness
	    						int p2 = random.nextInt(N/2-1)+N/2; //choose randomly 50% worse fitness
	    		 				Individual parent1 = population.getIndividual(p1);
	    		 				Individual parent2 = population.getIndividual(p2);
	    		 				Crossover crossover = new  Crossover();
	    						ArrayList<Individual> offsprings = crossover.SPX(parent1, parent2);
	    						//Mutation (in order to mutation be operated ,Crossover have to be operate )
	    						Individual offspring1 = new Individual(offsprings.get(0));
	    						Individual offspring2 = new Individual(offsprings.get(1));
	    						if(random.nextDouble(1)<Factors.mutationRate) {
	    							offspring1 = new  Mutation().swap(offspring1);
	    							offspring2 = new  Mutation().swap(offspring2);
	    							
	    						}
	    						offspring1.calculateFitness(GUI_GA.map);
	    						offspring2.calculateFitness(GUI_GA.map);
	    						//replace parent by offspring if offspring has better fitness
//	    						System.out.println(offsprings.get(0).getFitness() +"-"+ parent1.getFitness());
	    						if (offspring1.getFitness() < parent1.getFitness()) {
	    							population.setIndividual(p1,offspring1);
	    						}
	    						if (offspring2.getFitness() < parent2.getFitness()) {
	    							population.setIndividual(p2, offspring2);
	    						}
	    					}
	    					//get the old path to delete
	    					ArrayList<Integer> oldPath = null;
	    					if (GUI_GA.bestFitnessIndividual != null ) {
	    						oldPath = GUI_GA.bestFitnessIndividual.getPath();
	    					}
	    					//sort the Individual by fitness
	    					population.sortIndividual();
	    					Graph.repaintning(graphics);
	    					GUI_GA.bestFitnessIndividual = population.getIndividual(0);
	    					GUI_GA.bestFitnessIndividual.setOldPath(oldPath);
	    					GUI_GA.bestFitnessIndividual.drawEdges(graphics, GUI_GA.map);
	    					System.out.println(bestFitnessIndividual.getFitness());
	    					currentPathLabel.setText(bestFitnessIndividual.toString());
	    					nbGenerationTextfield.setText(String.valueOf(nbGen++));
		    				fitnessTexfield.setText(String.valueOf(bestFitnessIndividual.getFitness()));
	    				}
	                return null;
	            }
	        };
	    }
}