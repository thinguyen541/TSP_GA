package GA;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import GA.utils.Constants;
import GUI.DrawFlowGraph;

public class Individual implements Comparable<Individual> {
	private ArrayList<Integer> path = new ArrayList<Integer>();
	private ArrayList<Integer> oldPath = new ArrayList<Integer>();//delete old path
	private double fitness;
	
	
	/**
	 * 
	 */
	public Individual() {
	}


	/**
	 * @param path
	 */
	public Individual(ArrayList<Integer> path) {
		this.path = path;
	}


	/**
	 * @param path
	 * @param fitness
	 */
	public Individual(ArrayList<Integer> path, float fitness) {
		this.path = path;
		this.fitness = fitness;
	}

	public Individual(Map map) {
		for (int i = 0; i < map.getCities().size();i++) {
			if (i != Constants.startIndex)
				this.path.add(i);
		}
		Collections.shuffle(this.path);
	}
	
	public Individual(Individual individual) {
		path = new ArrayList<>(individual.getPath());
	}
	
	
	public ArrayList<Integer> getPath() {
		return path;
	}


	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	
	public void setNode(int index, int value) {
		this.path.set(index, value);
	}
	
	

	public ArrayList<Integer> getOldPath() {
		return oldPath;
	}


	public void setOldPath(ArrayList<Integer> oldPath) {
		this.oldPath = oldPath;
	}


	public double getFitness() {
		return fitness;
	}


	public void setFitness(float fitness) {
		this.fitness = fitness;
	}
	

	//calculate fitness
	public void calculateFitness(Map map) {
		
		double distance = 0;
		
		//distance from start node to first node
		distance += map.distanceCalculate(Constants.startIndex, this.path.get(0));
		//distance from first node to last node
		int end = this.path.size() - 1;
		//start node is 0
		for (int i = 0; i < end  ;i++) {
			if(i == Constants.startIndex)
				continue;
			distance += map.distanceCalculate(this.path.get(i), this.path.get(i+1));
		}
		//distance from last node to start node
		int lastNodeIndex = this.path.size()-1;
		distance += map.distanceCalculate(Constants.startIndex, this.path.get(lastNodeIndex));
		
		this.fitness = distance;
	}
	
	public int compareTo(Individual o) {
		return (int)(this.fitness - o.getFitness());
	};
	
	
	public void drawEdges(Graphics graphics, Map map) {
		clearOldEdges(graphics, map);
		Graphics2D graphics2d = (Graphics2D) graphics;
		City startCity = map.getCity(Constants.startIndex);
		int x1,x2,y1,y2;
		x1=(int)startCity.getX();
		y1=(int)startCity.getY();
		x2=(int)map.getCity(path.get(0)).getX();
		y2=(int)map.getCity(path.get(0)).getY();
		graphics2d.setColor(Color.red);
		drawArrowLine(graphics,x1+8, y1+10, x2+8, y2+10,8,4);
		for(int i=0;i < path.size();i++) {
			City city1 = map.getCity(path.get(i));
			City city2 = new City();
			if (i == path.size()-1)
				city2 = startCity;
			else 
				city2 = map.getCity(path.get(i+1));
			
			x1=(int)city1.getX();
			y1=(int)city1.getY();
			x2=(int)city2.getX();
			y2=(int)city2.getY();
			graphics2d.setColor(Color.red);
			drawArrowLine(graphics,x1+8, y1+10, x2+8, y2+10,8,4);
		}
		
	}
	public void clearOldEdges(Graphics graphics, Map map) {
		if (oldPath != null) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		City startCity = map.getCity(Constants.startIndex);
		int x1,x2,y1,y2;
		x1=(int)startCity.getX();
		y1=(int)startCity.getY();
		x2=(int)map.getCity(oldPath.get(0)).getX();
		y2=(int)map.getCity(oldPath.get(0)).getY();

		graphics2d.setColor(Color.white);
		drawArrowLine(graphics,x1+8, y1+10, x2+8, y2+10,8,4);
		for(int i=0;i < oldPath.size();i++) {
			City city1 = map.getCity(oldPath.get(i));
			City city2 = new City();
			if (i == path.size()-1)
				city2 = startCity;
			else 
				city2 = map.getCity(oldPath.get(i+1));
			
			x1=(int)city1.getX();
			y1=(int)city1.getY();
			x2=(int)city2.getX();
			y2=(int)city2.getY();
			drawArrowLine(graphics,x1+8, y1+10, x2+8, y2+10,8,4);

		}
		}
	}
	
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String result= "<html>"+Constants.startIndex + " ";
		for(int i:path) {
			result= result+" " +i;
		}
		return result +" "+ Constants.startIndex+ "</html>";
	}
}
