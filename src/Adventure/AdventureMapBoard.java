package Adventure;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;

public class AdventureMapBoard extends JPanel implements ExpositoryConstant {
	private String MOVED = "MOVED";
	
	
	public AdventureMapBoard() {
		setKeyBindings();
		currentFloor.setPlayerLocOnMap(player.getX(), player.getY());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		repaint();
		revalidate();
		
		//background
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//FloorMap
		g.setFont(new Font(STORY_FONT, Font.BOLD, 15));
		int x = 20, y = 20;
		for (int i = 0; i < currentFloor.getHeight(); i ++) {
			for (int j = 0; j < currentFloor.getWidth(); j++) {
				g.setColor((Color) currentFloor.getColor(j, i));
				g.drawString("" + currentFloor.getTile(j, i)
						, x
						, y);
				x += 15;
			}
			y += 15; x = 20;
		}
	}
	
	private void setKeyBindings() {
		ActionMap actionMap = getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = getInputMap(condition);
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), MOVED);		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), MOVED);		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MOVED);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MOVED);
		actionMap.put(MOVED, new KeyAction(MOVED));
	}
	
	private class KeyAction extends AbstractAction {
		private String action;
		public KeyAction(String action) {
			System.out.println("Added action: " + action);
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("Command: " + e.getActionCommand() + "\nsource: " + e.getSource());
			
			switch(e.getActionCommand()) {
			// Move player up
			case "w":
				if (currentFloor.playerCanMove(Direction.UP)) {
					currentFloor.moveTile(Direction.UP);
				}
				break;
				
			// Move player down
			case "s":
				if (currentFloor.playerCanMove(Direction.DOWN)) {
					currentFloor.moveTile(Direction.DOWN);
				}
				break;			
			
			// Move player left	
			case "a":
				if (currentFloor.playerCanMove(Direction.LEFT)) {
					currentFloor.moveTile(Direction.LEFT);
				}
				break;
				
			// Move player right
			case "d":
				if (currentFloor.playerCanMove(Direction.RIGHT)) {
					currentFloor.moveTile(Direction.RIGHT);
				}
				break;
			}
		}	
	}
	
}
