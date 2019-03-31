import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;

public class PlayerHUD extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables*/	
	private HashMap<String, Button> buttonsDB = new HashMap<String, Button>();
	private EventListenerList listenerList = new EventListenerList();
	
	public PlayerHUD () {
		this.setLayout(new FlowLayout(FlowLayout.LEADING)); 
		this.setBackground(BG_COLOR);
		Border margin = BorderFactory.createEmptyBorder(STORY_MARGIN, STORY_MARGIN, STORY_MARGIN, STORY_MARGIN);
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(), margin));
	}

	public void createButtons (String controlTitle, boolean displayTitle, HashMap<String, Integer> buttonsToAdd) {
		Button buttonControls = new Button (controlTitle, displayTitle);
		
		for (String name : buttonsToAdd.keySet()) {
			buttonControls.addBtn(name, buttonsToAdd.get(name));
		}
		buttonControls.addButtonListener(new ButtonListener() {
			@Override
			public void buttonPressed(ButtonEvent be) {
				fireHUDEvent(new HUDEvent(be, be.getBtnName()));
			}
		});
		buttonsDB.put(controlTitle, buttonControls);
		
		add(buttonControls);	
	}
	
	public void addButtonControl (String btnControlTitle, String btnName, int cooldown) {
		Button buttonControl = buttonsDB.get(btnControlTitle);
		buttonControl.addBtn(btnName, cooldown);
	}

	
	public void fireHUDEvent(HUDEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == HUDEventListener.class) {
				((HUDEventListener) listeners[i + 1]).HUDEventOccurred(e);
			}
		}
	}
	
	public void addHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.add(HUDEventListener.class, HUDEventListener);
	}
	
	public void removeHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.remove(HUDEventListener.class, HUDEventListener);
	}
}
