package GA.operations;

import java.util.Random;

import GA.Individual;

public class Mutation {
	public Individual swap(Individual parent){
		int N = parent.getPath().size();
		Random random = new Random(); 
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
		return offspring;
		
	}

}
