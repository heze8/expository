package GameEvents;

import java.awt.CardLayout;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ExpositoryConstant.CostMap;
import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import GUI.LatopText;
import GUI.Nanobot;
import GUI_Event_Handlers.NanobotListenerImplementation;

public class UserGeneratedRoomEvents implements ExpositoryConstant {
	private static int numTimesExplored = 0;
	private static SpaceshipParts blueprint = SpaceshipParts.SOLAR_PANELS;
	
	
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
				while (true) {
					 UIManager.put("OptionPane.background", BG_COLOR); 
					 UIManager.put("Panel.background", BG_COLOR);
					 UIManager.put("TextField.background", BG_COLOR);
					 UIManager.put("TextField.foreground", NORMAL_COLOR);
					 UIManager.put("OptionPane.messageFont", new Font(STORY_FONT, Font.PLAIN, 15));
					 UIManager.put("OptionPane.messageForeground", NORMAL_COLOR);
					 nanobotName = JOptionPane.showInputDialog(null
							, "Enter a name for the NanoBot: "
							, "Name the Nanobot"
							, JOptionPane.PLAIN_MESSAGE);
					 if (!nanobotName.equals("")) break;
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
			default:
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
				Resources.yourRoom.setBtnToolTip("Actions", "Explore", CostMap.costMap.get("Explore"));
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
			RandomDustEvents.rngLoot(LootSource.SCAVENGE);
			if (Resources.inven.getInventory(STORES).getQuantity("Blueprint") != 0
					&& !Resources.yourRoom.btnExist("Actions", "Use Blueprint")) {
				Resources.yourRoom.addButton("Actions", "Use Blueprint", LONG_WAIT, true);
				Resources.yourRoom.setBtnCost("Actions", "Use Blueprint", new HashMap<String, Integer>() {{
					put ("Blueprint", 1);
				}});
			}
			break;
		case "Use Blueprint":
			switch (blueprint) {
			case SOLAR_PANELS:
				Resources.yourRoom.addButtonGroup("Build", true);
				addSpaceshipInvenAndBotOption(SpaceshipParts.SOLAR_PANELS);
				blueprint = SpaceshipParts.ORE_REFINERY;
				break;
			case ORE_REFINERY:
				addSpaceshipInvenAndBotOption(SpaceshipParts.SOLAR_PANELS);
				blueprint = SpaceshipParts.ALLOY_REFINERY;
				break;
			case ALLOY_REFINERY:
				addSpaceshipInvenAndBotOption(SpaceshipParts.SOLAR_PANELS);
				blueprint = SpaceshipParts.GLASS_FACTORY;
				break;
			case GLASS_FACTORY:
				addSpaceshipInvenAndBotOption(SpaceshipParts.SOLAR_PANELS);
				blueprint = null;
				break;
			default:
				Resources.story.displayText("The blueprint did not reveal anything useful");
				break;
			}
			break;
			
		case "Solar Panels":
			Resources.inven.getInventory(WEAPONS).addEntry("Solar Panels", 1);
			// Add nanobot extra param
			break;
		case "Ore Refinery": 
			Resources.inven.getInventory(WEAPONS).addEntry("Solar Panels", 1);
			break;
		case"Alloy Refinery":
			Resources.inven.getInventory(WEAPONS).addEntry("Solar Panels", 1);
			break;
		case "Glass Factory":
			Resources.inven.getInventory(WEAPONS).addEntry("Solar Panels", 1);
			break;
		// Locations
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
			// Displays the dust tab where user can embark on the map hunting
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

	private static void addSpaceshipInvenAndBotOption(SpaceshipParts part) {
		Resources.yourRoom.addButton("Build", part.toString(), NOT_RECLICKABLE, true);
		Resources.yourRoom.setReclickable("Build", part.toString(), false);
		Resources.yourRoom.setBtnCost("Build", part.toString(), CostMap.costMap.get(part.toString()));
		Resources.yourRoom.setBtnToolTip("Build", part.toString(), CostMap.costMap.get(part.toString()));
		Resources.story.displayText("The blueprint revealed the technical knowledge to build a " + part.toString());
	}
}