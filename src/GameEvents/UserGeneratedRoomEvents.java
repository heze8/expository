package GameEvents;

import java.awt.CardLayout;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import GUI.LatopText;
import GUI.Nanobot;
import GUI_Event_Handlers.NanobotListenerImplementation;

public class UserGeneratedRoomEvents implements ExpositoryConstant {
	private static int numTimesExplored = 0;
	
	
	@SuppressWarnings("serial")
	public static void handleButtonEvent(String btnName) {
		
		switch (btnName) {
		
		case "Explore":
			switch (Resources.currExploreEvent) {
			case FIND_NANOBOT:
				Resources.story.displayText("The twitching seems to be coming from a NanoBot. You somehow knew they could be useful.");
				
				//Creates tab to access Nanobot details
				Resources.location.addButtonGroup("Spaceship", false);
				Resources.location.addButton("Spaceship", "|    A Lone Robot", NO_WAIT, false);
				
				//Prompts User for Name and creates NanoBot to add to pane
				String nanobotName = "";
				while (nanobotName.equals("")) {
					nanobotName = JOptionPane.showInputDialog(null
							, "Enter a name for the NanoBot: "
							, "Name the Nanobot"
							, JOptionPane.PLAIN_MESSAGE);
				}
				Nanobot bot = new Nanobot(nanobotName);
				bot.addNanobotListener(new NanobotListenerImplementation());
				Resources.spaceShip.add(bot);
				Resources.NanoBotDetails.put(nanobotName, bot);
				break;
				
			case FIND_DOOR:
				Resources.story.displayText("The rectangular protrusion opens up to reveal a whole landscape of desert.");
				Resources.location.addButtonGroup("Dust", false);
				Resources.location.addButton("Dust", "|    An Empty Desert", NO_WAIT, false);
				
				Resources.dust.addButtonGroup("Actions", false);
				Resources.dust.addButton("Actions", "Scavenge", DEFAULT_WAIT, true);
				break;
			}
			if (Resources.currExploreEvent != ExploreBtnEvent.NOTHING) {
				Resources.currExploreEvent = ExploreBtnEvent.NOTHING;
				break;
			}
			
			switch(numTimesExplored) {
			case 0:
				Resources.story.displayText("The floor you're on is cool and solid. Titanium.");
				numTimesExplored++;
				break;
			case 1:
				Resources.story.displayText("A tank of water lay in one corner");
				numTimesExplored++;
				break;
			case 2:
				Resources.story.displayText("A piece of paper lay crumpled next to you. Amelia2604, it says.");
				numTimesExplored++;
				break;
			case 3:
				Resources.story.displayText("Four walls greet you with a featureless dull gray stare.");
				Resources.location.changeBtnName("Your Room", "Unknown", "A Room");
				numTimesExplored++;
				break;
			case 4:
				Resources.story.displayText("Directly opposite, in the left corner, a computing unit sits on the floor...");
				Resources.yourRoom.addButton("Actions", "Browse Laptop", DEFAULT_WAIT, true);
				numTimesExplored++;
				break;
			case 5:
				Resources.story.displayText("Your water stores is running low");
				Resources.inven.showInvenGroup("Stores");
				numTimesExplored++;
				break;
			case 6:
				Resources.story.displayText("The computing unit flickers, almost as if inviting you in");
				numTimesExplored++;
				break;
			case 7:
				Resources.story.displayText("There seems to be nothing else worth exploring for now...");
				break;
			default:
				break;
			}
			break;
			
		case "Browse Laptop":
			Resources.overallContainerCL.show(Resources.mainContainer, LAPTOP);
			Resources.laptop.bootLaptop();
			Resources.userLocation = Location.LAPTOP;
			break;
		
		case "Scavenge":
			RandomDustEvents.rngLoot(LootSource.EXPLORATION);
			break;
			
		case "Unknown":
		case "A Room":
		case "Your Room":
			// DIsplays the room tab where the user can build stuff
			Resources.controllerCL.show(Resources.buttonContainer, YOUR_ROOM);
			
			// Ensures that only "Stores" is showing
			Resources.inven.removeInvenGroup(WEAPONS);
			Resources.inven.removeInvenGroup(SPACESHIP_PARTS);
			
			// Sets the User location for event generation
			Resources.userLocation = Location.ROOM; 
			break;
		case "|    Spaceship":
		case "|    A Lone Robot":
			// Displays the spaceship tab where user can manage Nanobots
			Resources.controllerCL.show(Resources.buttonContainer, SPACESHIP);
			
			//Displays The three inventory (Weapons, stores, and spaceship parts
			Resources.inven.removeInvenGroup(WEAPONS);
			Resources.inven.removeInvenGroup(SPACESHIP_PARTS);
			if (!Resources.inven.isEmpty(WEAPONS)) {
				Resources.inven.showInvenGroup(WEAPONS);	
			}
			if (!Resources.inven.isEmpty(SPACESHIP_PARTS)) {
				Resources.inven.showInvenGroup(SPACESHIP_PARTS);
			}
			
			// Sets the User location for event generation
			Resources.userLocation = Location.SPACESHIP; 
			break;
		case "|    Dust":
		case "|    An Empty Desert":
			// Displlays the dust tab where user can embark on the map hunting
			Resources.controllerCL.show(Resources.buttonContainer, DUST);
			
			//Displays The the inventory (Weapons and stores
			Resources.inven.removeInvenGroup(WEAPONS);
			Resources.inven.removeInvenGroup(SPACESHIP_PARTS);
			if (!Resources.inven.isEmpty(WEAPONS)) {
				Resources.inven.showInvenGroup(WEAPONS);	
			}
			// Sets the User location for event generation
			Resources.userLocation = Location.DUST; 
			break;
		}

	}
}