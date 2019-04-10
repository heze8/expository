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
	 * @param groupTitle of type String, provides the title for the particular set of controls
	 * @param displayTitle of type boolean, if true, the title of the controls will be shown
	 * @param buttonsToAdd of type HashMap, contains the buttons to be added, 
	 * with each button name of type String mapped to its cooldown time (of type int) in seconds.
	 */
	public void addButtonGroup (String groupTitle, boolean displayTitle, HashMap<String, Integer> buttonsToAdd, boolean displayBorder) {
		Button buttonGroup = new Button (groupTitle, displayTitle);
		
		for (String name : buttonsToAdd.keySet()) {
			buttonGroup.addBtn(name, buttonsToAdd.get(name), displayBorder);
		}
		buttonGroup.addButtonListener(new ButtonListener() {
			@Override
			public void buttonPressed(ButtonEvent be) {
				buttonClicked(be);
			}

			@Override
			public boolean buttonClickable(HashMap<String, Integer> costMap) {
				return getIfButtonClickable(costMap);
			}
		});
		buttonsDB.put(groupTitle, buttonGroup);
		
		add(buttonGroup);	
	}
	
	/**
	 * Puts a set of control up onto the JPanel for users to interact with
	 * @param btnGroupTitle of type String, provides the title for the particular set of controls
	 * @param displayTitle of type boolean, if true, the title of the controls will be shown
	 */
	public void addButtonGroup(String btnGroupTitle, boolean displayTitle) {
		Button buttonGroup = new Button (btnGroupTitle, displayTitle);
		
		buttonGroup.addButtonListener(new ButtonListener() {
			@Override
			public void buttonPressed (ButtonEvent be) {
				
			}

			@Override
			public boolean buttonClickable(HashMap<String, Integer> costMap) {
				return getIfButtonClickable(costMap);
			}
		});
		buttonsDB.put(btnGroupTitle, buttonGroup);
		
		add(buttonGroup);	
	}
	
	/**
	 * Allows the client to add additional buttons to existing button sets
	 * @param btnGroupTitle of type String, provides the buttonTitle to identify the set 
	 * for which the new button is to be added too
	 * @param btnName of type String, provides the name of the button that is being added
	 * @param cooldown of type int, provides the cooldown type of the button being added in seconds.
	 */
	public void addButton (String btnGroupTitle, String btnName, int cooldown, boolean displayBorder) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		buttonGroup.addBtn(btnName, cooldown, displayBorder);
	}
	
	
	
	public void changeBtnTitle(String btnGroupTitle, String newTitle) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		buttonGroup.setTitle(newTitle);
	}
	
	public String getBtnTitle(String btnGroupTitle) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		return buttonGroup.getTitle();
	}
	
	public void changeBtnName(String btnGroupTitle, String oldBtnName, String newBtnName) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		buttonGroup.changeBtnName(oldBtnName, newBtnName);
	}
	
	public void setBtnToolTip (String btnGroupTitle, String btnNameToAddTooltip, String tip) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		buttonGroup.setToolTip(btnNameToAddTooltip, tip);
	}
	
	public void setBtnCost (String btnGroupTitle, String btnName, HashMap<String, Integer> costMap) {
		Button buttonGroup = buttonsDB.get(btnGroupTitle);
		buttonGroup.setBtnCost(btnName, costMap);
	}

	/**
	 * Puts a set of control up onto the JPanel for users to interact with
	 * @param groupTitle of type String, provides the title for the particular set of controls
	 * @param displayTitle of type boolean, if true, the title of the controls will be shown
	 * @param buttonsToAdd of type HashMap, contains the buttons to be added, 
	 * with each button name of type String mapped to its cooldown time (of type int) in seconds.
	 */
	public void createPlusMinus (String groupTitle, boolean displayTitle, HashMap<String, Integer> buttonsToAdd, boolean clickable) {
		PlusMinusBtn plusMinusControls = new PlusMinusBtn (groupTitle, displayTitle);
		
		for (String name : buttonsToAdd.keySet()) {
			plusMinusControls.addPlusMinus(buttonsToAdd.get(name), name, true);
		}
//		plusMinusControls.addPlusMinusListener(new PlusMinusListener() {
//			@Override
//			public boolean plusMinusClicked(String command) {
//				fireHUDEvent(new HUDEvent(be, be.getBtnName()));
//			}
//		});
		plusMinusDB.put(groupTitle, plusMinusControls);
		add(plusMinusControls);	
	}
	
	/**
	 * Allows the client to add additional buttons to existing button sets
	 * @param btnGroupTitle of type String, provides the buttonTitle to identify the set 
	 * for which the new button is to be added too
	 * @param btnName of type String, provides the name of the button that is being added
	 * @param cooldown of type int, provides the cooldown type of the button being added in seconds.
	 */
	public void addPlusMinus (String btnGroupTitle, String btnName, int initQuantity) {
		PlusMinusBtn plusMinusControl = plusMinusDB.get(btnGroupTitle);
		plusMinusControl.addPlusMinus(initQuantity, btnName, true);
	}
	
	public void buttonClicked(ButtonEvent be) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == HUDEventListener.class) {
				((HUDEventListener) listeners[i + 1]).buttonPressed(be);
			}
		}
	}
	
	public boolean getIfButtonClickable(HashMap<String, Integer> costMap) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == HUDEventListener.class) {
				return ((HUDEventListener) listeners[i + 1]).buttonClickable(costMap);
			}
		}
		return false;
	}
	
	public void addHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.add(HUDEventListener.class, HUDEventListener);
	}
	
	public void removeHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.remove(HUDEventListener.class, HUDEventListener);
	}
}
