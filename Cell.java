import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Cell extends GameObject {
	private Handler handler;
	private Game game;
	private int cellSize = 20;
	
	// enum to determine state of Cell
	public enum STATE {
		Hidden,
		Flagged,
		Showing
	}
	
	// Changes STATE based on user action
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
		if (unpressed == STATE.Hidden) { // NOTE: need user input for parameter(buttonClicked)
			// If hidden, makes empty rectangle
			g.setColor(Color.WHITE);
			g.fillRect((int)x, (int)y, cellSize, cellSize);
		} else {
			// If showing
			String numTouching = Integer.toString(game.getNumMines(x,y));
			g.drawString(numTouching,x,y);
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, cellSize, cellSize);
	}

}
