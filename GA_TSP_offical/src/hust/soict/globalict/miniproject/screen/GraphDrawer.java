package hust.soict.globalict.miniproject.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import hust.soict.globalict.miniproject.component.City;
import hust.soict.globalict.miniproject.component.Map;
import hust.soict.globalict.miniproject.utils.Constants;

public class GraphDrawer {
	private ArrayList<Integer> currentPath = new ArrayList<Integer>();
	private ArrayList<Integer> oldPath = new ArrayList<Integer>();//delete old path
	private Map map;
	JPanel pane;
	
	/**
	 * @param currenPath
	 * @param oldPath
	 * @param map
	 */
	public GraphDrawer(ArrayList<Integer> currentPath, ArrayList<Integer> oldPath, Map map, JPanel pane) {
		this.currentPath = currentPath;
		this.oldPath = oldPath;
		this.map = map;
		this.pane = pane;
	}
	
	
	public void drawVertices() {
		Graphics g = pane.getGraphics();
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
	
	public void drawEdges() {
		if (currentPath == null)
			return;
		Graphics graphics = pane.getGraphics();
		clearOldEdges();
		Graphics2D graphics2d = (Graphics2D) graphics;
		City startCity = map.getCity(Constants.startIndex);
		int x1,x2,y1,y2;
		x1=(int)startCity.getX();
		y1=(int)startCity.getY();
		x2=(int)map.getCity(currentPath.get(0)).getX();
		y2=(int)map.getCity(currentPath.get(0)).getY();
		graphics2d.setColor(Color.red);
		drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);
		for(int i=0;i < currentPath.size();i++) {
			City city1 = map.getCity(currentPath.get(i));
			City city2 = new City();
			if (i == currentPath.size()-1)
				city2 = startCity;
			else 
				city2 = map.getCity(currentPath.get(i+1));
			
			x1=(int)city1.getX();
			y1=(int)city1.getY();
			x2=(int)city2.getX();
			y2=(int)city2.getY();
			graphics2d.setColor(Color.red);
			drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);
		}
		
	}
	
	public void clearOldEdges() {
		Graphics graphics = pane.getGraphics();
		if (oldPath != null) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		City startCity = map.getCity(Constants.startIndex);
		int x1,x2,y1,y2;
		x1=(int)startCity.getX();
		y1=(int)startCity.getY();
		x2=(int)map.getCity(oldPath.get(0)).getX();
		y2=(int)map.getCity(oldPath.get(0)).getY();
		graphics2d.setColor(new Color(204, 255, 255));
		drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);
		for(int i=0;i < oldPath.size();i++) {
			City city1 = map.getCity(oldPath.get(i));
			City city2 = new City();
			if (i == oldPath.size()-1)
				city2 = startCity;
			else 
				city2 = map.getCity(oldPath.get(i+1));
			
			x1=(int)city1.getX();
			y1=(int)city1.getY();
			x2=(int)city2.getX();
			y2=(int)city2.getY();
			drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);

		}
		}
	}
	
	public void clear() {
		Graphics graphics = pane.getGraphics();
		for (City city:map.getCities()) {
			int id= city.getIndex();
			float x = city.getX();
			float y = city.getY();
			if (id == Constants.startIndex)
				graphics.setColor(new Color(204, 255, 255));
			else 
				graphics.setColor(new Color(204, 255, 255));
			graphics.fillOval((int)x, (int)y, 15, 15);
			
			if (id == Constants.startIndex)
				graphics.setColor(new Color(204, 255, 255));
			else 
				graphics.setColor(new Color(204, 255, 255));
			graphics.drawString(String.valueOf(id),(int)x+13 , (int)y+2);
		}
		//
		Graphics2D graphics2d = (Graphics2D) graphics;
		City startCity = map.getCity(Constants.startIndex);
		int x1,x2,y1,y2;
		x1=(int)startCity.getX();
		y1=(int)startCity.getY();
		x2=(int)map.getCity(currentPath.get(0)).getX();
		y2=(int)map.getCity(currentPath.get(0)).getY();
		graphics2d.setColor(new Color(204, 255, 255));
		drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);
		for(int i=0;i < currentPath.size();i++) {
			City city1 = map.getCity(currentPath.get(i));
			City city2 = new City();
			if (i == currentPath.size()-1)
				city2 = startCity;
			else 
				city2 = map.getCity(currentPath.get(i+1));
			
			x1=(int)city1.getX();
			y1=(int)city1.getY();
			x2=(int)city2.getX();
			y2=(int)city2.getY();
			graphics2d.setColor(new Color(204, 255, 255));
			drawArrowLine(graphics,x1+8, y1+7, x2+8, y2+7,8,4);
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
	
	
	

}
