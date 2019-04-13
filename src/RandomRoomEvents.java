package GameEvents;

import java.util.ArrayList;
import java.util.List;
import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;
import StoryData.Parser;

public class RandomRoomEvents implements ExpositoryConstant {
	private static boolean nanobotFound = false;
	private static Parser randomRoomEvents = new Parser("randomRoomEvents.txt");
	private static ArrayList<String> events = randomRoomEvents.retrieve();

	public static void getEvent() {
		int toDisplay = (int) (Math.random() * events.size());
		if(toDisplay == 0 && nanobotFound) {
			nanobotFound = true;
			events.remove(0);
		}

		Resources.story.displayText(events.get(toDisplay));
		
		/** 
		switch (toDisplay) {
		
		case 1:
			Resources.story.displayText("The air is cold.");
			break;
		case 2:
			Resources.story.displayText("A scufffle can be hear going on outside.");
			break;
		case 3:
			Resources.story.displayText("Something scrapped against the walls.");
			break;
		case 4:
			Resources.story.displayText("The laptop screen flickers");
			break;
		case 5:
			if (!nanobotFound) {
				Resources.story.displayText("Something twitched in your peripheral");
				Resources.currExploreEvent = ExploreBtnEvent.FIND_NANOBOT;
				nanobotFound = true;
			}
			else {
				Resources.story.displayText("The winds are howling outside");
			}
			break;
			
		} */
		
					
	}

}
