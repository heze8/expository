package GameEvents;

import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;

public class RandomRoomEvents implements ExpositoryConstant {
	private static boolean nanobotFound = false;

	public static void getEvent() {
		int toDisplay = ThreadLocalRandom.current().nextInt(1, 6);
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
			
		}
					
	}

}
