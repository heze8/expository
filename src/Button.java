import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Button extends JPanel implements ExpositoryConstant {
	private HashMap<String, HashMap<JLabel, Integer>> btnDB = new HashMap<String, HashMap<JLabel, Integer>>();
	
	/**
	 * Constructor for the Button Control class, creates a title for the set of controls if needed.
	 * @param controlTitle of type String, provides the title associated with the set of buttons
	 * @param displayTitle of type boolean, results in the title being displayed for the buttons 
	 * if set to true.
	 */
	public Button (String controlTitle, boolean displayTitle) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		if (displayTitle) {
			Border empty = BorderFactory.createEmptyBorder();
			TitledBorder title = BorderFactory.createTitledBorder(empty, controlTitle);
			setBorder(title);
		}
	}
	
	/**
	 * Allows individual buttons to be added to the Button group
	 * @param name of type String, provides the name for the btn
	 * @param cooldown of type int, provides the associate value for the time needed between each button press.
	 */
	public void addBtn(String name, int cooldown) {
		JLabel newBtn = new JLabel(name);
		
        int marginLeftRight = (CONTROL_LABEL_LENGTH - newBtn.getPreferredSize().width) / 2;
        Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight
        		, MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight);
        newBtn.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), margin));
        newBtn.setForeground(Color.BLACK);
        newBtn.setBackground(Color.BLACK);
        
        newBtn.addMouseListener(new ButtonClickedListener(cooldown * CONVERT_TO_MSEC));
        add(newBtn);
        
        btnDB.put(name, new HashMap<JLabel, Integer>(){{
        	put (newBtn, cooldown);
        }});
	}
}
