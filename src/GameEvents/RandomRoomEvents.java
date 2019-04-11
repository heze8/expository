package GameEvents;

import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;

public class RandomRoomEvents implements ExpositoryConstant {
	
	public RandomRoomEvents () {
	}
	
	public void getEvent() {
		switch (Resources.storyText.getnumTimesExplore()) {
		case 6:
			getRandomEvent();
		default:
			int toDisplay = ThreadLocalRandom.current().nextInt(1, 6);
			switch(toDisplay) {
			case 1:
				Resources.story.displayText("The air is cold.");
				break;
			case 2:
				Resources.story.displayText("A scufffle can be hear going on outside.");
				break;
			case 3:
				Resources.story.displayText("something scrapped against the walls.");
				break;
			case 4:
				Resources.story.displayText("The laptop screen flickers");
				break;
			case 5:
				Resources.story.displayText("Something twitched in your peripheral");
				Resources.currExploreEvent = ExploreBtnEvent.FIND_NANOBOT;
				break;
			}
			
		}
					
	}

	private void getRandomEvent() {
		
		
	}
}
