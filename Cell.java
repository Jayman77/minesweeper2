import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Cell extends GameObject {
	@SuppressWarnings("unused")
	private Handler handler;
	private int cellSize = 20;
	
	public enum STATE {
		Hidden,
		Flagged,
		Showing
	}

	public STATE unpressed = STATE.Hidden;
	public STATE rClicked = STATE.Flagged;
	public STATE lClicked = STATE.Showing;
	
	public Cell(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}
	public int getCellSize() { return cellSize; }
	@Override
	public void render(Graphics g) {
		if(unpressed == STATE.Hidden) {
			g.setColor(Color.WHITE);
			g.fillRect((int)x, (int)y, cellSize, cellSize);
		} else if(unpressed == STATE.Showing) {
		
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, cellSize, cellSize);
	}

}
