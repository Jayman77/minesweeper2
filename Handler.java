import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {	
	LinkedList<GameObject> objects = new LinkedList<GameObject>();	// stores all game objects
	
	public void tick() {	// runs tick method of every game object
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObj = objects.get(i);
			tempObj.tick();
		}
	}
	
	public void render(Graphics g) {	// runs render method of every game object
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObj = objects.get(i);
			tempObj.render(g);
		}
	}
	
	public void addObject(GameObject object) {	// adds gameobject to list
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object) {	// removes game object from list
		this.objects.remove(object);
	}
	
	public void clear() {	// clears the list
		for(int i = objects.size() - 1; i >= 0; i--)
		{
			removeObject(objects.get(i));
		}
	}
}
