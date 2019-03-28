import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;

public class PlayerHUD extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables*/
	Inventory inven;
	private GridBagConstraints gc = new GridBagConstraints();
	
	private HashMap<String, Button> controlsDB = new HashMap<String, Button>();
	private HashMap<String, HashMap<String, JLabel>> controlButtonsDB = new HashMap<String, HashMap<String, JLabel>>();
	private int currControlRow = 0;
	
	private HashMap<String, Inventory> invenDB = new HashMap<String, Inventory>();
	private int currInvenRow = 0;
	
	private EventListenerList listenerList = new EventListenerList();
	
	public PlayerHUD () throws IllegalAccessException {
		setLayout(new GridBagLayout()); 
		gc.weightx = WEIGHT_X;
		gc.weighty = WEIGHT_Y;
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
		controlsDB.put(controlTitle, buttonControls);
		
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = CONTROL_COL;
		gc.gridy = currControlRow ++;
		add(buttonControls, gc);	
	}
	
	public void addButtonControl (String btnControlTitle, String btnName, int cooldown) {
		Button buttonControl = controlsDB.get(btnControlTitle);
		buttonControl.addBtn(btnName, cooldown);
	}

	
	public void createInven (String invenTitle, HashMap<String, Integer> invenItems) {
		Inventory inven = new Inventory(invenTitle);
		for (String name : invenItems.keySet()) {
			inven.addEntry(name, invenItems.get(name));
		}
		invenDB.put(invenTitle, inven);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridx = INVENTORY_COL;
		gc.gridy = currInvenRow ++;
		add(inven, gc);
	}
	
	public void addInvenItem (String invenTitle, String item, int quantity) {
		Inventory inven = invenDB.get(invenTitle);
		inven.addEntry(item, quantity);
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
