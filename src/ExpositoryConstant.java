/*
 * FileName: ExpositoryConstant.java
 * --------------------------------
 * This files provides the constants provided for the Expository games
 */

public interface ExpositoryConstant {
	public static final int APPLICATION_WIDTH = 1280;
	public static final int APPLICATION_HEIGHT = 720;
	
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
	
	/* Constant for the Inventory margins */
	public static final int MARGIN_TOP_BOTTOM_INVEN= 10;
	public static final int INVENTORY_WIDTH_MARGIN = 10;
	
	/* Constant for the col where the inventory will appear at for PlayerHUD */
	public static final int INVENTORY_COL = 1;
	
	/* Constant for the delay between characters when displaying text in story class*/
	public static final int TEXT_DISP_DELAY = 50;
	
	/* Constant for Story font */
	public static final String STORY_FONT = "Helvatica";
	
	/* Constant for Story font size */
	public static final int STORY_FONT_SIZE = 20;
}
