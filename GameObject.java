
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x;
	protected int y;
	protected ID id;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();	//Collision rectangle
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public ID getID() {
		return id;
	}
	
	public void setID(ID id) {
		this.id = id;
	}
}
