/*
 * FileName: ExpositoryConstant.java
 * --------------------------------
 * This files provides the constants provided for the Expository games
 */

public interface ExpositoryConstant {
	public static final int APPLICATION_WIDTH = 1280;
	public static final int APPLICATION_HEIGHT = 720;
	
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
}
