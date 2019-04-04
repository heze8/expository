import java.awt.*;

import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI.Button;
import GUI.PlusMinusBtn;

public class Nanobot extends JPanel implements ExpositoryConstant {
	private int capacity = 5;

	public Nanobot (String name) {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		addNameLabel(name);
		addControls();
	}
	
	private void addNameLabel(String name) {
		JLabel nameLabel = new JLabel(name);
		nameLabel.setForeground(NORMAL_COLOR);
		nameLabel.setFont(new Font (STORY_FONT, Font.BOLD, 15));
		nameLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		add(nameLabel, BorderLayout.LINE_START);
	}

	private void addControls() {
		JPanel controls = new JPanel();
		controls.setBackground(BG_COLOR);
		controls.setLayout(new FlowLayout(FlowLayout.LEADING));
		initControlsPanel(controls);
		add(controls, BorderLayout.CENTER);
		
	}

	private void initControlsPanel(JPanel controls) {
		Button fuel = new Button("Fuel", true);
		fuel.addBtn("Fuel", 20, true);
		
		Button health = new Button("Health", true);
		health.addBtn("Health", 20, true);
		
		Button upgrade = new Button("Upgrade", true);
		upgrade.addBtn("Upgrade", 20, true);
		
		PlusMinusBtn water = new PlusMinusBtn("Parameters", false);
		water.addPlusMinus(0, "Water", true);
		
		controls.add(fuel);
		controls.add(health);
		controls.add(water);
		controls.add(upgrade);
		
	}

	public void addParam (String param) {
		PlusMinusBtn newParam = new PlusMinusBtn("Parameters", false);
		newParam.addPlusMinus(0, param, true);
		add(newParam);
	}
	
	public void upgrade() {
		
	}
}
