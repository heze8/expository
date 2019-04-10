import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ConsoleListener;

public class Laptop extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables */
	private JLabel keyLabel;
	private ConsoleListener consoleListener = null;
	private int historyIndex = 0;
	private Vector<String> historyString = new Vector<String>();
	private ConsoleVector currentString = new ConsoleVector();
	private String oldText = "";
	private boolean logIn = false;
	private boolean loggingIn = false;
	
	/**
	 * Constructor for Laptop class. Initialises the keyBinding associated with this class.
	 * Sets the layout to flow layout.
	 */
	public Laptop() {
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		setKeyBindings();
	}

	/**
	 * Main method of the class. Initialises a label, formats it and add its on the JPanel
	 */
	public void runLaptop() {
		keyLabel = new JLabel("");
		keyLabel.setFont(new Font (Font.MONOSPACED, Font.PLAIN, 20));
//		keyLabel.setForeground(NORMAL_COLOR);
		this.setBackground(BG_COLOR);
		this.add(keyLabel);
		print();
	}

	public void slowPrint(String text, int delay) {
		Timer timer = new Timer (delay, null);
		timer.addActionListener(new ActionListener() {
			int textPos = 0;
			@Override
			public void actionPerformed (ActionEvent e) {
				oldText += text.charAt(textPos ++);
				print();
				if (textPos >= text.length()) {
					timer.stop();
				}
			}
		});
		timer.start();
	}
	
	private void fadeIn() {
		Timer timer = new Timer (TEXT_FADE_UPDATE, null);
		timer.addActionListener(new ActionListener () {
			int visibility = INVINSIBLE;
			@Override
			public void actionPerformed (ActionEvent e) {
				visibility += TEXT_FADE_DELAY;
				if (visibility < VISIBLE) {
					keyLabel.setForeground(new Color (visibility, visibility, visibility));	
					keyLabel.revalidate();
				} else {
					timer.stop();
				}
			}
		});
		timer.start();
	}
	
	public void bootLaptop() {
		oldText = "Laptop Booted.<br>"
				+ "<br>"
				+ "Possible Actions:<br>"
				+ "> :Login<br>"
				+ "> :exit<br>"
				+ "<br>"
				+ "> ";
		print();
		fadeIn();
	}

	public void setLoggingIn(boolean b) {
		loggingIn = b;
	}

	public boolean loggingIn() {
		return loggingIn;
	}
	
	public boolean getLoginStatus() {
		return logIn;
	}
	
	public void setLoginStatus(boolean loginStatus) {
		logIn = loginStatus;
	}
	
	public static int hash(String commandResponse) {
		int hashCode = 0;
		
		for (int i = 0; i < commandResponse.length(); i ++) {
			hashCode += Math.pow(HASH_CONSTANT, i) * ((int)commandResponse.charAt(i));
		}
		System.out.println(hashCode);
		return hashCode;
	}

	
	/**
	 * Listener registration method to allow other classes to listen for event 
	 * in the Laptop class.
	 * @param c of type ConsoleListener, provides the listener that will will invoked when
	 * an event happens in Laptop class.
	 */
    public void addConsoleListener(ConsoleListener c) {
    	consoleListener = c;
    }
    

	/**
	 * Allows client-side code to output values onto the Laptop class JPanel
	 * @param toPrint
	 */
	public void print(String toPrint) {
		currentString.fromString(toPrint.split(""));
		print();
	}
    
	/**
	 * Handles the alphabetical key presses on the keyboard, 
	 * along with "Enter", "Space-bar", and "Backspace".
	 * @param key of type int, representing the ASCII value of the key pressed.
	 */
	@SuppressWarnings("unchecked")
	private void handleKey(int key) {
		historyIndex = 0;
		if (key == KeyEvent.VK_ENTER) {
			enter();
		} else if (key == KeyEvent.VK_BACK_SPACE) {
			backspace();
		} else if (key == KeyEvent.VK_SPACE) {
			space();
		} else if (key != KeyEvent.CHAR_UNDEFINED) {
			currentString.add(String.valueOf(Character.toChars(key)));
			print();
		}
	}
	
	/**
	 * Handles the text formatting within the JLabel, keyLabel, after the user presses enter.
	 */
	private void enter() {
		String commandToCommit = currentString.toString();
		String commandResponse = "";
		if (consoleListener != null) {
			commandResponse = consoleListener.receiveCommand(commandToCommit);
		}
		currentString.clear();
		oldText += commandToCommit + "<br>" + commandResponse;
		print();
	}

	/**
	 * Handles the text formatting in the JLabel, keyLabel, after the user presses "Backspace"
	 */
	private void backspace() {
		if (!currentString.isEmpty()) {
			currentString.remove(currentString.size() - 1);
			print();
		}
	}
	
	/**
	 * Handles the text formatting in the JLabel, keyLabel, after the user presses "Space"
	 */
	@SuppressWarnings("unchecked")
	private void space() {
		currentString.add(" ");
		print();
	}
	
	//A little buggy pressing down will skip to the second earliest character instead of the earliest one
	/**
	 * Allows the user to quickly access pass commands entered just like in the actual cmd.
	 * @param toDisplay of type int, lets the method know whether to display the most recent cmd
	 * or the earlier cmds.
	 */
	private void displayHistory(int toDisplay) {
		if (historyString.isEmpty()) {
			return;
		}
		if (toDisplay == MOST_RECENT) {
			historyIndex ++;
			if (historyIndex > historyString.size()) {
                historyIndex = 1;
            }
			currentString.clear();
			if (historyIndex != 0) {
				String history = historyString.get((historyString.size() - historyIndex) % historyString.size());
				currentString.fromString(history.split(""));	
			}
		} else if (toDisplay == OLDEST) {
			historyIndex --;
			if (Math.abs(historyIndex) > historyString.size()) {
				historyIndex = -1;
			}
			currentString.clear();
			String history = historyString.get((historyString.size() - historyIndex) % historyString.size());
			currentString.fromString(history.split(""));	
		}
		
		print();
	}
	
	/**
	 * Prints value of what the user has written thus far to the JLabel, keyLabel,
	 * by changing the text value associated with the JLabel
	 */
	private void print() {
		keyLabel.setText("<html>" + oldText + currentString.toString() + " </html>");
		repaint();
	}

	/**
	 * Maps the various Key actions of the user to the various possible action within the class.
	 */
	private void setKeyBindings() {
	      ActionMap actionMap = getActionMap();
	      int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
	      InputMap inputMap = getInputMap(condition);

	      String keyPressed = "keyPressed";
	      for (int i = 46; i < 60; i ++) {
		      inputMap.put(KeyStroke.getKeyStroke(i, 0), keyPressed);
		      actionMap.put(keyPressed, new KeyAction(i, keyPressed));
	      }
	      for (int i = 65; i <= 90; i ++) {
		      inputMap.put(KeyStroke.getKeyStroke(i, 0), keyPressed);
		      actionMap.put(keyPressed, new KeyAction(i, keyPressed));
	      }
	      for (int i = 65; i <= 90; i ++) {
		      inputMap.put(KeyStroke.getKeyStroke(i, InputEvent.SHIFT_DOWN_MASK ), keyPressed);
		      actionMap.put(keyPressed, new KeyAction(i, keyPressed));
	      }
	      
	      inputMap.put(KeyStroke.getKeyStroke(59, InputEvent.SHIFT_DOWN_MASK ), keyPressed);
	      actionMap.put(keyPressed, new KeyAction(59, keyPressed));
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), keyPressed);
	      actionMap.put(keyPressed, new KeyAction(KeyEvent.VK_SPACE, keyPressed));
	     
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0 ), keyPressed);
	      actionMap.put(keyPressed, new KeyAction(KeyEvent.VK_BACK_SPACE, keyPressed));
	      
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), keyPressed);
	      actionMap.put(keyPressed, new KeyAction(KeyEvent.VK_ENTER, keyPressed));
	      
	      String up = "up";
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), up);
	      actionMap.put(up, new Up());
	      
	      String downString = "down";
	      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), downString);
	      actionMap.put(downString, new Down());
	}
	
	/**
	 * Class that allows the action of the key event to be fired when said keys are pressed
	 */
	private class KeyAction extends AbstractAction {
	      public KeyAction(int keyPressed, String actionCommand) {
	    	  putValue(String.valueOf(keyPressed), actionCommand);
	      }

	      @Override
	      public void actionPerformed(ActionEvent actionEvt) {
	    	  if (actionEvt.getActionCommand() != null ) {
	    		  handleKey(actionEvt.getActionCommand().charAt(0));	    		  
	    	  }
	      }
	}

	private class Down extends AbstractAction {
		
		public Down () {
		}
		 @Override
	      public void actionPerformed(ActionEvent actionEvt) {
	    	  displayHistory(OLDEST);
	      }
	}
	private class Up extends AbstractAction {
		
		public Up () {
		}
		 @Override
	      public void actionPerformed(ActionEvent actionEvt) {
	    	  displayHistory(MOST_RECENT);
	      }
	}

}

//class ConsoleVector extends Vector {
//	
//	public ConsoleVector() {
//        super();
//    }
//	
//	@SuppressWarnings("unchecked")
//    public void fromString(String[] p) {
//        for (int i = 0; i < p.length; i++) {
//            this.add(p[i]);
//        }
//    }
//
//    @Override
//    public String toString() {
//        StringBuffer s = new StringBuffer();
//        for (int i = 0; i < this.size(); i++) {
//            s.append(this.get(i));
//        }
//        return s.toString();
//    }
//}
