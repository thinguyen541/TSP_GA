package hust.soict.globalict.miniproject.component;

import java.util.ArrayList;
import java.util.Collections;

import hust.soict.globalict.miniproject.utils.Constants;

public class Individual extends Chromosome implements Comparable<Individual> {
	private Map map;
	
	/**
	 * 
	 */
	public Individual() {
	}

	/**
	 * @param path
	 */
	public Individual(ArrayList<Integer> path) {
		path = path;
	}
	

	/**
	 * @param path
	 * @param fitness
	 * @param map
	 */
	public Individual(ArrayList<Integer> path, double fitness, Map map) {
		path= path;
		setFitness(fitness);
		this.map = map;
	}


	/**
	 * @param path
	 * @param fitness
	 */
	public Individual(ArrayList<Integer> path, float fitness) {
		path= path;
		setFitness(fitness);
	}

	public Individual(Map map) {
		this.map = map;
		for (int i = 0; i < this.map.getCities().size();i++) {
			if (i != Constants.startIndex)
				path.add(i);
		}
		Collections.shuffle(path);
	}
	
	public Individual(Individual individual) {
		this.map = individual.map;
		path = new ArrayList<>(individual.getPath());
	}
	

	public void setNode(int index, int value) {
		path.set(index, value);
	}
	
	

	//calculate fitness
	@Override
	public void calculateFitness() {
		double distance = 0;
		//distance from start node to first node
		distance += this.map.distanceCalculate(Constants.startIndex, path.get(0));
		//distance from first node to last node
		int end = this.path.size() - 1;
		//start node is 0
		for (int i = 0; i < end  ;i++) {
			if(i == Constants.startIndex)
				continue;
			distance += this.map.distanceCalculate(path.get(i), path.get(i+1));
		}
		//distance from last node to start node
		int lastNodeIndex = path.size()-1;
		distance += this.map.distanceCalculate(Constants.startIndex, path.get(lastNodeIndex));
		
		setFitness(distance);
	}
	
	public int compareTo(Individual o) {
		return (int)(getFitness() - o.getFitness());
	};
	
	public String toString() {
		// TODO Auto-generated method stub
		String result= "<html>"+Constants.startIndex + " ";
		for(int i:path) {
			result= result+" " +i;
		}
		return result +" "+ Constants.startIndex+ "</html>";
	}

}
