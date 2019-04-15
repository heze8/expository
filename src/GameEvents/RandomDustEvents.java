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
			String thingsFound = "Scavenging the nearby area, the find included ";
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
			break;
		default:
			break;
		}
	
	}

	public static void gotFight() {
		// TODO Auto-generated method stub
		// RNG, if hit a certain pecentage (e.g. 30%) fight occurs
		// when fight occurs, change screen to fight screen, displaying text of players and opponent moves
			// behind the scene, a grid (6X6) is initialised and the player is randomly placed on the second last row, 
			// while the opponent on the second row.
			// cover from 1x1 to 3x1 are initialized on the grid in a horizontal fashion
			
		//Some text that appears to the user:
	        /* On describing character movement */
		        // "You moved right"
		        // "You dashed right"
		        // "You moved left"
		        // "You moved forward"
		        
	        /* On describing retreating from the fight */
		        // "You backed off"
		        // "You retreated from the fight"
		        // "You fled from the fight"
		        
	        /* On describing coming up against battlefield boundaries and taking cover */
		        // "You are up against the edge of a building on your left"
		        // "You are up against the edge of a building right"
		        // "An abandoned car/bus/truck lie to your..."
		        // "A hunk of stone lie to your..."
		        // "A tree lie to your..."
		        // "You take cover behind the tree/car/bus/truck/rock"
		        
	        /* On describing attacking */
				// "You let loose a shot"
		        // "You fired the gun"
				// ”You rose from your cover and risked a shot”
				// "You threw a punch"
				// “you sprung from your cover and attempted a surprise melee”
				// "You drove your fist straight at the guy"
				// “Your cover prevents you from shooting right now”
		        
	        /* on describing how the attack went */
		        // "The shot went to the left"
		        // "The shot went to the right"
		        // "The punch went to the right"
		        // "The punch went to the right"
		        // "Argh, you could have won if not for the tree/truck/car/bus your enemy was hiding behind"
		        // "There’s something in between the two of you that’s absorbing all your shots"
	        
	        /* On describing your opponents movement */
				// "The enemy moved to your right"
		        // "The Enemy dashed to your right"
				// "The enemy moved to your left"
		        // "The Enemy dashed to your left"
				// “The enemy moved forward”
				// “The enemy dashed forward”   

		
	}
}
