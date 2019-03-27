import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.EventListenerList;

public class PlayerHUD extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables*/
	Inventory inven;
	private GridBagConstraints gc = new GridBagConstraints();
	
	private HashMap<String, JPanel> controlsDB = new HashMap<String, JPanel>();
	private HashMap<String, HashMap<String, JLabel>> controlButtonsDB = new HashMap<String, HashMap<String, JLabel>>();
	private int currControlRow = 0;
	
	private HashMap<String, Inventory> invenDB = new HashMap<String, Inventory>();
	private int currInvenRow = 0;
	
	private EventListenerList listenerList = new EventListenerList();
	
	public PlayerHUD () throws IllegalAccessException {
		setLayout(new GridBagLayout()); 
		gc.weightx = 1;
		gc.weighty = 1;
	}

	public void createButtons (String controlTitle, boolean displayTitle, HashMap<String, Integer> buttonNamesToDelay) {
		JPanel buttonControls = new JPanel();
		buttonControls.setLayout(new BoxLayout(buttonControls, BoxLayout.PAGE_AXIS));
		if (displayTitle) {
			Border empty = BorderFactory.createEmptyBorder();
			TitledBorder title = BorderFactory.createTitledBorder(empty, controlTitle);
			buttonControls.setBorder(title);
		}
		
		for (String name : buttonNamesToDelay.keySet()) {
			JLabel toDisplay = createLabel(name, buttonNamesToDelay.get(name));
			controlButtonsDB.get(controlTitle).put(name, toDisplay);
			buttonControls.add(toDisplay);
		}
		controlsDB.put(controlTitle, buttonControls);
		
		gc.gridx = CONTROL_COL;
		gc.gridy = currControlRow ++;
		add(buttonControls, gc);	
	}
	
	public void addButtonControl (String btnControlTitle, String btnName, int delay) {
		
	}
	
	private JLabel createLabel(String text, int delay) {
		JLabel lbl = new JLabel(text);
        int marginLeftRight = (CONTROL_LABEL_LENGTH - lbl.getPreferredSize().width) / 2;
        Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight
        		, MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight);
        lbl.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), margin));
        lbl.setForeground(Color.BLACK);
        lbl.setBackground(Color.BLACK);
        
        lbl.addMouseListener(new MouseListener() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {}

        	@Override
        	public void mouseEntered(MouseEvent arg0) {
        		JLabel label1 = (JLabel)arg0.getSource();		
        		if (label1.getForeground() != Color.RED) {
        			label1.setForeground(Color.LIGHT_GRAY);
        			label1.setBackground(Color.LIGHT_GRAY);	
        		}
        	}

        	@Override
        	public void mouseExited(MouseEvent arg0) {
        		JLabel label1 = (JLabel)arg0.getSource();
        		if (label1.getForeground() != Color.RED) {
        			label1.setForeground(Color.BLACK);
        			label1.setBackground(Color.BLACK);	
        		}
        	}

        	@Override
        	public void mousePressed(MouseEvent arg0) {
        		JLabel label1 = (JLabel)arg0.getSource();
        		if (label1.getForeground() != Color.RED) {
        			// Changes button color to show that button is not clickable
        			label1.setForeground(Color.RED);
        			label1.setBackground(Color.RED);
        			
        			// Changes the button color back to original after delay ms has passed
        			// Allows the button to be clicked again
		    		Timer timer = new Timer(delay, new ActionListener(){
		    		  @Override
		    		  public void actionPerformed( ActionEvent e ){
		    		    label1.setForeground( Color.BLACK);
		    		    label1.setBackground(Color.BLACK);
		    		  }
		    		} );
		    		timer.setRepeats( false );
		    		timer.start();
		    		
		    		fireHUDEvent(new HUDEvent(this));
        		}
        	}

        	@Override
        	public void mouseReleased(MouseEvent arg0) {}
        });
        return lbl;
	}

	
	public void createInven (String invenTitle, HashMap<String, Integer> invenItems) {
		Inventory inven = new Inventory(invenTitle);
		for (String name : invenItems.keySet()) {
			inven.addEntry(name, invenItems.get(name));
		}
		invenDB.put(invenTitle, inven);
		
		gc.gridx = INVENTORY_COL;
		gc.gridy = currInvenRow ++;
		add(inven, gc);
	}
	
	public void addInvenItem (String invenTitle, String item, int Quantity) {
		
	}
	
	public void fireHUDEvent(HUDEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == HUDEventListener.class) {
				((HUDEventListener) listeners[i + 1]).HUDEventOccurred(e);
			}
		}
	}
	
	public void addHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.add(HUDEventListener.class, HUDEventListener);
	}
	
	public void removeHUDEventListener(HUDEventListener HUDEventListener) {
		listenerList.remove(HUDEventListener.class, HUDEventListener);
	}
}
