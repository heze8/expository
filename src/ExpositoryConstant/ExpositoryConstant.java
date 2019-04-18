package ExpositoryConstant;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/*
 * FileName: ExpositoryConstant.java
 * --------------------------------
 * This files provides the constants provided for the Expository games
 */

public interface ExpositoryConstant {
	
	public static final int APPLICATION_WIDTH = 1600;
	public static final int APPLICATION_HEIGHT = 720;
	
	/* Constants for the color scheme of text in the game */
	public static final Color BG_COLOR = Color.BLACK;
	public static final Color NORMAL_COLOR = Color.WHITE;
	
	/* Constant for random event occurrence */
	public static final int RANDOM_OCCURRENCE_DELAY = 10000;
	
	/////////////////////////
	// PlayerHUD CONSTANTS //
	/////////////////////////
	/* Constant for various display position in cardLayout display */
	public static final String MAIN_GUI = "1";
	public static final String LAPTOP = "2";
	
	public static final String YOUR_ROOM = "1";
	public static final String SPACESHIP = "2";
	public static final String DUST_CONTAINER = "3";
	
	public static final String DUST = "4";
	public static final String MAP = "5";
	public static final String COMBAT = "6";
	
	/* Enum for locaiton of user within the game HUD */
	public enum Location {
		ROOM,
		LAPTOP,
		SPACESHIP,
		DUST;
	}
	
	/* Constants for PlayerHUD overall margin */
	public static final int PLAYER_HUD_MARGIN = 10;
	
	/////////////////////////
	// PlusMinus CONSTANTS //
	/////////////////////////
	/* Constants for the JLabel extraction in PlusMinus class when incrementing/decreasing it*/
	public static final int JLABEL = 2;
	
	/* Constant for plusMinusBtn class for event firing */
	public static final String DECREASE = "Decrease";
	public static final String INCREASE = "Increase";
		
	//////////////////////
	// Button CONSTANTS //
	//////////////////////
	/* Constant for JLabel extraction in Button class when changing name */
	public static enum btnData {
		JLABEL,
		COOLDOWN,
		COST, 
		RECLICKABLE;
	}
	
	public static enum pressedBtnData {
		X_POS,
		Y_POS,
		WIDTH,
		HEIGHT;
	}
	
	public static enum ExploreBtnEvent {
		NOTHING,
		FIND_NANOBOT,
		FIND_DOOR;
	}
	
	/* Constant for PlayerHUD control colors */
	public static final Color CLICKED_COLOR = Color.RED;
	public static final Color HOVER_COLOR = Color.LIGHT_GRAY;
	
	/* Constant for the margin of the text labels for controls */
	public static final int MARGIN_TOP_BOTTOM_CONTROL = 10;
	public static final int CONTROL_LABEL_LENGTH= 100;
	
	/* Constant for conversion of cooldown value from seconds to milliseconds for button controls */
	public static final int SEC_TO_MSEC = 1000;
	
	/* Constant for update interval for button cooldown control animation */
	public static final int UPDATE_TIME = 50;
	
	/* Constant for buttons to have various cooldown time */
	public static final int NO_WAIT = 0;
	public static final int NOT_RECLICKABLE = 1;
	public static final int DEFAULT_WAIT = 5;
	public static final int LONG_WAIT = 10;
	public static final int NANOBOT_LIFESPAN = 20;
	
	/////////////////////////
	// Inventory CONSTANTS //
	/////////////////////////
	/* Constants for the width and height of inventory */
	public static final int INVEN_WIDTH_PREFF = 220;
	public static final int INVEN_HEIGHT_PREFF = 720;
	
	/* Constant for the Inventory margins */
	public static final int MARGIN_TOP_BOTTOM_INVEN= 10;
	public static final int INVENTORY_WIDTH_MARGIN = 10;
	
	/* Constants for the Inventory Names */
	public static final String STORES = "Stores";
	public static final String WEAPONS = "Weapons";
	public static final String SPACESHIP_PARTS = "Spaceship Parts";
	
	public enum StoreItems {
		METAL_ALLOY ("Metal Alloy"),
		METAL_PLATES ("Metal Plates"),
		CLOTH ("Cloth"),
		WATER ("Water"),
		ORES ("Ores"),
		SILICON_CHIPS ("Silicon Chips"),
		COPPER ("Copper"),
		WIRES ("Wires"),
		BATTERIES ("Batteries"),
		GLASS_BITS ("Glass Bits"),
		GLASS ("Glass");
		
		private String itemStringName;
		StoreItems(String itemStringName) {
			this.itemStringName = itemStringName;
		}
		
		@Override
		public String toString() {
			return itemStringName;
		}
	}
	
	public enum SpaceshipParts {
		SOLAR_PANELS("Solar Panels"), 
		ORE_REFINERY("Ore Refinery"), 
		ALLOY_REFINERY("Alloy Refinery"),
		GLASS_FACTORY("Glass Factory");
//		
//		(5 water -> 1 electricity)                       
//		(2 ores 2 electricity - > 1 metal alloy          
//		, (4 metal alloy 2 electricity -> 1 metal plate) 
//		 (5 glass shards + 2 electricity -> 1 glass)     
		
		private String itemStringName;
		SpaceshipParts (String itemStringName) {
			this.itemStringName = itemStringName;
		}
		
		@Override
		public String toString() {
			return itemStringName;
		}
	}
	
	/////////////////////
	// STORY CONSTANTS //
	/////////////////////
	/* Constants for the prefferred height and width of the story panel */
	public static final int STORY_WIDTH_PREFF = 420;
	public static final int STORY_HEIGHT_PREFF = 720;
	
	public static final int STORY_WIDTH_MAX = 480;
	public static final int STORY_HEIGHT_MAX= 1080;
	
	/* Constant for the delay and update frequency between characters when displaying text in story class */
	public static final int TEXT_FADE_UPDATE= 50;
	public static final int TEXT_FADE_DELAY = 10
			;
	/* Constant for Story font and size */
	public static final String STORY_FONT = "Helvatica";
	public static final int STORY_FONT_SIZE = 20;
	
	/* Constant for Text margin */
	public static final int STORY_MARGIN = 10;
	
	/* Constant for max number of messages to display */
	public static final int MAX_MSG = 12;
	
	/* Constant for colour fade increment in text display */
	public static final int VISIBLE = 255;
	public static final int COLOR_FADE_INCREMENT = 20;
	public static final int INVINSIBLE = 0;
	
	//////////////////////
	// Laptop CONSTANTS //
	//////////////////////
	
	/* Constant for the password hashCode */
	public static final float PASSWORD = 1417332055813389L;
	public static final float AUTHORISATION_CODE = 1999L;
	public static final int HASH_CONSTANT = 31;
	
	/* Constant for Laptop userName */
	public static final String USERNAME = "Hj72669.@234:~$> ";
	
	/* Constant for the Laptop history display */
	public static final int MOST_RECENT = 1;
	public static final int OLDEST = 2;
	
	/* Constant for Laptop slow printing delay*/
	public static final int PRINT_DELAY = 50;
	public static final int PRINT_DELAY_FAST= 10;
	
	/* Constant for laptop elipses print number */
	public static final int NUMBER_OF_DOTS= 3;
		
	/////////////////////////
	// FloorMaps CONSTANTS //
	/////////////////////////
	/* Constants for the world map size */
	public static final int WORLD_HEIGHT = 40;
	public static final int WORLD_WIDTH = 60;
	public static final long SEED_VALUE = 0;
	
	
	/* Constant for the colours of the various terrains in the world */
	
	/* Constants for the color and character representing the terrain */
	public static enum terrain {
		TILE,
		COLOR;
	}
		
	//////////////////////
	// Player CONSTANTS //
	//////////////////////
	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
		
	/////////////////////
	// Event CONSTANTS //
	/////////////////////
	public enum LootSource {
		SCAVENGE,
		WINNING_FIGHTS,
		EXPLORATION,
		ROBBING;
	}
	
	/////////////////////
	// Fight CONSTANTS //
	/////////////////////
	public enum FightStuff {
		PLAYER(1, "Player"),
		ENEMY(1, "Enemy"),
		CAR(2, "Car"),
		TRUCK(3, "Truck"),
		BUS(4, "Bus"),
		TREE(1, "Tree"),
		ROCK(1, "Rock"),
		BUILDING(3, "Building");
		
		private int sizes;
		private String name;
		FightStuff (int size, String stuffName) {
			sizes = size;
			name = stuffName;
		}
		
		public int getSize() {
			return sizes;
		}
		
		public String getName() {
			return name;
		}
		
		private static final List<FightStuff> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		public static FightStuff getRandomFightItem()  {
		  return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
	public enum Position {
		FRONT_LEFT("Front Left"),
		FRONT ("Front"),
		FRONT_RIGHT ("Front Right"),
		RIGHT ("Right"),
		BACK_RIGHT ("Back Right"),
		BACK ("Back"),
		BACK_LEFT ("Back Left"),
		LEFT ("Left");
		
		private String name;
		Position (String stuffName) {
			name = stuffName;
		}
		public String getName() {
			return name;
		}
	}
	
	public enum Actions {
		MOVE("Move"),
		SHOOT("Shoot"),
		MELEE("Melee");
		
		private String name;
		Actions (String actionName) {
			name = actionName;
		}
		public String getName() {
			return name;
		}
	}
	
	public enum Moves {
		UP("Up"),
		DOWN("Down"),
		LEFT("Left"),
		RIGHT("Right"),
		SHOOT("Shoot"),
		MELEE("Melee");
		
		private String name;
		Moves (String actionName) {
			name = actionName;
		}
		public String getName() {
			return name;
		}
	}

	
	public static int BATTLEFIELD_WIDTH = 10;
	public static int BATTLEFIELD_HEIGHT = 10;
}
