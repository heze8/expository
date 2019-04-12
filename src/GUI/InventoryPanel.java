package GUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ExpositoryConstant.ExpositoryConstant;

public class InventoryPanel extends JPanel implements ExpositoryConstant{
	
	private HashMap<String, Inventory> invenDB = new HashMap<String, Inventory>();
	private ArrayList<String> invenDisplayed = new ArrayList<String>();
	
	/**
	 * Constructor, creates an instance of the InventoryPanel class
	 */
	public InventoryPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(BG_COLOR);
		this.setPreferredSize(new Dimension(INVEN_WIDTH_PREFF, INVEN_HEIGHT_PREFF));
	}
	
	/**
	 * Creates a Inventory group with certain items inside
	 * @param invenGroupTitle of type String, provides the titles for the inventory group
	 * @param invenItems of type HashMap, provides a list of items along with their initial quantity
	 */
	public void createInvenGroup (String invenGroupTitle, HashMap<String, Integer> invenItems) {
		Inventory inven = new Inventory(invenGroupTitle);
		for (String name : invenItems.keySet()) {
			inven.addEntry(name, invenItems.get(name));
		}
		invenDB.put(invenGroupTitle, inven);
	}
	
	/**
	 * Creates an empty Inventory group 
	 * @param invenGroupTitle of type String, provides the titles for the inventory group
	 */
	public void createInvenGroup (String invenGroupTitle) {
		Inventory inven = new Inventory(invenGroupTitle);
		invenDB.put(invenGroupTitle, inven);
	}
	
	/**
	 * Adds an inventory item to s specific inventory group within the InventoryPanel class
	 * @param invenGroupTitle of type String, provides the name of the Inventory group that the new item is to be added too
	 * @param item of type String, provides the name of the new item that is being added
	 * @param quantity of type int, provides the initial quantity that is associated with the item 
	 */
	public void addInvenItem (String invenGroupTitle, String item, int quantity) {
		Inventory inven = invenDB.get(invenGroupTitle);
		inven.addEntry(item, quantity);
	}

	/**
	 * Returns a particular inventory group
	 * @param inventoryName of type String, provides the name of the inventory group for retrieval
	 * @return a particular inventory group
	 */
	public Inventory getInventory(String inventoryName) {
		return invenDB.get(inventoryName);
	}
	
	/**
	 * Shows a particular inventory group on the Jpanel
	 * @param invenGroupTitle of type String, provides the name of the Inventory group to be displayed.
	 */
	public void showInvenGroup (String invenGroupTitle) {
		if (invenDB.get(invenGroupTitle) != null) {
			add(invenDB.get(invenGroupTitle));
			invenDisplayed.add(invenGroupTitle);
			revalidate();
			repaint();
		}
		else {
			System.out.println("Trying to show non existent group" + invenGroupTitle);
		}
	}
	
	/**
	 * Removes a particular inventory group from the Jpanel
	 * @param invenGroupTitle of type String, provides the name of the Inventory group to be removed.
	 */
	public void removeInvenGroup (String invenGroupTitle) {
		if (invenDB.get(invenGroupTitle) != null) {
			this.removeAll();
			invenDisplayed.remove(invenGroupTitle);
			for (String displayedInven : invenDisplayed) {
				add(invenDB.get(displayedInven));
			}
			revalidate();
			repaint();
		}
		else {
			System.out.println("Trying to remove non existent group" + invenGroupTitle);
		}
	}
	
	public boolean isEmpty(String invenGroupTitle) {
		return invenDB.get(invenGroupTitle).isEmpty();
	}
}
