package GUI;
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;

public class Inventory extends JPanel implements ExpositoryConstant {
	private HashMap<String, JLabel> inventoryDB = new HashMap<String, JLabel>();
	
	/**
	 * Constructor of the Inventory Class. 
	 * Initialize an empty inventory with a title base on @param titleString.
	 * Creates a titledBorder around the whole class.
	 * @param titleString is of type String, provides the title for the inventory
	 */
	public Inventory (String titleString) {
		this.setBackground(BG_COLOR);
		
		Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_INVEN
        		, INVENTORY_WIDTH_MARGIN
        		, MARGIN_TOP_BOTTOM_INVEN
        		, INVENTORY_WIDTH_MARGIN);
		
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(NORMAL_COLOR), 
				titleString, 
				TitledBorder.CENTER, 
				TitledBorder.TOP, 
				new Font (STORY_FONT, Font.BOLD, 12), 
				NORMAL_COLOR);
	    setBorder(new CompoundBorder(title, margin));
	    
	    setLayout(new GridLayout( 0 , 2, 40, 10));
	    
	}
	
	/**
	 * Adds an entry into the inventory by taking the two parameter below.
	 * New Entry is added below all other previous entry.
	 * Two parameters are then stored into a HashMap called inventoryDB for accessing records in other methods.
	 * @param name of type String, provides the name of item being added.
	 * @param quantity of type int, provides the quantity of the item being added.
	 */
	public void addEntry (String name, int quantity) {
		JLabel entry = new JLabel(name);
		entry.setForeground(NORMAL_COLOR);
		JLabel quantityLabel = new JLabel("" + quantity);
		quantityLabel.setForeground(NORMAL_COLOR);
		inventoryDB.put(name, quantityLabel);
		
		add(entry);
		add(quantityLabel);		
	}
	
	/**
	 * Gets the number of quantity associated with a particular item. Item must already exist.
	 * Returns error if the item searched for cannot be found.
	 * @param name of type String, provides the item for which the quantity associated
	 * with it is to be retrieved
	 * @return an int, representing the quantity associated with @param name.
	 * @throws IllegalAccessException if the @param name is not found within the inventoryDB
	 */
	public int getQuantity(String item) {
		JLabel quantity = inventoryDB.get(item);
		try{
			if (quantity != null) {
				int quant = Integer.parseInt(quantity.getText());
				return quant;			
			} 
			else {
				throw new IllegalAccessException();
			} 
		}
		catch(IllegalAccessException e) {
			System.out.println("Item " + item + " does not exist in inventory!");
			return 0;
		}
	}
	
	/**
	 * Allows user to update the quantity of a particular item
	 * @param name of type String, provides the name of item for which
	 * the quantity is to be updated for
	 * @param newQuantity of type int, provides the newQuantity that will replace 
	 * the current quantity value associated with @param name
	 */
	public void setQuantity (String item, int newQuantity) {
		inventoryDB.get(item).setText("" + newQuantity);
	}
	
	/**
	 * Increments the quantity of a particular item. Item must already exist.
	 * @param name of type String, provides the name of the item for which the 
	 * quantity is to be incremented.
	 * @param quantity of type int, provides the amount to increase the item quantity by
	 */
	public void increseQuantity (String item, int quantity) {
		inventoryDB.get(item).setText("" + (getQuantity(item) + quantity));
	}

	/**
	 * Decreases the quantity of a particular item. Item must already exist.
	 * @param name of type String, provides the name of the item for which the 
	 * quantity is to be decreased.
	 * @param quantity of type int, provides the amount to decrease the item quantity by
	 */
	public void decreaseQuantity (String item, int quantity) {
		if (getQuantity(item) - quantity >= 0) {
			inventoryDB.get(item).setText("" + (getQuantity(item) - quantity));
		}
	}
	
	/**
	 * Provides the user with all the items currently in the inventory
	 * @return  Set<String> of all the names of the items in the inventory.
	 */
	public Set<String> getItemSet () {
		return inventoryDB.keySet();
	}
	
	public boolean isEmpty() {
		return inventoryDB.isEmpty();
	}
}
