package hust.soict.globalict.miniproject.component;

import java.util.ArrayList;
import java.util.Collections;

public class Population {

	private ArrayList<Individual> individuals = new ArrayList<Individual>();

	public Population() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param individuals
	 */
	public Population(ArrayList<Individual> individuals) {
		this.individuals = individuals;
	}
	
	//Initial poulation by map
	public Population(int nbIndividuals,Map map) {
		for(int i=0; i<nbIndividuals;i++) {
			this.individuals.add(new Individual(map));
		}
		Collections.sort(individuals);
	}
	
	//Initial poulation by another population
	public Population(int nbIndividuals, Population population) {
		for(int i=0; i<nbIndividuals;i++) {
			this.setIndividual(i, population.getIndividual(i));
		}
	}


	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}


	public void setIndividuals(ArrayList<Individual> individuals) {
		this.individuals = individuals;
	}
	
	public Individual getIndividual(int index) {
		return this.individuals.get(index);
	}
	
	public void setIndividual(int index,Individual individual) {
		this.individuals.set(index, individual);
	}
	
	public void sortIndividual() {
		Collections.sort(this.individuals);
	}

}
