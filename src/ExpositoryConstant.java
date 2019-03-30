/*
 * FileName: ExpositoryConstant.java
 * --------------------------------
 * This files provides the constants provided for the Expository games
 */

public interface ExpositoryConstant {
	public static final int APPLICATION_WIDTH = 1280;
	public static final int APPLICATION_HEIGHT = 720;
	
	/////////////////////////
	// PlayerHUD CONSTANTS //
	/////////////////////////
	/* Constant for PlayerHUD weight */
	public static final int WEIGHT_X = 1;
	public static final int WEIGHT_Y = 1;
	
	/* Constant for the margin of the text labels for controls */
	public static final int MARGIN_TOP_BOTTOM_CONTROL = 10;
	public static final int CONTROL_LABEL_LENGTH= 100;
	
	/* Constant for conversion of cooldown value from seconds to milliseconds for button controls */
	public static final int CONVERT_TO_MSEC = 1000;
	
	/* Constant for the col where the controls will appear at for PlayerHUD */
	public static final int CONTROL_COL = 0;
	
	/////////////////////////
	// Inventory CONSTANTS //
	/////////////////////////
	/* Constant for the Inventory margins */
	public static final int MARGIN_TOP_BOTTOM_INVEN= 10;
	public static final int INVENTORY_WIDTH_MARGIN = 10;
	
	/* Constant for the col where the inventory will appear at for PlayerHUD */
	public static final int INVENTORY_COL = 1;
	
	
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
}
