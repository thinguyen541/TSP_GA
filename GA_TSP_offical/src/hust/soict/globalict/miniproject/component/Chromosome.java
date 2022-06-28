package hust.soict.globalict.miniproject.component;

import java.util.ArrayList;

public abstract class Chromosome {
	protected ArrayList<Integer> path = new ArrayList<Integer>();
	protected double fitness;
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	abstract void calculateFitness();
	public Chromosome() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Integer> getPath() {
		return path;
	}
	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	

}
