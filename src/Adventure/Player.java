package Adventure;

import ExpositoryConstant.ExpositoryConstant.Direction;

public class Player {
	private int posX;
	private int posY;	
	private Direction mostRecentMove = null;
	
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
		
		System.out.println("Player is now at " + posX + ", " + posY);
	}
	
	/**
	 * Sets the most recent move of the player
	 * @param dir of enum Direction in ExpositoryConstant, provides the most 
	 * recent direction the player moved in.
	 */
	public void setMostRecentMove(Direction dir) {
		mostRecentMove = dir;
	}

	/**
	 * Returns the most recent move of the player
	 * @return the most recent move made by the player
	 */
	public Direction getMostRecentMove() {
		return mostRecentMove;
	}
	
}
