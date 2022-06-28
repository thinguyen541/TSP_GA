package hust.soict.globalict.miniproject.component;

public class City {
	
	
	private int index;
	private float x;
	private float y;
	
	public City() {
	}
	
	public City(int index,float x, float y) {
		this.x = x;
		this.y = y;
		this.index = index;
	}
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


}
