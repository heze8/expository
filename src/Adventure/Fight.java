
package Adventure;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;
import javax.swing.Timer;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import ExpositoryConstant.ExpositoryConstant.*;
import GUI.Story;
import GameEvents.RandomDustEvents;

public class Fight extends Story implements ExpositoryConstant {
	private List<String> shotString = Arrays.asList("You let loose a shot"
			,"You fired the gun" 
			,"You rose from your cover and risked a shot");
	private List<String> punchString = Arrays.asList("You threw a punch"
			 ,"You drove your fist straight ahead");
	
	private List<String> upString = Arrays.asList("You moved forward"
			 ,"You took a step forward");
	private List<String> downString = Arrays.asList("You moved backwards"
			 ,"You backed off");
	private List<String> leftString = Arrays.asList("You moved to the left"
			,"You took a shuffle step towards the left");
	private List<String> rightString = Arrays.asList("You moved to the right"
			,"You took a shuffle step towards the right");
	private Player player;
	private Player enemy;
	private boolean underCover = false;
	private boolean fightOver = false;
	private int hits = 0;
	private int enemyHits = 0;
	FightStuff [][] fightGrid;
	
	public Fight() {
		super ();
		super.setFade(false);
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
	    super.setPreferredSize(new Dimension(super.getPreferredSize().width, super.getPreferredSize().height));
	}
	
	public static boolean gotFight() {
		double fight = Math.random();
		return fight < 0.1;
	}
	
	/**
	 * Sets up a array which represents the grid for combat
	 */
	public void generateFight() {
		removeAll();
		displayText("A deranged man approaches you with a knife in one hand and a gun in the other");
		generatebattleField();
		fightOver = false;
		setKeyBindings();
		Timer enemyMove = new Timer(5 * SEC_TO_MSEC, null);
		enemyMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (fightOver) {
					enemyMove.stop();
					return;
				}
				makeEnemyMove();
				updateEnemyLocation();
			}
		});
		enemyMove.start();
	}

	private void generatebattleField() {
		fightGrid = new FightStuff [BATTLEFIELD_WIDTH][BATTLEFIELD_HEIGHT];
		
		// Initializing the obstacles
		for (int i = 0; i < (0.3 * BATTLEFIELD_HEIGHT * BATTLEFIELD_WIDTH) / 6; i ++) {
			int obstacleXPos = ThreadLocalRandom.current().nextInt(1, BATTLEFIELD_WIDTH);
			int obstacleYPos = ThreadLocalRandom.current().nextInt(1, BATTLEFIELD_HEIGHT);
			System.out.println("new obstacle at XPos: " + obstacleXPos + ", YPos: " + obstacleYPos);
			drawObstacle(obstacleXPos, obstacleYPos);
		}
		
		// Initializing the player and opponent
		int playerXPos = (int) (Math.random() * BATTLEFIELD_WIDTH);
		player = new Player (playerXPos, BATTLEFIELD_HEIGHT - 2);
		fightGrid[player.getY()][player.getX()] = FightStuff.PLAYER;
		
		
		int enemyXPos = (int) (Math.random() * BATTLEFIELD_WIDTH);
		enemy = new Player(enemyXPos, 1);
		fightGrid[enemy.getY()][enemy.getX()] = FightStuff.ENEMY;
		
		// printing the grid to check
		for (int i = 0; i < BATTLEFIELD_HEIGHT; i ++) {
			String toPrint = "";
			for (int j = 0; j < BATTLEFIELD_WIDTH; j ++) {
				toPrint += fightGrid[i][j]+ ", ";
			}
			System.out.println(toPrint);
		}
	}

	private void drawObstacle(int obstacleXPos, int obstacleYPos) {
		FightStuff obstacle;
		while (true) {
			obstacle = FightStuff.getRandomFightItem();
			System.out.println("Got obstacle: " + obstacle);
			if (obstacle != FightStuff.PLAYER && obstacle != FightStuff.ENEMY) break;
		}
		
		int lengthPlaced = 0;
		while (freeSpaceInRow(obstacleYPos) && lengthPlaced < obstacle.getSize()) {
			System.out.println(fightGrid[obstacleYPos][obstacleXPos]);
			
			// Only places object if there is nothing in the path
			if (fightGrid[obstacleYPos][obstacleXPos] == null) {
				fightGrid[obstacleYPos][obstacleXPos] = obstacle;
				lengthPlaced++;
				
				// Attempts to add remaining obstacle to the right first, if can't, add to left
				if (obstacleXPos < BATTLEFIELD_WIDTH - 1 
						&& fightGrid[obstacleYPos][obstacleXPos + 1] == null) {
					obstacleXPos += 1;
				} 
				else if (obstacleXPos > 0 
						&& fightGrid[obstacleYPos][obstacleXPos - 1] == null) {
					obstacleXPos -= 1;
				}
			}
			
			// Randomizes x coordinate again if there is not free space
			else obstacleXPos = ThreadLocalRandom.current().nextInt(1, BATTLEFIELD_WIDTH);
		}
	}

	private boolean freeSpaceInRow(int obstacleYPos) {
		int freeSpace = 0;
		for (int i = 0; i < BATTLEFIELD_WIDTH; i ++) {
			if (fightGrid[obstacleYPos][i] == null) {
				freeSpace++;
			}
		}
		return freeSpace > 3;
	}

	
	private void setKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), Actions.MOVE.getName());		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), Actions.MOVE.getName());		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), Actions.MOVE.getName());
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), Actions.MOVE.getName());
		actionMap.put(Actions.MOVE.getName(), new KeyAction(Actions.MOVE.getName()));
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), Actions.SHOOT.getName());
		actionMap.put(Actions.SHOOT.getName(), new KeyAction(Actions.SHOOT.getName()));
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0, true), Actions.MELEE.getName());
		actionMap.put(Actions.MELEE.getName(), new KeyAction(Actions.MELEE.getName()));
	}
	
	private void removeKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);
		
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true));		
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true));		
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true));
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true));
		actionMap.remove(Actions.MOVE.getName());
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true));
		actionMap.remove(Actions.SHOOT.getName());
		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0, true));
		actionMap.remove(Actions.MELEE.getName());
	}
	
	/**
	 * Handles the key input of the user
	 */
	private class KeyAction extends AbstractAction {
		private String action;
		public KeyAction(String action) {
			System.out.println("Added action: " + action);
			this.action = action;
		}
		
		/**
		 * Fired every time the user presses a key of interest
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			messages.clear();
			switch(e.getActionCommand()) {
			// Move player up
			case "w":
				if (canMove(Moves.UP, player)) {
					player.move(Direction.UP);
					updateFigthGrid(Direction.UP, player, FightStuff.PLAYER);
					displayText(upString.get(ThreadLocalRandom.current().nextInt(1, upString.size())));
					updateSurrounding();
				}
				else if (player.getY() - 1 >= 0) {
					displayText("There's a " + fightGrid[player.getY() - 1][player.getX()].getName() + " in front of you");	
				}
				else {
					displayText("There's nowhere left to go up ahead");
				}

				break;
				
			// Move player down
			case "s":
				if (canMove(Moves.DOWN, player)) {
					player.move(Direction.DOWN);
					updateFigthGrid(Direction.DOWN, player, FightStuff.PLAYER);
					displayText(downString.get(ThreadLocalRandom.current().nextInt(1, downString.size())));
					updateSurrounding();
				}
				else if (player.getY() + 1 < BATTLEFIELD_HEIGHT) {
					displayText("There's a " + fightGrid[player.getY() + 1][player.getX()].getName() + " behind you");
				}
				else {
					displayText("You retreated from the fight");
					Resources.dustCL.show(Resources.dustContainer, MAP);
					fightOver = true;
				}
				if (player.getY() == BATTLEFIELD_HEIGHT - 1) {
					displayText("Another step backwards and you can retreat from the fight");
				}
				break;			
			
			// Move player left	
			case "a":
				if (canMove(Moves.LEFT, player)) {
					player.move(Direction.LEFT);
					updateFigthGrid(Direction.LEFT, player, FightStuff.PLAYER);
					displayText(leftString.get(ThreadLocalRandom.current().nextInt(1, leftString.size())));
					updateSurrounding();
				}
				else if (player.getX() - 1 >= 0) {
					displayText("There's a " + fightGrid[player.getY()][player.getX() - 1].getName() + " to your left");
				}
				else {
					displayText("There's nowhere else to go on your left");
				}
				break;
				
			// Move player right
			case "d":
				if (canMove(Moves.RIGHT, player)) {
					player.move(Direction.RIGHT);
					updateFigthGrid(Direction.RIGHT, player, FightStuff.PLAYER);
					displayText(rightString.get(ThreadLocalRandom.current().nextInt(1, rightString.size())));
					updateSurrounding();
				}
				else if (player.getX() + 1 < BATTLEFIELD_WIDTH) {
					displayText("There's a " + fightGrid[player.getY()][player.getX() + 1].getName() + " to your right");
				}
				else {
					displayText("There's no where else to go on the right");
				}
				break;
				
			// Player attack square in front	
			case "o":
				int toDisplayPunch = ThreadLocalRandom.current().nextInt(1, punchString.size());
				displayText(punchString.get(toDisplayPunch));
				updateAttackStatus(Actions.MELEE);
				break;
			
			// Player shot, line in front affected
			case "p":
				int toDisplayShoot = 0;
				if (underCover) {
					toDisplayShoot = shotString.size() - 1;
				}
				else {
					toDisplayShoot = ThreadLocalRandom.current().nextInt(1, shotString.size() - 1);
				}
				displayText(shotString.get(toDisplayShoot));
				updateAttackStatus(Actions.SHOOT);
				break;
			}
			
		}

		private void updateAttackStatus(Actions action) {
			switch (action) {
			case MELEE:
				if (withinRange(player,enemy)) {
					if (++hits == 2) {
						displayText("The enemy lies on the ground, unmoving after the blow from your fist");
						lootAndReturn();
						fightOver = true;
						removeKeyBindings();
						return;
					}
					else {
						displayText("The enemy took a hit and stumbled back");
						return;	
					}
					
				}
				else {
					displayText("There is nothing around you");
				}
				break;
			case SHOOT:
				if(shootable(player, enemy)) {
					double dead = Math.random();
					if (dead < 0.9) {
						displayText("Your shot found its mark.");
						displayText("The enemy lays dead on the floor.");
						lootAndReturn();
						fightOver = true;
						removeKeyBindings();
						return;
					}
					else {
						displayText("Your shot went wild, missing your enemy");
					}
				}
				displayText("Your shot rang through the air, finding nothing");
				break;
			default:
				break;
			}
		}

		private void lootAndReturn() {
			RandomDustEvents.rngLoot(LootSource.WINNING_FIGHTS);
			Timer returnToMap = new Timer (3 * SEC_TO_MSEC, new ActionListener () {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Resources.dustCL.show(Resources.dustContainer, MAP);
				}
			});
			returnToMap.setRepeats(false);
			returnToMap.start();
		}

		private void updateSurrounding() {
			// if player is under cover
			if (isUnderCover(player)) {
				displayText("You took cover from behind a " + fightGrid[player.getY() - 1][player.getX()].getName());
				underCover = true;
				return;
			}
			
			// If player is not under cover
			for (int i = player.getY() - 1; i < player.getY() + 2; i++) {
				for (int j = player.getX() - 1; j < player.getX() + 2; j++) {
					// Skips the squares that are outside of boundaries
					if (i < 0 || i >= BATTLEFIELD_HEIGHT
							|| j < 0 || j >= BATTLEFIELD_WIDTH) continue;
					
					if (fightGrid[i][j] != null) {
						// skips if the obstacle is the player itself
						if (i == player.getY() && j == player.getX()) continue;
						
						Position obstacle = getPosition(j, i);
						displayText("A " + fightGrid[i][j].getName() + " lay to your " + obstacle.getName());
						underCover = false;
						
					}

				}
			}
		}	
	}
	
	private Position getPosition(int x, int y) {
		int xDisp = x - player.getX();
		int yDisp = y - player.getY();
		if (xDisp < 0) {
			if (yDisp > 0)
				return Position.BACK_LEFT;
			if (yDisp == 0)
				return Position.LEFT;
			if (yDisp <0)
				return Position.FRONT_LEFT;
		}
		if (xDisp == 0) {
			if (yDisp > 0)
				return Position.BACK;
			if (yDisp < 0)
				return Position.FRONT;
		}
		if (xDisp > 0) {
			if (yDisp > 0)
				return Position.BACK_RIGHT;
			if (yDisp == 0)
				return Position.RIGHT;
			if (yDisp < 0)
				return Position.FRONT_RIGHT;
		}
		return null;
	}

	private void updateEnemyLocation() {
		Position enemyPos = getPosition(enemy.getX(), enemy.getY());
		displayText("The enemy is in your " + enemyPos.getName() + " area");
	}

	private boolean shootable(Player attacker, Player defender) {
		if (attacker == player) {
			for (int i = 0; i < attacker.getY(); i ++) {
				if(fightGrid[i][attacker.getX()] == FightStuff.ENEMY) {
					return true;
				}
			}
		}
		
		else {
			for (int i = attacker.getY(); i < BATTLEFIELD_HEIGHT; i ++) {
				if(fightGrid[i][attacker.getX()] == FightStuff.PLAYER) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean withinRange(Player attacker, Player defender) {
		if (attacker == player && attacker.getY() - 1 >= 0) {
			for (int i = player.getX() - 1; i <= player.getX() + 1; i ++) {
				if (i >= 0 && i < BATTLEFIELD_WIDTH) {
					if (fightGrid[player.getY() - 1][i] == FightStuff.ENEMY)
						return true;
				}
			}
		}
		else if (attacker == enemy && attacker.getY() + 1 < BATTLEFIELD_HEIGHT) {
			for (int i = enemy.getX() - 1; i <= enemy.getX() + 1; i ++) {
				if (i >= 0 && i < BATTLEFIELD_WIDTH) {
					if (fightGrid[enemy.getY() + 1][i] == FightStuff.PLAYER)
						return true;
				}
			}
		}
		return false;
	}

	private void updateFigthGrid(Direction dir, Player person, FightStuff personality) {
		switch(dir) {
		case UP:
			fightGrid[person.getY() + 1][person.getX()] = null;
			fightGrid[person.getY()][person.getX()] = personality;
			break;
		case DOWN:
			fightGrid[person.getY() -1][person.getX()] = null;
			fightGrid[person.getY()][person.getX()] = personality;
			break;
		case LEFT:
			fightGrid[person.getY()][person.getX() + 1] = null;
			fightGrid[person.getY()][person.getX()] = personality;
			break;
		case RIGHT:
			fightGrid[person.getY()][person.getX() - 1] = null;
			fightGrid[person.getY()][person.getX()] = personality;
			break;
		default:
			break;
		}
		
		// printing the grid to check
		for (int i = 0; i < BATTLEFIELD_HEIGHT; i ++) {
			String toPrint = "";
			for (int j = 0; j < BATTLEFIELD_WIDTH; j ++) {
				toPrint += fightGrid[i][j]+ ", ";
			}
			System.out.println(toPrint);
		}
	}

	private boolean isUnderCover(Player personTakingCover) {
		if (personTakingCover == player) {
			if (player.getY() - 1 < 0) return false;
			if (fightGrid[player.getY() - 1][player.getX()] != null 
					&& fightGrid[player.getY() - 1][player.getX()] != FightStuff.ENEMY) return true;
			return false;
		}
		else {
			if (personTakingCover.getY() + 1 >= BATTLEFIELD_HEIGHT) return false;
			if (fightGrid[personTakingCover.getY() + 1][personTakingCover.getX()] != null 
					&& fightGrid[personTakingCover.getY() + 1][personTakingCover.getX()] != FightStuff.PLAYER)
				return true;
			return false;
		}
	}


	private void makeEnemyMove() {
		Moves bestmove = (Moves) getBestMove(Moves.SHOOT, 1, true).get(MOVE);
		System.out.print("Enenmy thinks that the best move is: " +bestmove + "\n");
		switch (bestmove) {
		case UP:
			if (canMove(Moves.UP, enemy)) {
				enemy.move(Direction.UP);
				updateFigthGrid(Direction.UP, enemy, FightStuff.ENEMY);
				displayText("The enemy backed off from you");
			}
			break;
		case DOWN:
			if (canMove(Moves.DOWN, enemy)) {
				enemy.move(Direction.DOWN);
				updateFigthGrid(Direction.DOWN, enemy, FightStuff.ENEMY);
				displayText("The enemy advances on you");
			}
			break;
		case LEFT:
			if (canMove(Moves.LEFT, enemy)) {
				enemy.move(Direction.LEFT);
				updateFigthGrid(Direction.LEFT, enemy, FightStuff.ENEMY);
				displayText("The enemy moved to your left");
			}
			break;
		case RIGHT:
			if (canMove(Moves.RIGHT, enemy)) {
				enemy.move(Direction.RIGHT);
				updateFigthGrid(Direction.RIGHT, enemy, FightStuff.ENEMY);
				displayText("The enemy moved to your right");
			}
			break;
		case MELEE:
			displayText("The enemy throws a punch");
			if (withinRange(enemy, player)) {
				if (++enemyHits == 2) {
					displayText("The blow from your enemy's attack knocked you out");
					returnToBase();
					fightOver = true;
					removeKeyBindings();
					return;
				}
				else {
					displayText("You stumbled back from the impact");
					return;	
				}
			}
			else {
				displayText("It cataches only air");
			}
			break;
		case SHOOT:
			displayText("The enemy fires a shot");
			if(shootable(player, enemy)) {
				double dead = Math.random();
				if (dead < 0.6) {
					displayText("The enemy's shot found its mark.");
					displayText("Your vision turns to black");
					returnToBase();
					fightOver = true;
					removeKeyBindings();
					return;
				}
				else {
					displayText("The shot went wild, narrowly missing you");
				}
			}
			displayText("The shot whizzes by in a distance");
			break;
		default:
			break;
		
		}
	}
	
	
	
	private void returnToBase() {
		Timer returnToMap = new Timer (4 * SEC_TO_MSEC, new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				Resources.controllerCL.show(Resources.buttonContainer, YOUR_ROOM);
				Resources.dustCL.show(Resources.dustContainer, MAP);
			}
		});
		returnToMap.setRepeats(false);
		returnToMap.start();
	}



	private int SCORE = 1;
	private int MOVE = 0;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getBestMove(Moves move, int depth, boolean isEnemy) {
		Moves bestMove = null;
		int minVal = 200;
		int maxVal = -200;

		if (depth == 0) {
			return evalPosition(move, !isEnemy); 
		}
		
		else {
			for (Moves futureAction : Moves.values()) {
				if (isEnemy) {
					Vector eval = getBestMove(futureAction, depth - 1, false);
					if ((int) eval.get(SCORE) > maxVal) {
						maxVal = (int) eval.get(SCORE);
						bestMove = (Moves) eval.get(MOVE);
					}
				}
				
				else {
					Vector eval = getBestMove(futureAction, depth - 1, true);
					if ((int) eval.get(SCORE) < minVal) {
						minVal = (int) eval.get(SCORE);
						bestMove = (Moves) eval.get(MOVE);
					}
				}
			}
		}
		
		if (isEnemy) {
			Vector results = new Vector();
			results.add(bestMove);
			results.add(maxVal);
			return results;
		}
		else {
			Vector results = new Vector();
			results.add(bestMove);
			results.add(minVal);
			return results;
		}
	}

	@SuppressWarnings("unchecked")
	private Vector evalPosition(Moves evalAction, boolean isEnemy) {
		if (isEnemy) {
			Vector results = new Vector();
			results.add(evalAction);
			boolean moved = false;
			switch (evalAction) {
			case DOWN:
				if (canMove(Moves.DOWN, enemy)) {
					enemy.move(Direction.DOWN);
					moved = true;
				}
				if (isUnderCover(enemy)) {
					results.add(30);
				}
			    if (gettingCloser(evalAction, enemy)) {
					results.add(50);
				}
				else {
					results.add(-30);
				}
				if (moved) {
					enemy.move(Direction.UP);
					moved = false;
				}
				break;
			case LEFT:
				if (canMove(Moves.LEFT, enemy)) {
					enemy.move(Direction.LEFT);
					moved = true;
				}
				if (isUnderCover(enemy)) {
					results.add(30);
				}
			    if (gettingCloser(evalAction, enemy)) {
					results.add(50);
				}
				else {
					results.add(-30);
				}
				if (moved) {
					moved = false;
					enemy.move(Direction.RIGHT);
				}
				break;
			case RIGHT:
				if (canMove(Moves.RIGHT, enemy)) {
					enemy.move(Direction.RIGHT);
					moved = true;
				}
				if (isUnderCover(enemy)) {
					results.add(30);
				}
			    if (gettingCloser(evalAction, enemy)) {
					results.add(50);
				}
				else {
					results.add(-30);
				}
				if (moved) {
					moved = false;
					enemy.move(Direction.LEFT);
				}
				break;
			case UP:
				if (canMove(Moves.UP, enemy)) {
					moved = true;
					enemy.move(Direction.UP);
				}
				if (isUnderCover(enemy)) {
					results.add(30);
				}
			    if (gettingCloser(evalAction, enemy)) {
					results.add(50);
				}
				else {
					results.add(-30);
				}
				if (moved) {
					moved = false;
					enemy.move(Direction.DOWN);
				}
				break;
			case MELEE:
				if (withinRange(enemy, player)) {
					results.add(50);
				} 
				else {
					results.add(-100);
				}
				break;
			case SHOOT:
				if (shootable(enemy, player)) {
					results.add(100);
				}
				else {
					results.add(-100);
				}
				break;
			default:
				System.out.print("default for enemy being reach.");
				break;
			}
			return results;
		}
		
		else {
			Vector results = new Vector();
			results.add(evalAction);
			switch (evalAction) {
			case DOWN:
			case LEFT:
			case RIGHT:
			case UP:
				if (isUnderCover(player)) {
					results.add(-30);
				}
				if (gettingCloser(evalAction, player)) {
					results.add(50);
				}
				else {
					results.add(30);
				}
				break;
			case MELEE:
				if (withinRange(player, enemy)) {
					results.add(-50);
				} 
				else {
					results.add(50);
				}
				break;
			case SHOOT:
				if (shootable(player, enemy)) {
					results.add(-100);
				}
				else {
					results.add(100);
				}
				break;
			default:
				System.out.print("default for player being reach.");
				break;
			}
			return results;
		}
	}

	private boolean gettingCloser(Moves evalAction, Player movingEntity) {
		int movedX = movingEntity.getX();
		int movedY = movingEntity.getY();
		switch (evalAction) {
		case DOWN:
			movedY += 1;
			break;
		case LEFT:
			movedX -= 1;
			break;
		case RIGHT:
			movedX += 1;
			break;
		case UP:
			movedY -= 1;
			break;
		default:
			break;
		}
		
		Player otherParty = null;
		if (movingEntity == player)
			otherParty = enemy;
		else otherParty = player;
		
		double origDist = Math.abs(movingEntity.getY() - otherParty.getY()) 
				+ Math.abs(movingEntity.getX() - otherParty.getX());
		double newDist = Math.abs(movedY - otherParty.getY()) 
				+ Math.abs(movedX - otherParty.getX());
		if (newDist > origDist) return false;
		else return true;
	}

	/**
	 * Checks if the player can move in a specified direction
	 * @param dir of the enum type Direction in ExpositoryConstant, 
	 * provides the direction that the player wants to move in
	 * @return true if the player can move in that direction
	 * false otherwise
	 */
	public boolean canMove (Moves dir, Player person) {
		switch (dir) {
		case UP:
			return person.getY() - 1 >= 0 
			&& fightGrid[person.getY() - 1][person.getX()] == null;
		case DOWN:
			return person.getY() + 1 < BATTLEFIELD_HEIGHT
					&& fightGrid[person.getY() + 1][person.getX()] == null; 
		case LEFT:
			return person.getX() - 1 >= 0 
			&& fightGrid[person.getY()][person.getX() - 1] == null; 
		case RIGHT:
			return person.getX() + 1 < BATTLEFIELD_WIDTH
					&& fightGrid[person.getY()][person.getX() + 1] == null;
		default:
			break; 
		}
		return false;
	}
	
	//Some text that appears to the user:
//	        /* On describing attacking */
//				 "You let loose a shot"
//			     "You fired the gun"
//				 ”You rose from your cover and risked a shot”
//				 "You threw a punch"
//				 “you sprung from your cover and attempted a surprise melee”
//				 "You drove your fist straight at the guy"
//				 “Your cover prevents you from shooting right now”
		        
	        /* On describing retreating from the fight */
		        // "You backed off"
		        // "You retreated from the fight"
		        // "You fled from the fight"
		        
	        /* On describing coming up against battlefield boundaries and taking cover */
		        // "You are up against the edge of a building on your left"
		        // "You are up against the edge of a building right"
		        // "An abandoned car/bus/truck lie to your..."
		        // "A hunk of stone lie to your..."
		        // "A tree lie to your..."
		        // "You take cover behind the tree/car/bus/truck/rock"
		        
	     
		        
	        /* on describing how the attack went */
		        // "The shot went to the left"
		        // "The shot went to the right"
		        // "The punch went to the right"
		        // "The punch went to the right"
		        // "Argh, you could have won if not for the tree/truck/car/bus your enemy was hiding behind"
		        // "There’s something in between the two of you that’s absorbing all your shots"
	        
	        /* On describing your opponents movement */
				// "The enemy moved to your right"
		        // "The Enemy dashed to your right"
				// "The enemy moved to your left"
		        // "The Enemy dashed to your left"
				// “The enemy moved forward”
				// “The enemy dashed forward”   

}
