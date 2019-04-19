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
	
	private static HashMap<String, Double> winFightChanceMap = new HashMap<String, Double>() {{
		put("Ores", 0.5);
		put ("Copper", 0.35);
		put("Cloth", 0.5);
		put ("Glass Bits", 0.5);
		put ("Metal Alloy", 0.1);
		put ("Blueprint", 0.3);
		put ("Spaceship fuel", 0.1);
	}};
	
	
	public static void rngLoot(LootSource source) {
		Inventory stores = Resources.inven.getInventory("Stores");
		String thingsFound = " ";
		switch (source) {
		case EXPLORATION:
			break;
		case ROBBING:
			break;
		case SCAVENGE:
			thingsFound = "Scavenging the nearby area, the find included ";
			for (Map.Entry<String, Double> itemFound : scavengeChanceMap.entrySet()) {
				double rand = Math.random();
				if (rand < itemFound.getValue()) {
					int quantity = 0;
					switch (itemFound.getKey()) {
					case "Ores":
						thingsFound += "loose stones which proved to be valuable. ";
						quantity = (int) (Math.random() * 50);
						break;
					case "Copper":
						thingsFound += "Copper wires strewn on the sand. ";
						quantity = (int) (Math.random() * 50);
						break;
					case "Cloth":
						thingsFound += "Scraps of torn cloth. ";
						quantity = (int) (Math.random() * 50);
						break;
					case "Glass Bits":
						thingsFound += "Glass Bits from a shattered object. ";
						quantity = (int) (Math.random() * 50);
						break;
					case "Metal Alloy" :
						thingsFound += "Pricey alloy from a broken robot. ";
						quantity = (int) (Math.random() * 50);
						break;
					case "Blueprint": 
						thingsFound += "knowledge. ";
						quantity = 1;
						break;
					}
					stores.increseQuantity(itemFound.getKey(), quantity);
				}
			}
			Resources.story.displayText(thingsFound);
			break;
		case WINNING_FIGHTS:
			thingsFound = "Searching your enemy, the find included ";
			for (Map.Entry<String, Double> itemFound : scavengeChanceMap.entrySet()) {
				double rand = Math.random();
				if (rand < itemFound.getValue()) {
					int quantity = 0;
					switch (itemFound.getKey()) {
					case "Ores":
						thingsFound += "loose stones which proved to be valuable. ";
						quantity = (int) (Math.random() * 20);
						break;
					case "Copper":
						thingsFound += "Copper wires strewn on the sand. ";
						quantity = (int) (Math.random() * 20);
						break;
					case "Cloth":
						thingsFound += "Scraps of torn cloth. ";
						quantity = (int) (Math.random() * 20);
						break;
					case "Glass Bits":
						thingsFound += "Glass Bits from a shattered object. ";
						quantity = (int) (Math.random() * 20);
						break;
					case "Metal Alloy" :
						thingsFound += "Pricey alloy from a broken robot. ";
						quantity = (int) (Math.random() * 10);
						break;
					case "Blueprint": 
						thingsFound += "knowledge. ";
						quantity =  1;
						break;
					case "Spaceship fuel":
						quantity = (int) (Math.random() * 15);
						break;
					}
					stores.increseQuantity(itemFound.getKey(), quantity);
				}
			}
			Resources.story.displayText(thingsFound);
			break;
		default:
			break;
		}
		
	}
}
