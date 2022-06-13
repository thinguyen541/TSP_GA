package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;

import javax.swing.JPanel;

import GA.City;
import GA.Individual;
import GA.Map;
import GA.utils.Constants;

public class DrawFlowGraph extends JPanel{
	private ArrayList<Integer> path = new ArrayList<Integer>();
	private Map map = new Map();
	
	
	@Override
	public void paintComponent(Graphics g) {
		for (City city:map.getCities()) {
			int id= city.getIndex();
			float x = city.getX();
			float y = city.getY();
			if (id == Constants.startIndex)
				g.setColor(Color.magenta);
			else 
				g.setColor(Color.green);
			g.fillOval((int)x, (int)y, 15, 15);
			
			if (id == Constants.startIndex)
				g.setColor(Color.ORANGE);
			else 
				g.setColor(Color.magenta);
			g.drawString(String.valueOf(id),(int)x+13 , (int)y+2);
		}
	}
	
	public void repaintning(Graphics g) {
		Graphics graphics =g;
		for (City city:map.getCities()) {
			int id= city.getIndex();
			float x = city.getX();
			float y = city.getY();
			if (id == Constants.startIndex)
				graphics.setColor(Color.magenta);
			else 
				graphics.setColor(Color.green);
			graphics.fillOval((int)x, (int)y, 15, 15);
			
			if (id == Constants.startIndex)
				graphics.setColor(Color.ORANGE);
			else 
				graphics.setColor(Color.magenta);
			graphics.drawString(String.valueOf(id),(int)x+13 , (int)y+2);
		}
	}
	public ArrayList<Integer> getPath() {
		return path;
	}

	public void setPath(ArrayList<Integer> path) {
		this.path = path;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
}
