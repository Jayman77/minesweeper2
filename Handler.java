import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	public void tick() {
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObj = objects.get(i);
			tempObj.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < objects.size(); i++)
		{
			GameObject tempObj = objects.get(i);
			tempObj.render(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.objects.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}
	
	public void clear() {
		for(int i = objects.size() - 1; i >= 0; i--)
		{
			removeObject(objects.get(i));
		}
	}
}
