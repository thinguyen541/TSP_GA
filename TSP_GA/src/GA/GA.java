package GA;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingWorker;
import javax.swing.SwingUtilities;

import GA.operations.Crossover;
import GA.operations.Mutation;
import GA.utils.Factors;
import GUI.DrawFlowGraph;



public class GA {
	private Individual bestFitnessIndividual;
	private SwingWorker<Void, Integer> worker;
	
	public void execute(DrawFlowGraph graph,Map map,Graphics g) {
		int N = Factors.nbIndividual;//number of individual in a population
		//initial First population randomly
		Population population = new Population(N, map);
		//sort the Individual by fitness
		population.sortIndividual();
		//crossover and mutation
		for (int i = 0; i < Factors.nbGeneration;i++) {
			//calculate fitness
			for (Individual individual: population.getIndividuals()) {
				individual.calculateFitness(map);
			}
			//Crossover
			int nbCrossover = map.getCities().size(); // number of crossover is number of cities 
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
				offspring1.calculateFitness(map);
				offspring2.calculateFitness(map);
				//replace parent by offspring if offspring has better fitness
//				System.out.println(offsprings.get(0).getFitness() +"-"+ parent1.getFitness());
				if (offspring1.getFitness() < parent1.getFitness()) {
					population.setIndividual(p1,offspring1);
				}
				if (offspring2.getFitness() < parent2.getFitness()) {
					population.setIndividual(p2, offspring2);
				}
				
			}
			//get the old path to delete
			ArrayList<Integer> oldPath = null;
			if (this.bestFitnessIndividual != null ) {
				oldPath = this.bestFitnessIndividual.getPath();
			}
			//sort the Individual by fitness
			population.sortIndividual();
			graph.repaintning(g);
			this.bestFitnessIndividual = population.getIndividual(0);
			this.bestFitnessIndividual.setOldPath(oldPath);
			this.bestFitnessIndividual.drawEdges(g, map);
			System.out.println(bestFitnessIndividual.getFitness());
		}
		
	}
	
	public Individual getIbestFitnessIndividual() {
		return bestFitnessIndividual;
	}

	public GA() {
		// TODO Auto-generated constructor stub
	}
	
}
