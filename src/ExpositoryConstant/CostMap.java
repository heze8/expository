package ExpositoryConstant;

import ExpositoryConstant.ExpositoryConstant.*;

import java.util.HashMap;

public class CostMap {
	public static HashMap<String, Integer> exploreCost = new HashMap<String, Integer>() {{
		put (StoreItems.WATER.toString(), 1);
	}};	
	
	public static HashMap<String, Integer> solarPanelsCost = new HashMap<String, Integer>() {{
		put (StoreItems.WATER.toString(), 100);
		put (StoreItems.ORES.toString(), 150);
		put (StoreItems.GLASS_BITS.toString(), 80);
	}};	
	public static HashMap<String, Integer> oreRefineryCost = new HashMap<String, Integer>() {{
		put (StoreItems.WATER.toString(), 100);
		put (StoreItems.ORES.toString(), 150);
		put (StoreItems.GLASS_BITS.toString(), 80);
	}};
	public static HashMap<String, Integer> alloyRefineryCost = new HashMap<String, Integer>() {{
		put (StoreItems.WATER.toString(), 100);
		put (StoreItems.ORES.toString(), 150);
		put (StoreItems.GLASS_BITS.toString(), 80);
	}};
	public static HashMap<String, Integer> glassFactroyCost = new HashMap<String, Integer>() {{
		put (StoreItems.WATER.toString(), 100);
		put (StoreItems.ORES.toString(), 150);
		put (StoreItems.GLASS_BITS.toString(), 80);
	}};
	
	public static HashMap<String, HashMap<String, Integer>> costMap = new HashMap<String, HashMap<String, Integer>>() {{
		put ("Explore", exploreCost);
		put (SpaceshipParts.SOLAR_PANELS.toString(), solarPanelsCost);
		put (SpaceshipParts.ORE_REFINERY.toString(), oreRefineryCost);
		put (SpaceshipParts.ALLOY_REFINERY.toString(), alloyRefineryCost);
		put (SpaceshipParts.GLASS_FACTORY.toString(), glassFactroyCost);
	}};
}
