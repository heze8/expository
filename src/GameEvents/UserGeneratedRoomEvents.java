package GameEvents;

import java.awt.CardLayout;

import javax.swing.JPanel;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import GUI.LatopText;

public class UserGeneratedRoomEvents implements ExpositoryConstant {
	private static int numTimesExplored = 0;
	
	
	public static void handleButtonEvent(String btnName) {
		
		switch (btnName) {
		
		case "Explore":
			switch(numTimesExplored) {
			case 0:
				Resources.story.displayText("The floor here you're on is cool and solid. Titanium.");
				numTimesExplored++;
				break;
			case 1:
				Resources.story.displayText("A tank of water lay in one corner");
				numTimesExplored++;
				break;
			case 2:
				Resources.story.displayText("A piece of paper lay crumpled next it the computing . Amelia2604, it says.");
				numTimesExplored++;
				break;
			case 3:
				Resources.story.displayText("Four walls greet you with a featureless dull gray stare.");
				Resources.location.changeBtnName("Your Room", "Unknown", "A Room");
				numTimesExplored++;
				break;
			case 4:
				Resources.story.displayText("Directly opposite, in the left corner, a computing unit sits on the floor...");
				Resources.yourRoom.addButton("Actions", "Browse Laptop", 5, true);
				numTimesExplored++;
				break;
			case 5:
				Resources.story.displayText("The computing unit flickers, almost as if inviting you in");
				numTimesExplored++;
				break;
			case 6:
				switch (Resources.currExploreEvent) {
				case FIND_NANOBOT:
					Resources.story.displayText("The twitching seems to be com");
				default:
					Resources.story.displayText("There seems to be nothing else worth exploring for now...");
					break;
				}
			default:
				break;
			}
			break;
			
		case "Browse Laptop":
			Resources.overallContainerCL.show(Resources.mainContainer, LAPTOP);
			Resources.laptop.bootLaptop();
			Resources.userLocation = Location.LAPTOP;
			break;
		}
		
		case "Unknown":
		case "A Room":
		case "Your Room":
			Resources.controllerCL.show(Resources.buttonContainer, YOUR_ROOM);
			break;
		case "|    Spaceship     |":
			Resources.controllerCL.show(Resources.buttonContainer, SPACESHIP);
			break;
		case "Dust":
			Resources.controllerCL.show(Resources.buttonContainer, DUST);
	}

}