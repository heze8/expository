import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI.*;
import GUI_Event_Handlers.*;

import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant, HUDEventListener, ConsoleListener, NanobotListener{
	private CardLayout overallContainerCL = new CardLayout();
	private CardLayout controllerCL = new CardLayout();
	private JPanel mainContainer;
	private JPanel centerContainer;
	private JPanel buttonContainer;
	private JPanel mainGUI;
	
	private Story story;
	private StoryText storyText = new StoryText();
	private PlayerHUD yourRoom;
	private PlayerHUD spaceShip;
	private PlayerHUD dust;
	private PlayerHUD location;
	private InventoryPanel inven;
	private LatopText laptop = new LatopText();
	
	public MainFrame(String title) {
		this.setTitle(title);	
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        loadGUI();
	}
	
	private void loadGUI() {
		
		//Creating the JPanels that will store the various components
		mainContainer = new JPanel(overallContainerCL);
		mainGUI = new JPanel(new BorderLayout());
		centerContainer = new JPanel(new BorderLayout());
		buttonContainer = new JPanel(controllerCL);
		
		//Create swing components to be added to the JPanels
		yourRoom = new PlayerHUD();
		spaceShip = new PlayerHUD();
		dust = new PlayerHUD();
		
		location = new PlayerHUD();
		story = new Story();
		inven = new InventoryPanel();
		
		//Add swing components to JPanels, mainGUI is the parent here.
		mainGUI.add(story, BorderLayout.LINE_START);
		mainGUI.add(inven, BorderLayout.LINE_END);
		
		centerContainer.add(location, BorderLayout.PAGE_START);
		centerContainer.add(buttonContainer, BorderLayout.CENTER);
		buttonContainer.add(yourRoom, YOUR_ROOM);
		buttonContainer.add(spaceShip, SPACESHIP);
		buttonContainer.add(dust, DUST);
		mainGUI.add(centerContainer, BorderLayout.CENTER);
		
		//Adding mainGUI JPanel and Laptop class to mainContainer, the overall parent.
		mainContainer.add(mainGUI, MAIN_GUI);
		mainContainer.add(laptop, LAPTOP);
		laptop.bootLaptop();
		
		this.add(mainContainer);
	}
	
	public void playGame() {
		story.displayText("The World Comes into Vision");
		yourRoom.createButtonGroup("Actions", false, new HashMap<String, Integer>() {{
		    put ("Explore", NO_WAIT);
		    put ("Stay Still", NO_WAIT);
		}}, true);
		location.createButtonGroup("Your Room", false, new HashMap<String, Integer> () {{
			put ("A Place", NO_WAIT);
		}}, false);
		location.createButtonGroup("Spaceship", false, new HashMap<String, Integer> () {{
			put ("|    Spaceship     |", NO_WAIT);
		}}, false);
		location.createButtonGroup("Dust", false, new HashMap<String, Integer> () {{
			put ("Dust", NO_WAIT);
		}}, false);
		
		inven.createInvenGroup("Stores", new HashMap<String, Integer>() {{
			put("Water", 0);
		}});
		
		Nanobot test = new Nanobot("TestBot");
		test.addNanobotListener(this);
		spaceShip.add(test);
		
		yourRoom.addHUDEventListener(this);
		spaceShip.addHUDEventListener(this);
		dust.addHUDEventListener(this);
		location.addHUDEventListener(this);
		laptop.addConsoleListener(this);		
	}

	@Override
	public void buttonPressed(ButtonEvent be) {
		String btnName = be.getBtnName();
		if (btnName.equals("Explore")) {
			   story.displayText(storyText.explore());
			   if (storyText.getExploreState() == Integer.parseInt(LAPTOP)) {
				   yourRoom.addButton("Actions", "Browse Laptop", 5, true);
			   } else if (storyText.getExploreState() == NANOBOT) {
				   	location.addButton("Locations", "A Wrecked Room", 0, false);
			   }
		   } else if (btnName.equals("Stay Still")) {
			   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
			   		+ "\n");
		   } else if (btnName.equals("Browse Laptop")) {
			   overallContainerCL.show(mainContainer, LAPTOP);
	   }
		
	   if (btnName.equals("A Place")) {
		   controllerCL.show(buttonContainer, YOUR_ROOM);
	   } else if (btnName.equals("|    Spaceship     |")) {
		   controllerCL.show(buttonContainer, SPACESHIP);
	   } else if (btnName.equals("Dust")) {
		   controllerCL.show(buttonContainer, DUST);
	   }
	}
	
	@Override
	public boolean buttonClickable(HashMap<String, Integer> costMap) {
		boolean repairable = true;
		Inventory stores = inven.getInventory("Stores");
		if (costMap != null) {
			for (String item : costMap.keySet()) {
				if (stores.getQuantity(item) < costMap.get(item)) {
					repairable = false;
					story.displayText("Not enough " + item);
					return repairable;
				} else {
					stores.setQuantity(item, stores.getQuantity(item) - costMap.get(item));
					story.displayText("Button clicked");
				}
			}
		}
		return repairable;
	}
	
	@Override
	public String receiveCommand(String command) {
		return storyText.laptopReply(command, laptop, overallContainerCL, mainContainer);
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
