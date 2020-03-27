
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x;	// x position of game object
	protected int y;	// y position of game object
	protected ID id;	// id of game object
	
	public GameObject(int x, int y, ID id) {	//Constructor
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void render(Graphics g);	// Abstract render method for on screen image
	public abstract Rectangle getBounds();	//Collision rectangle
	
	public int getX() {	// returns x position
		return x;
	}
	
	public void setX(int x) {	// sets x position
		this.x = x;
	}
	
	public int getY() {	// returns y position
		return y;
	}
	
	public void setY(int y) {	// sets y position
		this.y = y;
	}
	
	public ID getID() {	// returns game object ID
		return id;
	}
	
	public void setID(ID id) {	// sets game object ID
		this.id = id;
	}
}
