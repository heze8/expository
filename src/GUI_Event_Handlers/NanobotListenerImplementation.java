package GUI_Event_Handlers;

import java.util.HashMap;

import ExpositoryConstant.Resources;
import GUI.Inventory;

public class NanobotListenerImplementation implements NanobotListener {
	@Override
	public void nanobotEventOccurred(HashMap<String, Integer> valuesForUpdating) {
		Inventory stores = Resources.inven.getInventory("Stores");
		for (String item : valuesForUpdating.keySet()) {
			stores.setQuantity(item, stores.getQuantity(item) + valuesForUpdating.get(item));
		}
	}

	@Override
	public boolean nanobotRepairOccurred(HashMap<String, Integer> costMap) {
		boolean repairable = true;
		if (costMap != null) {
			Inventory stores = Resources.inven.getInventory("Stores");
			for (String item : costMap.keySet()) {
				if (stores.getQuantity(item) < costMap.get(item)) {
					repairable = false;
					Resources.story.displayText("Not enough " + item);
					return repairable;
				} else {
					stores.setQuantity(item, stores.getQuantity(item) - costMap.get(item));
					Resources.story.displayText("NanoBot upgraded");
				}
			}
		}
		return repairable;
	}
}
