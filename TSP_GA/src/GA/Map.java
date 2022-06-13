package GA;

import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

public class Map{
	private ArrayList<City> cities =  new ArrayList<City>();
	
	public Map() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param nbCities
	 * @param cities
	 */
	public Map(ArrayList<City> cities) {
		this.cities = cities;
	}


	public ArrayList<City> getCities() {
		return cities;
	}

	public City getCity(int index) {
		return cities.get(index);
	}
	
	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}
	
	
	public void loadFromFile(File inputFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(inputFile);
		int index = 0;
		while(scanner.hasNextLine()) {
			String[] token = scanner.nextLine().split(" ");
			City city = new City(index, Float.parseFloat(token[0]), Float.parseFloat(token[1]));
			this.cities.add(city);
			index++;
		}
	}
	
	public double distanceCalculate(int Index1, int Index2) {
		
		float x1 = this.cities.get(Index1).getX();
		float y1 = this.cities.get(Index1).getY();
		float x2 = this.cities.get(Index2).getX();
		float y2 = this.cities.get(Index2).getY();
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); //euclidean distance
	}
	

}
