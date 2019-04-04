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
	private PlusMinusListener plusMinusListener;

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
		decrease.addBtn("<", NO_WAIT, false);
		Button increase = new Button (btnName, false);
		increase.addBtn(">", NO_WAIT, false);
		decrease.addButtonListener(this);
		increase.addButtonListener(this);
		JLabel quantityLabel = new JLabel(String.valueOf(initQuantity));
		quantityLabel.setForeground(NORMAL_COLOR);
		quantityLabel.setFont(new Font(STORY_FONT, Font.PLAIN, 15));
		
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
		
		Vector data = new Vector() {{
			add(decrease);
			add(increase);
			add(quantityLabel);
			add(controller);
			
		}};
		plusMinusBtnDB.put(btnName, data);
		
		this.add(controller);
	}
	
	public void buttonPressed (ButtonEvent e) {
		String btnName = e.getTitle();
		Vector data = plusMinusBtnDB.get(btnName);
		JLabel affectedQuan = (JLabel) data.get(JLABEL);
		if (e.getBtnName().equals("<")) {
			if (Integer.parseInt(affectedQuan.getText()) > 0 
					&& plusMinusListener.plusMinusClicked(DECREASE, btnName)) {
				String newQuan = String.valueOf(Integer.parseInt(affectedQuan.getText()) - 1);
				affectedQuan.setText(newQuan);
			}
		} else if (e.getBtnName().equals(">")) {
			if (Integer.parseInt(affectedQuan.getText()) >= 0 
					&& plusMinusListener.plusMinusClicked(INCREASE, btnName)) {
				String newQuan = String.valueOf(Integer.parseInt(affectedQuan.getText()) + 1);
				affectedQuan.setText(newQuan);
			}
		}
	}

	public void addPlusMinusListener(PlusMinusListener plusMinusListener) {
		this.plusMinusListener = plusMinusListener;
	}

}
