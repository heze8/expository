import java.awt.*;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel implements ExpositoryConstant{
	
	private HashMap<String, Inventory> invenDB = new HashMap<String, Inventory>();
	
	public InventoryPanel() {
		this.setLayout(new FlowLayout());
		this.setBackground(BG_COLOR);
		this.setPreferredSize(new Dimension(INVEN_WIDTH_PREFF, INVEN_HEIGHT_PREFF));
	}
	
	public void createInven (String invenTitle, HashMap<String, Integer> invenItems) {
		Inventory inven = new Inventory(invenTitle);
		for (String name : invenItems.keySet()) {
			inven.addEntry(name, invenItems.get(name));
		}
		invenDB.put(invenTitle, inven);
		this.add(inven);
	}
	
	public void addInvenItem (String invenTitle, String item, int quantity) {
		Inventory inven = invenDB.get(invenTitle);
		inven.addEntry(item, quantity);
	}
}
