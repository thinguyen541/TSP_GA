package hust.soict.globalict.miniproject.GenerticAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hust.soict.globalict.miniproject.component.Individual;
import hust.soict.globalict.miniproject.component.Map;
import hust.soict.globalict.miniproject.component.Population;
import hust.soict.globalict.miniproject.operation.Crossover;
import hust.soict.globalict.miniproject.operation.Mutation;
import hust.soict.globalict.miniproject.screen.GraphDrawer;
import hust.soict.globalict.miniproject.utils.Constants;
import hust.soict.globalict.miniproject.utils.Factors;

public class GAexecution extends Thread {
	private JPanel pane;
	private Map map;
	private GraphDrawer displayGraph;
	private JTextField textGeneration;
	private JLabel lblcurrentPath;
	private JTextField textfitness;
	private long sleepTime = 0;
	
	

	
	
	

	/**
	 * @param pane
	 * @param map
	 * @param displayGraph
	 * @param textGeneration
	 * @param lblcurrentPath
	 * @param textfitness
	 */
	public GAexecution(JPanel pane, Map map, GraphDrawer displayGraph, JTextField textGeneration,
			JLabel lblcurrentPath, JTextField textfitness) {
		this.pane = pane;
		this.map = map;
		this.displayGraph = displayGraph;
		this.textGeneration = textGeneration;
		this.lblcurrentPath = lblcurrentPath;
		this.textfitness = textfitness;
	}


	

	public long getSleepTime() {
		return sleepTime;
	}




	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}




	public JPanel getPane() {
		return pane;
	}



	public void setPane(JPanel pane) {
		this.pane = pane;
	}



	public Map getMap() {
		return map;
	}



	public void setMap(Map map) {
		this.map = map;
	}



	public GraphDrawer getDisplayGraph() {
		return displayGraph;
	}



	public void setDisplayGraph(GraphDrawer displayGraph) {
		this.displayGraph = displayGraph;
	}



	public void run() {
		//path cu
		ArrayList<Integer> oldPath = null;
		int N = Factors.nbIndividual;//number of individual in a population
		// khoi tao quan the ngau nhien va tinh fitness 
		Population population = new Population(N, this.map);
		for (Individual individual: population.getIndividuals()) {
			individual.calculateFitness();
		}
		//lai ghep va dot biet
		for (int i = 0; i < Factors.nbGeneration;i++) {
			//calculate fitness
			for (Individual individual: population.getIndividuals()) {
				individual.calculateFitness();
			}
			int nbCrossover = map.getCities().size(); // number of crossover is number of cities 
			for(int j=0; j < nbCrossover ;j++) {
 				Crossover crossover = new  Crossover();
				crossover.operate(population);
			}
			for(int j=0; j < nbCrossover ;j++) {
				Random rand = new Random();
				float rMutation	= rand.nextFloat(1);
				if (rMutation < Factors.mutationRate) {
					Mutation mutation = new  Mutation();
					mutation.operate(population);
				}
			}
			//sort lai quan the moi
			population.sortIndividual();
			//lay ra ca the co fitness tot nhat
			Individual bestIndividual  = population.getIndividual(0);
			System.out.println(bestIndividual.getFitness());
			//display
			displayGraph = new GraphDrawer(bestIndividual.getPath(), oldPath, map, pane);
				displayGraph.drawVertices();
				displayGraph.drawEdges();
			oldPath = bestIndividual.getPath();
			textfitness.setText(String.valueOf(bestIndividual.getFitness()));
			textGeneration.setText(String.valueOf(i));
			lblcurrentPath.setText(bestIndividual.toString());
			try {
				sleep(200-sleepTime*2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
