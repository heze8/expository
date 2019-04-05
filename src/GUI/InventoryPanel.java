package GUI;
import java.awt.*;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ExpositoryConstant.ExpositoryConstant;

public class InventoryPanel extends JPanel implements ExpositoryConstant{
	
	private HashMap<String, Inventory> invenDB = new HashMap<String, Inventory>();
	
	public InventoryPanel() {
		this.setLayout(new FlowLayout());
		this.setBackground(BG_COLOR);
		this.setPreferredSize(new Dimension(INVEN_WIDTH_PREFF, INVEN_HEIGHT_PREFF));
	}
	
	public void createInvenGroup (String invenGroupTitle, HashMap<String, Integer> invenItems) {
		Inventory inven = new Inventory(invenGroupTitle);
		for (String name : invenItems.keySet()) {
			inven.addEntry(name, invenItems.get(name));
		}
		invenDB.put(invenGroupTitle, inven);
		this.add(inven);
	}
	
	public void addInvenItem (String invenGroupTitle, String item, int quantity) {
		Inventory inven = invenDB.get(invenGroupTitle);
		inven.addEntry(item, quantity);
	}

	public Inventory getInventory(String inventoryName) {
		return invenDB.get(inventoryName);
	}
}
