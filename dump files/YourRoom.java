import java.awt.CardLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ButtonEvent;
import GUI_Event_Handlers.ButtonListener;
import GUI.*;

public class YourRoom extends JPanel implements ExpositoryConstant, ButtonListener {
	PlayerHUD location;
	CardLayout overallContainerCL;
	JPanel mainContainer;
	
	private InventoryPanel inven;
	private Story story;
	private StoryText storyText;
	private Button actions;
	
	public YourRoom (InventoryPanel inven, Story story, PlayerHUD location, CardLayout overallContainerCL, JPanel mainContainer) {
		this.inven = inven;
		this.story = story;
		this.location = location;
		this.overallContainerCL = overallContainerCL;
		this.mainContainer= mainContainer;
		
		storyText = new StoryText();
		actions = new Button("Actions", false);
		actions.addButtonListener(this);
		actions.addBtn("Explore", NO_WAIT, true);
		actions.addBtn("Stay Still", NO_WAIT, true);
		add(actions);
	}
	
	@Override
	public void buttonPressed(ButtonEvent be) {
		String cmd = be.getBtnName();
			   
		if (cmd.equals("Explore")) {
		   story.displayText(storyText.explore());
		   if (storyText.getExploreState() == Integer.parseInt(LAPTOP)) {
			   actions.addBtn("Browse Laptop", 5, true);
		   } else if (storyText.getExploreState() == NANOBOT) {
			   	location.addButtonControl("Locations", "A Wrecked Room", 0, false);
		   }
	   } else if (cmd.equals("Stay Still")) {
		   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
		   		+ "\n");
	   } else if (cmd.equals("Browse Laptop")) {
		   overallContainerCL.show(mainContainer, LAPTOP);
	   }
	}

	@Override
	public boolean buttonClickable(HashMap<String, Integer> costMap) {
		// TODO Auto-generated method stub
		return false;
	}

}
