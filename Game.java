import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	// Basic instance variables for Game
	// Various objects to run Game (Cell, Thread, Handler)
	private Cell defCell;
	private int Width = (defCell.getCellSize() + 5) * 9;
	private int Height = (defCell.getCellSize() + 5) * 9;
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	private int boardSize;
	private int numMines;
	boolean[][] boardMines;
	public int[][] minesTouching;
	Cell[][] cells;
	
	public void buildBoard()
	{
//		int dif = 1;        ***NOTE: will implement difficulty later***
//		if (dif == 1) {
			boardSize = 9;
			numMines = 10;
//		}
//		else if (dif == 2) { boardSize = 16; }
//		else { boardSize = 24; }
		boardMines = new boolean[boardSize][boardSize];
		minesTouching = new int[boardSize][boardSize];
		cells = new Cell[boardSize][boardSize];
		for (int r = 0; r < boardSize; r++) { for (int c = 0; c < boardSize; c++) { boardMines[r][c] = false; } }
		int minesLeft = numMines;
		while (minesLeft > 0)
		{
			int randX = (int)(Math.random() * boardSize);
			int randY = (int)(Math.random() * boardSize);
			if (!boardMines[randX][randY]) { // if spot in matrix doesn't already have a mine, adds one
				boardMines[randX][randY] = true;
				minesLeft--;
			}
		}
		buildAllBoards();
	}
	public boolean ifPositionExists(int r, int c) {
		// If r AND c are within the range, return true
		boolean ifXExists = r >= 0 && r < boardSize;
		boolean ifYExists = c >= 0 && c < boardSize;
		return ifXExists && ifYExists;
	}
	public void buildAllBoards() {
		// Initiates all mines in board to false
		for (int r = 0; r < boardSize; r++) { for (int c = 0; c < boardSize; c++) { boardMines[r][c] = false; } }
		int minesLeft = numMines;
		// Adds all mines at random positions until none left
		while (minesLeft > 0)
		{
			int randX = (int)(Math.random() * boardSize);
			int randY = (int)(Math.random() * boardSize);
			if (!boardMines[randX][randY]) { // if spot in matrix doesn't already have a mine, adds one
				boardMines[randX][randY] = true;
				minesLeft--;
			}
		}
		// Creates board to get number of mines touching at each spot
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				if (!boardMines[r][c]) // if position [r][c] isn't a mine...
				{
					// ifPositionExists() used to ensure no errors with matrix
					int count = 0;
					if (ifPositionExists(r - 1, c - 1) && boardMines[r - 1][c - 1]) {
						count++;
					}
					if (ifPositionExists(r - 1, c) && boardMines[r - 1][c]) {
						count++;
					}
					if (ifPositionExists(r - 1, c + 1) && boardMines[r - 1][c + 1]) {
						count++;
					}
					if (ifPositionExists(r, c - 1) && boardMines[r][c - 1]) {
						count++;
					}
					if (ifPositionExists(r, c) && boardMines[r][c]) {
						count++;
					}
					if (ifPositionExists(r, c + 1) && boardMines[r][c + 1]) {
						count++;
					}
					if (ifPositionExists(r + 1, c - 1) && boardMines[r + 1][c - 1]) {
						count++;
					}
					if (ifPositionExists(r + 1, c) && boardMines[r + 1][c]) {
						count++;
					}
					if (ifPositionExists(r + 1, c + 1) && boardMines[r + 1][c + 1]) {
						count++;
					}
					minesTouching[r][c] = count;
				}
				else
				{
					// If there is a mine at [r,c], sets value to 10
					minesTouching[r][c] = 10;
				}
			}
		}
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				// Creates x and y value for GUI
				int x = (r / 9) * 64;
				int y = (c % 9) * 64;
				// Creates Cell at each spot
				if (boardMines[r][c]) // if bool at boardMines[r][c] is true, it is a bomb
					cells[r][c] = new Cell(x,y,ID.Bomb,handler);
				else
					cells[r][c] = new Cell(x,y,ID.Number,handler);
					
			}
		}
	}
	public int getNumMines(int x, int y) {
		int r = (x / 64) * 9;
		int c = (y / 64) * 9;
		return minesTouching[r][c];
	}
	public Game() {
	  handler = new Handler();
	  buildBoard();
	  new Window(Width, Height, "Minesweeper", this);
	}
	
	// Starts Thread for Game to run
	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	// Ends Thread when Game is finished
	public void stop() {
		try
		{
			thread.join();
			running = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// calls tick and render method based on a timed system based on amount of tiks and fps
	public void run() {
		double delta = 0;
		this.requestFocus();
		int frames = 0;
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		long timer = System.currentTimeMillis();
		double ns = 1000000000 / amountOfTicks;
		
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1)
			{
				tick();
				delta--;
			}
			if (running)
			{
				render();
			}
			
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("Ticks: " + amountOfTicks + ", FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	// tick method to move objects around
	public void tick() {	}
	
	// allows game objects to be rendered onto the screen
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.dispose();
		bs.show();
	}
	
	// clamps objects to the screen
	public static float clamp(float var, float min, float max) {
		if(var >= max)
		{
			var = max;
			return var;
		}
		else if(var <= min)
		{
			var = min;
			return var;
		}
		else
		{
			return var;
		}
	}
	
	
	public static void main(String args[])
	{
		new Game();
	}
	
}
