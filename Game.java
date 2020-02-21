import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

public class Game extends Canvas implements Runnable, ActionListener {
	private Cell defCell;
	private int Width = (defCell.getCellSize() + 5) * 9;
	private int Height = (defCell.getCellSize() + 5) * 9;
	private Thread thread;
	private Handler handler;
	private boolean running = false;
	private int boardSize;
	private int numMines;
	boolean[][] boardMines;
	int[][] minesTouching;
	Scanner sc = new Scanner(System.in);
	Cell[][] cells;   // enum attempt
	
	public void buildBoard()
	{
//		int dif = 1;        ***NOTE: will implement difficulty later**
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
		buildBoards();
	}
	// jay's attempt ------------------------------------------------------------------------------------------------
	public boolean ifPositionExists(int r, int c) {
		boolean ifXExists = r >= 0 && r < boardSize;
		boolean ifYExists = c >= 0 && c < boardSize;
		return ifXExists && ifYExists;
	}
	public void buildBoards() {
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
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				if (!boardMines[r][c]) // if position [r][c] isn't a mine...
				{
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
					minesTouching[r][c] = 10;
				}
			}
		}
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				int x = (r / 9) * 64;
				int y = (c % 9) * 64;
				if (boardMines[r][c]) // if bool at boardMines[r][c] is true, it is a bomb
					cells[r][c] = new Cell(x,y,ID.Bomb,handler);
				else
					cells[r][c] = new Cell(x,y,ID.Number,handler);
					
			}
		}
	}
	// jay's attempt -------------------------------------------------------------------------------------------------
	public Game() {
	  handler = new Handler();
		new Window(Width, Height, "Minesweeper", this);
		//handler.addObject();
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
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
	
	public void tick() {	}
	
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
	
	public static void main(String args[])
	{
		new Game();
	}
	
}
