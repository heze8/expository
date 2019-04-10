package Adventure;

public class Player {
	private int posX;
	private int posY;
	
	/**
	 * Constructor, creates a player object
	 * @param x provides the starting x location of the player
	 * @param y provides the starting y location of the player
	 */
	public Player (int x, int y) {
		posX = x;
		posY = y;
	}
	
	/**
	 * Return the current x location of the player
	 * @return the current x location of the player
	 */
	public int getX () {
		return posX;
	}
	
	/**
	 * Return the current y location of the player
	 * @return the current y location of the player
	 */
	public int getY () {
		return posY;
	}
	
	/**
	 * Updates the location of the player
	 * @param dir, of type Direction, determines the way in which location of the layer is to be updated
	 */
	public void move (Direction dir) {
		switch (dir) {
		case UP: posY--; break;
		case DOWN: posY++; break;
		case LEFT: posX--; break;
		case RIGHT: posX++; break;
		}
	}
	
	private enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
}
