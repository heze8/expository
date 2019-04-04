import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI.InventoryPanel;
import GUI.PlayerHUD;
import GUI.Story;
import GUI_Event_Handlers.HUDEvent;
import GUI_Event_Handlers.HUDEventListener;

import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant, HUDEventListener, ConsoleListener{
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
		yourRoom.createButtons("Actions", false, new HashMap<String, Integer>() {{
		    put ("Explore", 1);
		    put ("Stay Still", 1);
		}}, true);
		location.createButtons("Your Room", false, new HashMap<String, Integer> () {{
			put ("A Place", 1);
		}}, false);
		location.createButtons("Spaceship", false, new HashMap<String, Integer> () {{
			put ("|    Spaceship     |", 1);
		}}, false);
		location.createButtons("Dust", false, new HashMap<String, Integer> () {{
			put ("Dust", 1);
		}}, false);
		
		
		Nanobot test = new Nanobot("TestBot");
		spaceShip.add(test);
		
		
		yourRoom.addHUDEventListener(this);
		spaceShip.addHUDEventListener(this);
		dust.addHUDEventListener(this);
		location.addHUDEventListener(this);
		laptop.addConsoleListener(this);		
	}

	@Override
	public void HUDEventOccurred(HUDEvent e) {
		String cmd = e.getBtnName();
		   
	   if (cmd.equals("Explore")) {
		   story.displayText(storyText.explore());
		   if (storyText.getExploreState() == Integer.parseInt(LAPTOP)) {
			   yourRoom.addButtonControl("Actions", "Browse Laptop", 5, true);
		   } else if (storyText.getExploreState() == NANOBOT) {
			   location.addButtonControl("Locations", "A Wrecked Room", 0, false);
		   }
	   } else if (cmd.equals("Stay Still")) {
		   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
		   		+ "\n");
	   } else if (cmd.equals("Browse Laptop")) {
		   overallContainerCL.show(mainContainer, LAPTOP);
	   } 
	   
	   if (cmd.equals("A Place")) {
		   controllerCL.show(buttonContainer, YOUR_ROOM);
	   } else if (cmd.equals("|    Spaceship     |")) {
		   controllerCL.show(buttonContainer, SPACESHIP);
	   } else if (cmd.equals("Dust")) {
		   controllerCL.show(buttonContainer, DUST);
	   }
	}
	
	@Override
	public String receiveCommand(String command) {
		return storyText.laptopReply(command, laptop, overallContainerCL, mainContainer);
	}
}
