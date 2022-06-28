package hust.soict.globalict.miniproject.operation;

import java.util.Random;

import hust.soict.globalict.miniproject.component.Individual;
import hust.soict.globalict.miniproject.component.Population;
import hust.soict.globalict.miniproject.utils.Factors;

public class Mutation implements GAoperator{

	@Override
	public void operate(Population population) {
		int nbIndividual = Factors.nbIndividual;
		Random random = new Random();
		int p_index = random.nextInt(nbIndividual); //choose randomly 50% better fitness
		Individual parent = population.getIndividual(p_index);
		int N = parent.getPath().size();
		int point1 = random.nextInt(N);
		int point2 = random.nextInt(N);
		if(point1 == point2) {
			point2 = random.nextInt(N);
		}
		Individual offspring = new Individual(parent);
		int p1 = parent.getPath().get(point1);
		int p2 = parent.getPath().get(point2);
		offspring.setNode(point1, p2);
		offspring.setNode(point2, p1);
		offspring.calculateFitness();
		if (offspring.getFitness() < parent.getFitness()) {
			population.setIndividual(p_index, offspring);
		}
	}

}
