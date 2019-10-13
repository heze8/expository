package GUI_Event_Handlers;

import java.util.HashMap;

import ExpositoryConstant.Resources;
import GUI.Inventory;
import GameEvents.UserGeneratedRoomEvents;

public class ButtonListenerImplementation implements ButtonListener {

	@Override
	public void buttonPressed(ButtonEvent be) {
		String btnName = be.getBtnName();
		UserGeneratedRoomEvents.handleButtonEvent (btnName);
	}
	
	@Override
	public boolean buttonClickable(HashMap<String, Integer> costMap) {
		boolean repairable = true;
		Inventory stores = Resources.inven.getInventory("Stores");
		if (costMap != null) {
			for (String item : costMap.keySet()) {
				if (stores.getQuantity(item) < costMap.get(item)) {
					repairable = false;
					Resources.story.displayText("Not enough " + item);
					return repairable;
				} else {
					stores.setQuantity(item, stores.getQuantity(item) - costMap.get(item));
//					Resources.story.displayText("Button clicked");
				}
			}
		}
		return repairable;
	}
}
