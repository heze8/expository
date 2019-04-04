package GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ButtonEvent;
import GUI_Event_Handlers.ButtonListener;
import GUI_Event_Handlers.HUDEvent;
import GUI_Event_Handlers.HUDEventListener;

public class PlayerHUD extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables*/	
	private HashMap<String, Button> buttonsDB = new HashMap<String, Button>();
	private EventListenerList listenerList = new EventListenerList();
	private HashMap<String, PlusMinusBtn> plusMinusDB = new HashMap<String, PlusMinusBtn>();
	
	/**
	 * Creates a new JPanel and initialises its Layout to FlowLayout.
	 * An empty border is also created around the JPanel to act as a margin.
	 */
	public PlayerHUD () {
		this.setLayout(new FlowLayout(FlowLayout.LEADING)); 
		this.setBackground(BG_COLOR);
		Border margin = BorderFactory.createEmptyBorder(PLAYER_HUD_MARGIN, PLAYER_HUD_MARGIN, PLAYER_HUD_MARGIN, PLAYER_HUD_MARGIN);
		this.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(), margin));
	}

	/**
	 * Puts a set of control up onto the JPanel for users to interact with
	 * @param controlTitle of type String, provides the title for the particular set of controls
	 * @param displayTitle of type boolean, if true, the title of the controls will be shown
	 * @param buttonsToAdd of type HashMap, contains the buttons to be added, 
	 * with each button name of type String mapped to its cooldown time (of type int) in seconds.
	 */
	public void createButtons (String controlTitle, boolean displayTitle, HashMap<String, Integer> buttonsToAdd, boolean displayBorder) {
		Button buttonControls = new Button (controlTitle, displayTitle);
		
		for (String name : buttonsToAdd.keySet()) {
			buttonControls.addBtn(name, buttonsToAdd.get(name), displayBorder);
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
	
	/**
	 * Allows the client to add additional buttons to existing button sets
	 * @param btnControlTitle of type String, provides the buttonTitle to identify the set 
	 * for which the new button is to be added too
	 * @param btnName of type String, provides the name of the button that is being added
	 * @param cooldown of type int, provides the cooldown type of the button being added in seconds.
	 */
	public void addButtonControl (String btnControlTitle, String btnName, int cooldown, boolean displayBorder) {
		Button buttonControl = buttonsDB.get(btnControlTitle);
		buttonControl.addBtn(btnName, cooldown, displayBorder);
	}

	/**
	 * Puts a set of control up onto the JPanel for users to interact with
	 * @param controlTitle of type String, provides the title for the particular set of controls
	 * @param displayTitle of type boolean, if true, the title of the controls will be shown
	 * @param buttonsToAdd of type HashMap, contains the buttons to be added, 
	 * with each button name of type String mapped to its cooldown time (of type int) in seconds.
	 */
	public void createPlusMinus (String controlTitle, boolean displayTitle, HashMap<String, Integer> buttonsToAdd, boolean clickable) {
		PlusMinusBtn plusMinusControls = new PlusMinusBtn (controlTitle, displayTitle);
		
		for (String name : buttonsToAdd.keySet()) {
			plusMinusControls.addPlusMinus(buttonsToAdd.get(name), name, true);
		}
//		plusMinusControls.addPlusMinusListener(new PlusMinusListener() {
//			@Override
//			public boolean plusMinusClicked(String command) {
//				fireHUDEvent(new HUDEvent(be, be.getBtnName()));
//			}
//		});
		plusMinusDB.put(controlTitle, plusMinusControls);
		add(plusMinusControls);	
	}
	
	/**
	 * Allows the client to add additional buttons to existing button sets
	 * @param btnControlTitle of type String, provides the buttonTitle to identify the set 
	 * for which the new button is to be added too
	 * @param btnName of type String, provides the name of the button that is being added
	 * @param cooldown of type int, provides the cooldown type of the button being added in seconds.
	 */
	public void addPlusMinus (String btnControlTitle, String btnName, int initQuantity) {
		PlusMinusBtn plusMinusControl = plusMinusDB.get(btnControlTitle);
		plusMinusControl.addPlusMinus(initQuantity, btnName, true);
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
