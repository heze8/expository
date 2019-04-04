package GUI;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.*;

public class PlusMinusBtn extends JPanel implements ExpositoryConstant, ButtonListener {
	private HashMap<String, Vector> plusMinusBtnDB = new HashMap<String, Vector>();
	private EventListenerList listenerList = new EventListenerList();
	
	public PlusMinusBtn (String title, boolean displayTitle) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBackground(BG_COLOR);
		if (displayTitle) {
			Border empty = BorderFactory.createEmptyBorder();
			TitledBorder titleBorder = BorderFactory.createTitledBorder(empty, title);
			setBorder(titleBorder);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addPlusMinus(int initQuantity, String btnName, boolean clickable) {	
		Button decrease = new Button (btnName, false);
		decrease.addBtn("<", 1, false);
		Button increase = new Button (btnName, false);
		increase.addBtn(">", 1, false);
		decrease.addButtonListener(this);
		increase.addButtonListener(this);
		JLabel quantityLabel = new JLabel(String.valueOf(initQuantity));
		quantityLabel.setForeground(NORMAL_COLOR);
		quantityLabel.setFont(new Font(STORY_FONT, Font.PLAIN, 15));
		
		Vector data = new Vector() {{
			add(decrease);
			add(increase);
			add(quantityLabel);
		}};
		System.out.println(data);
		plusMinusBtnDB.put(btnName, data);
		
		JPanel controller = new JPanel(new FlowLayout(FlowLayout.LEADING));
		controller.setBackground(BG_COLOR);
		controller.add(decrease);
		controller.add(quantityLabel);
		controller.add(increase);
		
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), 
				btnName, 
				TitledBorder.CENTER, 
				TitledBorder.TOP, 
				new Font (STORY_FONT, Font.BOLD, 12), 
				NORMAL_COLOR);
		controller.setBorder(title);
		this.add(controller);
	}
	
	public void buttonPressed (ButtonEvent e) {
		Vector data = plusMinusBtnDB.get(e.getTitle());
		JLabel affectedQuan = (JLabel) data.get(2);
		if (e.getBtnName().equals("<")) {
			if (Integer.parseInt(affectedQuan.getText()) > 0) {
				String newQuan = String.valueOf(Integer.parseInt(affectedQuan.getText()) - 1);
				affectedQuan.setText(newQuan);
			}
		} else if (e.getBtnName().equals(">")) {
			if (Integer.parseInt(affectedQuan.getText()) >= 0) {
				String newQuan = String.valueOf(Integer.parseInt(affectedQuan.getText()) + 1);
				affectedQuan.setText(newQuan);
			}
		}
	}
	
	public void fireButtonEvent(ButtonEvent be) {
		Object[] listeners = listenerList.getListenerList();
		
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ButtonListener.class) {
				((ButtonListener) listeners[i + 1]).buttonPressed(be);
			}
		}
	}
	
	public void addButtonListener (ButtonListener e) {
		listenerList.add(ButtonListener.class, e);
	}
	
	public void removeButtonListener (ButtonListener e) {
		listenerList.remove(ButtonListener.class, e);
	}
}
