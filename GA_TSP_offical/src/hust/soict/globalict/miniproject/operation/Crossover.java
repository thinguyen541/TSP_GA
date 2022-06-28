package hust.soict.globalict.miniproject.operation;

import java.util.Random;

import hust.soict.globalict.miniproject.component.Individual;
import hust.soict.globalict.miniproject.component.Population;
import hust.soict.globalict.miniproject.utils.Factors;

public class Crossover implements GAoperator {
	@Override
	public void operate(Population population) {
		int N = Factors.nbIndividual;
		Random random = new Random();
		int p1_index = random.nextInt(N/2-1); //choose randomly 50% better fitness
		int p2_index = random.nextInt(N/2-1)+N/2; //choose randomly 50% worse fitness
		Individual parent1 = population.getIndividual(p1_index);
		Individual parent2 = population.getIndividual(p2_index);
		Individual offspring1 = new Individual(parent1);
		Individual offspring2 = new Individual(parent2);
		
		int length = parent1.getPath().size();
		random = new Random();
		int point = random.nextInt(length-1);
		int pos1 = point;
		int pos2 = point;
		
		for (int i=0; i<length ;i++) {
			if (pos1 >= length) 
				break;
			int p2 = parent2.getPath().get(i);
			for (int j = point; j<length ; j++) {
				int p1 = parent1.getPath().get(j);
				if (p1 == p2) {
					offspring1.setNode(pos1,p1);
					pos1++;
				}	
			}
		}
		
		for (int i=0; i<length ;i++) {
			if (pos2 >= length) 
				break;
			int p1 = parent1.getPath().get(i);
			for (int j = point; j<length ; j++) {
				int p2 = parent2.getPath().get(j);
				if (p1 == p2) {
					offspring2.setNode(pos2,p2);
					pos2++;
				}	
			}
		}
		offspring1.calculateFitness();
		offspring2.calculateFitness();
		//replace parent by offspring if offspring has better fitness
//		System.out.println(offsprings.get(0).getFitness() +"-"+ parent1.getFitness());
		if (offspring1.getFitness() < parent1.getFitness()) {
			population.setIndividual(p1_index,offspring1);
		}
		if (offspring2.getFitness() < parent2.getFitness()) {
			population.setIndividual(p2_index, offspring2);
		}
	}
}
