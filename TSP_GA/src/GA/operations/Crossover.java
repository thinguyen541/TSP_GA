package GA.operations;

import java.util.ArrayList;
import java.util.Random;

import GA.Individual;
import GA.Map;

public class Crossover {
	
	public ArrayList<Individual> SPX(Individual parent1, Individual parent2) {
		Individual offspring1 = new Individual(parent1);
		Individual offspring2 = new Individual(parent2);
		
		int length = parent1.getPath().size();
		Random random = new Random();
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
		ArrayList<Individual> offsprings = new ArrayList<Individual>();
		offsprings.add(offspring1);
		offsprings.add(offspring2);
		return offsprings;		
	}
}
