package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.FontUIResource;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ButtonEvent;
import GUI_Event_Handlers.ButtonListener;

public class Button extends JPanel implements MouseListener, ExpositoryConstant {
	private HashMap<String, Vector> btnDB = new HashMap<String, Vector>();
	private HashMap<String, Vector> clickedBtnDB = new HashMap<String , Vector>();
	private EventListenerList listenerList = new EventListenerList();
	
	private TitledBorder titleBorder;
	private String title;
	private boolean isClickable;
	
	/**
	 * Constructor for the Button class, creates a title for the set of controls if needed.
	 * @param controlTitle of type String, provides the title associated with the set of buttons
	 * @param displayTitle of type boolean, results in the title being displayed for the buttons 
	 * if set to true.
	 */
	public Button (String controlTitle, boolean displayTitle) {
		title = controlTitle;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(BG_COLOR);
		if (displayTitle) {
			Border empty = BorderFactory.createEmptyBorder();
			titleBorder = BorderFactory.createTitledBorder(empty, 
					controlTitle, 
					TitledBorder.CENTER, 
					TitledBorder.TOP, 
					new Font (STORY_FONT, Font.BOLD, 12), 
					NORMAL_COLOR);
			setBorder(titleBorder);
		}
	}
	
	/**
	 * Allows individual buttons to be added to the Button group
	 * @param name of type String, provides the name for the btn
	 * @param cooldown of type int, provides the associate value for the time needed between each button press.
	 */
	public void addBtn(String name, int cooldown, boolean displayBorder) {
		JLabel newBtn = new JLabel(name);
		
		if (displayBorder) {
			 int marginLeftRight = (CONTROL_LABEL_LENGTH - newBtn.getPreferredSize().width) / 2;
		        Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_CONTROL
		        		, marginLeftRight
		        		, MARGIN_TOP_BOTTOM_CONTROL
		        		, marginLeftRight);
		        newBtn.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), margin));
		}
        newBtn.setForeground(NORMAL_COLOR);
        newBtn.setBackground(NORMAL_COLOR);
        
        newBtn.addMouseListener(this);
        add(newBtn);
        
       Vector data = new Vector();
       data.add(newBtn);
       data.add(cooldown);
       data.add(null);
       btnDB.put(name, data);
	}
	
	/**
	 * Returns the title of the button group
	 * @return the title of the button group, of type String
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Allows the client to change the title of a button group if needed. 
	 * @param newTitle of type String, provides the new title that the title of the button 
	 * group will be changed too.
	 */
	public void setTitle(String newTitle) {
		titleBorder.setTitle(newTitle);
		title = newTitle;
		repaint();
	}
	
	/**
	 * Allows the name of a particular button within the button group to be changed
	 * @param oldName of type String, provides the current name of the button
	 * @param newName of type String, provides the new name of the button
	 */
	public void changeBtnName(String oldName, String newName) {
		Vector data = btnDB.get(oldName);
		JLabel labelToChange = (JLabel) data.get(btnData.JLABEL.ordinal());
		labelToChange.setText(newName);
		btnDB.remove(oldName);
		btnDB.put(newName, data);
	}
	
	/**
	 * 
	 * @param btnNameToAddTooltip
	 * @param tip
	 */
	public void setToolTip (String btnNameToAddTooltip, String tip) {
		UIManager.put("ToolTip.font", new FontUIResource(STORY_FONT, Font.PLAIN, STORY_FONT_SIZE));
		UIManager.put("ToolTip.background",BG_COLOR); 
		UIManager.put("ToolTip.foreground", NORMAL_COLOR); 
		Vector data = btnDB.get(btnNameToAddTooltip);
		JLabel labelToAddTooltip = (JLabel) data.get(btnData.JLABEL.ordinal());
		labelToAddTooltip.setToolTipText(tip);
	}
	
	//Wrongly implemented, clickable should pertain to specifc button.
	public boolean isClickable() {
		return isClickable;
	}
	
	public void setBtnCost(String btnName, HashMap<String, Integer> costMap) {
		Vector data = btnDB.get(btnName);
		data.set(btnData.COST.ordinal(), costMap);
	}
	
	
	public void buttonPressed (ButtonEvent be) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ButtonListener.class) {
				((ButtonListener) listeners[i + 1]).buttonPressed(be);
			}
		}
	}
	
	public boolean buttonClickable(HashMap<String, Integer> costMap) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ButtonListener.class) {
				return ((ButtonListener) listeners[i + 1]).buttonClickable(costMap);
			}
		}
		return false;
	}
	
	public void addButtonListener (ButtonListener listener) {
		listenerList.add(ButtonListener.class, listener);
	}
	
	public void removeButtonListener(ButtonListener listener) {
		listenerList.remove(ButtonListener.class, listener);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();		
		if (pressed.getForeground() != CLICKED_COLOR) {
			pressed.setForeground(HOVER_COLOR);
			pressed.setBackground(HOVER_COLOR);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();
		if (pressed.getForeground() != CLICKED_COLOR) {
			pressed.setForeground(NORMAL_COLOR);
			pressed.setBackground(NORMAL_COLOR);	
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mousePressed(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();		
		String pressedBtnLabel = pressed.getText();
		int cooldown = (int) btnDB.get(pressedBtnLabel).get(btnData.COOLDOWN.ordinal());
		HashMap<String, Integer> costMap = (HashMap<String, Integer>) btnDB.get(pressedBtnLabel).get(btnData.COST.ordinal());
		
		if (pressed.getForeground() != CLICKED_COLOR && buttonClickable(costMap)) {
			buttonPressed(new ButtonEvent(pressed, pressedBtnLabel, getTitle()));
			
			if (cooldown != 0) {
				// Changes button color to show that button is pressed
				pressed.setForeground(CLICKED_COLOR);
				pressed.setBackground(CLICKED_COLOR);
				clickedBtnDB.put(pressedBtnLabel, new Vector() {{
					add(pressed.getX());
					add(pressed.getY());
					add((double) pressed.getWidth());
					add(pressed.getHeight());
				}});
				coolDownAnimation(pressed, cooldown);
			}
		}
	}

	private void coolDownAnimation(JLabel pressed, int cooldown) {

		Thread coolDownAnim = new Thread (new Runnable() {
			
			@Override
			public void run() {
				Timer timer = new Timer (UPDATE_TIME, null);
				timer.addActionListener(new ActionListener() {
					double btnWidth = pressed.getWidth();
					double amountToShrinkEachUpdate = btnWidth / ((cooldown * SEC_TO_MSEC) / UPDATE_TIME);
					
					@SuppressWarnings("unchecked")
					@Override
					public void actionPerformed (ActionEvent e) {
						btnWidth -= amountToShrinkEachUpdate;
						clickedBtnDB.get(pressed.getText()).set(pressedBtnData.WIDTH.ordinal(), btnWidth);
						repaint();
						revalidate();
						if (btnWidth <= 0) {
							timer.stop();
							pressed.setForeground(NORMAL_COLOR);
							pressed.setBackground(NORMAL_COLOR);
							clickedBtnDB.remove(pressed.getText());
						}
					}
				});
				timer.start();
			}
			
		});
		coolDownAnim.start();
	}
	 @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(NORMAL_COLOR);
        for (String pressed : clickedBtnDB.keySet()) {
        	int x = (int) clickedBtnDB.get(pressed).get(pressedBtnData.X_POS.ordinal());
        	int y = (int) clickedBtnDB.get(pressed).get(pressedBtnData.Y_POS.ordinal());
        	int btnWidth = (int) ((Double) clickedBtnDB.get(pressed).get(pressedBtnData.WIDTH.ordinal())).intValue();
        	int btnHeight = (int) clickedBtnDB.get(pressed).get(pressedBtnData.HEIGHT.ordinal());
        	g.fillRect(x, y, btnWidth, btnHeight);
        }
        
    }
}
