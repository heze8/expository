package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ButtonEvent;
import GUI_Event_Handlers.ButtonListener;

public class Button extends JPanel implements MouseListener, ExpositoryConstant {
	private HashMap<String, Vector> btnDB = new HashMap<String, Vector>();
	private EventListenerList listenerList = new EventListenerList();
	private TitledBorder titleBorder;
	private String title;
	
	/**
	 * Constructor for the Button Control class, creates a title for the set of controls if needed.
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
       btnDB.put(name, data);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String newTitle) {
		titleBorder.setTitle(newTitle);
	}
	
	public void changeBtnName(String oldName, String newName) {
		Vector data = btnDB.get(oldName);
		JLabel labelToChange = (JLabel) data.get(JLABEL_TO_CHANGE);
		labelToChange.setText(newName);
		btnDB.remove(oldName);
		btnDB.put(newName, data);
	}
	
	public void addToolTip (String name) {
		Vector data = btnDB.get(name);
		JLabel labelToAddTooltip = (JLabel) data.get(JLABEL_TO_CHANGE);
		labelToAddTooltip.setToolTipText(TOOL_TIP_TEXT_KEY);
	}
	
	
	public void fireButtonEvent (ButtonEvent be) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ButtonListener.class) {
				((ButtonListener) listeners[i + 1]).buttonPressed(be);
			}
		}
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

	@Override
	public void mousePressed(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();		
		String pressedBtnLabel = pressed.getText();
		int cooldown = (int) btnDB.get(pressedBtnLabel).get(COOLDOWN);
		if (pressed.getForeground() != CLICKED_COLOR) {
			fireButtonEvent(new ButtonEvent (pressed, pressed.getText(), getTitle()));
			// Changes button color to show that button is not clickable
			pressed.setForeground(CLICKED_COLOR);
			pressed.setBackground(CLICKED_COLOR);
			//pressed.setText(String.valueOf(cooldown));
			// Changes the button color back to original after delay ms has passed
			// Allows the button to be clicked again
    		Timer timer = new Timer(CONVERT_TO_MSEC, null);
    		timer.addActionListener(new ActionListener(){
    			int timeToClickable = (int) btnDB.get(pressedBtnLabel).get(COOLDOWN);
				@Override
				public void actionPerformed( ActionEvent e ){
					//pressed.setText(String.valueOf(--timeToClickable));
					timeToClickable --;
					if (timeToClickable <= 0) {
						pressed.setForeground(NORMAL_COLOR);
						pressed.setBackground(NORMAL_COLOR);
						//pressed.setText(pressedBtnLabel);
						timer.stop();
					}
				}
    		});
    		timer.start();
		}
	}

}
