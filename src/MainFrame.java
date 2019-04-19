
import javax.swing.*;

import Adventure.AdventureMapBoard;
import ExpositoryConstant.CostMap;
import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import GUI.*;
import GUI_Event_Handlers.*;
import GameEvents.LaptopReply;
import GameEvents.RandomEvent;
import GameEvents.RandomRoomEvents;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant {
	
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
		Resources.spaceStation = new PlayerHUD();
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
		Resources.dustContainer.add(Resources.dust, DUST);
		Resources.dustContainer.add(Resources.mapBoard, MAP);
		Resources.dustContainer.add(Resources.combat, COMBAT);
		
		//Adding the parent center, @name centerContainer, JPanels to the mainGUI
		Resources.mainGUI.add(Resources.centerContainer, BorderLayout.CENTER);
		
		//Adding mainGUI JPanel and Laptop class to mainContainer, the overall parent container.
		Resources.mainContainer.add(Resources.mainGUI, MAIN_GUI);
		Resources.mainContainer.add(Resources.laptop, LAPTOP);
		Resources.mainContainer.add(Resources.spaceStation, SPACESTATION);
		
		this.add(Resources.mainContainer);
	
		Resources.laptop.addConsoleListener(new ConsoleListenerImplementation());	
	}
	
	/**
	 * Starts the game by initializing the intro buttons and text
	 */
	public void playGame() {
		Resources.story.displayText("The World Comes into Vision");
		initRoomControls();
		initLocationControls();
		initInventoryGroupd();
		initSpaceControls();
	}

	/**
	 * Initializes the initial buttons available to the user in "Unknown" location
	 */
	private void initRoomControls() {
		Resources.yourRoom.addButtonGroup("Actions", false, new HashMap<String, Integer>() {{
		    put ("Explore", DEFAULT_WAIT);
		}}, true);
		Resources.yourRoom.setBtnCost("Actions", "Explore", CostMap.costMap.get("Explore"));
	}

	/**
	 * Initializes the initial location available to the user ("Unknown")
	 */
	private void initLocationControls() {
		Resources.location.addButtonGroup("Your Room", false, new HashMap<String, Integer> () {{
			put ("Unknown", NO_WAIT);
		}}, false);
	}
	
	private void initSpaceControls() {
		Resources.spaceStation.addButtonGroup("Actions", false, new HashMap<String, Integer>() {{
		    put ("Use Terminal", DEFAULT_WAIT);
		    put ("Return Home", NO_WAIT);
		}}, true);
	}
	
	/**
	 * Initializes the inventory needed throughout the game
	 */
	private void initInventoryGroupd() {
		// Create an Inventory group called "Stores" and adds the item "Water" to it
		Resources.inven.createInvenGroup(STORES);
		Resources.inven.addInvenItem(STORES, StoreItems.WATER.toString(), 10);
		
		// Create an Inventory group called "Weapons"
		Resources.inven.createInvenGroup(WEAPONS);
		// Create an Inventory group called "Spaceship Parts"
		Resources.inven.createInvenGroup(SPACESHIP_PARTS);
	}
}
