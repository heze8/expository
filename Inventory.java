import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Inventory extends JPanel implements ExpositoryConstant {
	private int currRow = 0;
	private GridBagConstraints gc = new GridBagConstraints();
	private HashMap<String, JLabel> inventoryDB = new HashMap<String, JLabel>();
	
	/**
	 * Constructor of the Inventory Class. 
	 * Initialize an empty inventory with a title base on @param titleString.
	 * Creates a titledBorder around the whole class.
	 * @param titleString is of type String, provides the title for the inventory
	 */
	public Inventory (String titleString) {
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder(titleString);
		Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_INVEN
        		, INVENTORY_WIDTH_MARGIN
        		, MARGIN_TOP_BOTTOM_INVEN
        		, INVENTORY_WIDTH_MARGIN);
        setBorder(new CompoundBorder(title, margin));
        
        setLayout(new GridBagLayout());
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
		JLabel quantityLabel = new JLabel("" + quantity);
		inventoryDB.put(name, quantityLabel);
		
		//Adding to JPanel display
		gc.weightx = 1;
		gc.weighty = 1;
		
		//Adding Name of inventory item
		gc.gridx = 0;
		gc.gridy = currRow ++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(entry, gc);
		
		//Adding quantity of inventory item
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(quantityLabel, gc);
	}
	
	/**
	 * Gets the number of quantity associated with a particular item. Item must already exist.
	 * Returns error if the item searched for cannot be found.
	 * @param name of type String, provides the item for which the quantity associated
	 * with it is to be retrieved
	 * @return an int, representing the quantity associated with @param name.
	 * @throws IllegalAccessException if the @param name is not found within the inventoryDB
	 */
	public int getQuantity(String name) {
		JLabel quantity = inventoryDB.get(name);
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
			System.out.println("Item " + name + " does not exist in inventory!");
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
	public void updateQuantity (String name, int newQuantity) {
		inventoryDB.get(name).setText("" + newQuantity);
	}
	
	/**
	 * Increments the quantity of a particular item by 1. Item must already exist.
	 * @param name of type String, provides the name of the item for which the 
	 * quantity is to be incremented.
	 * @throws IllegalAccessException if @param name does not exist
	 */
	public void increseQuantity (String name) throws IllegalAccessException {
		inventoryDB.get(name).setText("" + (getQuantity(name) + 1));
	}

	/**
	 * Decreases the quantity of a particular item by 1. Item must already exist.
	 * @param name of type String, provides the name of the item for which the 
	 * quantity is to be decreased.
	 * @throws IllegalAccessException if @param name does not exist
	 */
	public void decreaseQuantity (String name) throws IllegalAccessException {
		inventoryDB.get(name).setText("" + (getQuantity(name) - 1));
	}
	
	/**
	 * Provides the user with all the items currently in the inventory
	 * @return  Set<String> of all the names of the items in the inventory.
	 */
	public Set<String> getItemSet () {
		return inventoryDB.keySet();
	}
}
