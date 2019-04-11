import javax.swing.*;

import Adventure.AdventureMapBoard;
import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import GUI.*;
import GUI_Event_Handlers.*;
import GameEvents.LaptopReply;
import GameEvents.UserGeneratedRoomEvents;

import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant, HUDEventListener, ConsoleListener, NanobotListener{
	
	/**
	 * Constructor which initializes the overall JFrame of the application
	 * @param title of type String, provides the name for the application window.
	 */
	public MainFrame(String title) {
		this.setTitle(title);	
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        loadGUI();
	}
	
	/**
	 * Loads all the GUI components into their proper position.
	 * Adds listener to the components as well.
	 */
	private void loadGUI() {
		
		//Creating the JPanels that will store the various components
		Resources.mainContainer = new JPanel(Resources.overallContainerCL);
		Resources.mainGUI = new JPanel(new BorderLayout());
		Resources.centerContainer = new JPanel(new BorderLayout());
		Resources.buttonContainer = new JPanel(Resources.controllerCL);
		Resources.dustContainer = new JPanel(Resources.dustCL);
		
		//Create swing components to be added to the JPanels
		Resources.yourRoom = new PlayerHUD();
		Resources.spaceShip = new PlayerHUD();
		Resources.dust = new PlayerHUD();
		Resources.mapBoard = new AdventureMapBoard();
		Resources.location = new PlayerHUD();		

		Resources.story = new Story();
		Resources.inven = new InventoryPanel();
		
		//Add swing components to JPanels, mainGUI is the parent here.
		Resources.mainGUI.add(Resources.story, BorderLayout.LINE_START);
		Resources.mainGUI.add(Resources.inven, BorderLayout.LINE_END);
		
		// Adding swing components to the center panel.
		Resources.centerContainer.add(Resources.location, BorderLayout.PAGE_START);
		Resources.centerContainer.add(Resources.buttonContainer, BorderLayout.CENTER);
		Resources.buttonContainer.add(Resources.yourRoom, YOUR_ROOM);
		Resources.buttonContainer.add(Resources.spaceShip, SPACESHIP);
		Resources.buttonContainer.add(Resources.dustContainer, DUST);
		Resources.dustContainer.add(Resources.mapBoard, MAP);
		Resources.dustContainer.add(Resources.dust, DUST);
        
		//Adding the parent center, @name centerContainer, JPanels to the mainGUI
		Resources.mainGUI.add(Resources.centerContainer, BorderLayout.CENTER);
		
		//Adding mainGUI JPanel and Laptop class to mainContainer, the overall parent container.
		Resources.mainContainer.add(Resources.mainGUI, MAIN_GUI);
		Resources.mainContainer.add(Resources.laptop, LAPTOP);
		
		this.add(Resources.mainContainer);
		
		//Adding Listeners to the various components
		Resources.yourRoom.addHUDEventListener(this);
		Resources.spaceShip.addHUDEventListener(this);
		Resources.dust.addHUDEventListener(this);
		Resources.location.addHUDEventListener(this);
		Resources.laptop.addConsoleListener(this);	
	}
	
	
	public void playGame() {
		Resources.story.displayText("The World Comes into Vision");
		initRoomControls();
		initLocationControls();
//		dust.addButtonGroup("TEST", true);
//		dust.addButton("TEST", "HOME", 0, true);
//		
//		Resources.inven.createInvenGroup("Stores", new HashMap<String, Integer>() {{
//			put("Water", 0);
//		}});
//		
//		Nanobot test = new Nanobot("TestBot");
//		test.addNanobotListener(this);
//		spaceShip.add(test);
//		
//			
	}

	private void initRoomControls() {
		Resources.yourRoom.addButtonGroup("Actions", false, new HashMap<String, Integer>() {{
		    put ("Explore", 5);
		}}, true);
	}

	private void initLocationControls() {
		Resources.location.addButtonGroup("Your Room", false, new HashMap<String, Integer> () {{
			put ("Unknown", NO_WAIT);
		}}, false);
//		location.addButtonGroup("Spaceship", false, new HashMap<String, Integer> () {{
//			put ("|    Spaceship     |", NO_WAIT);
//		}}, false);
//		location.addButtonGroup("Dust", false, new HashMap<String, Integer> () {{
//			put ("Dust", NO_WAIT);
//		}}, false);
	}

	@Override
	public void buttonPressed(ButtonEvent be) {
		String btnName = be.getBtnName();
		UserGeneratedRoomEvents.handleButtonEvent (btnName);
	}
	
	@Override
	public boolean buttonClickable(HashMap<String, Integer> costMap) {
		boolean repairable = true;
		Inventory stores = Resources.inven.getInventory("Stores");
		if (costMap != null) {
			for (String item : costMap.keySet()) {
				if (stores.getQuantity(item) < costMap.get(item)) {
					repairable = false;
					Resources.story.displayText("Not enough " + item);
					return repairable;
				} else {
					stores.setQuantity(item, stores.getQuantity(item) - costMap.get(item));
					Resources.story.displayText("Button clicked");
				}
			}
		}
		return repairable;
	}
	
	@Override
	public String receiveCommand(String command) {
		return LaptopReply.replyTo(command);
	}

	@Override
	public void nanobotEventOccurred(HashMap<String, Integer> valuesForUpdating) {
		Inventory stores = inven.getInventory("Stores");
		for (String name : valuesForUpdating.keySet()) {
			stores.setQuantity(name, stores.getQuantity(name) + valuesForUpdating.get(name));
		}
	}

	@Override
	public boolean nanobotRepairOccurred(HashMap<String, Integer> costMap) {
		boolean repairable = true;
		Inventory stores = inven.getInventory("Stores");
		for (String item : costMap.keySet()) {
			if (stores.getQuantity(item) < costMap.get(item)) {
				repairable = false;
				story.displayText("Not enough " + item);
				return repairable;
			} else {
				stores.setQuantity(item, stores.getQuantity(item) - costMap.get(item));
				story.displayText("NanoBot upgraded");
			}
		}
		return repairable;
	}

	@Override
	public boolean plusMinusClicked(String command, String param) {
		// TODO Auto-generated method stub
		return false;
	}
}
