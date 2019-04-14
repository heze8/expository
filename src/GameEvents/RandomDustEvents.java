package GameEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.*;
import GUI.Inventory;

public class RandomDustEvents implements ExpositoryConstant {
	private static HashMap<String, Double> scavengeChanceMap = new HashMap<String, Double>() {{
		put("Ores", 0.5);
		put ("Copper", 0.35);
		put("Cloth", 0.5);
		put ("Glass Bits", 0.5);
		put ("Metal Alloy", 0.1);
		put ("Blueprint", 0.3);
	}};
	
	
	public static void rngLoot(LootSource source) {
		switch (source) {
		case EXPLORATION:
			
			break;
		case ROBBING:
			break;
		case SCAVENGE:
			Inventory stores = Resources.inven.getInventory("Stores");
			for (Map.Entry<String, Double> itemFound : scavengeChanceMap.entrySet()) {
				double rand = Math.random();
				if (rand < itemFound.getValue()) {
					int quantity = 0;
					switch (itemFound.getKey()) {
					case "Ores":
						quantity = (int) (Math.random() * 50);
						break;
					case "Copper":
						quantity = (int) (Math.random() * 50);
						break;
					case "Cloth":
						quantity = (int) (Math.random() * 50);
						break;
					case "Glass Bits":
						quantity = (int) (Math.random() * 50);
						break;
					case "Metal Alloy" :
						quantity = (int) (Math.random() * 50);
						break;
					case "Blueprint": 
						quantity = 1;
						break;
					}
					stores.increseQuantity(itemFound.getKey(), quantity);
				}
			}
			break;
		case WINNING_FIGHTS:
			break;
		default:
			break;
		}
	
	}
}
