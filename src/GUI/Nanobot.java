package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.EventListenerList;

import ExpositoryConstant.ExpositoryConstant;
import GUI_Event_Handlers.ButtonEvent;
import GUI_Event_Handlers.ButtonListener;
import GUI_Event_Handlers.NanobotListener;
import GUI_Event_Handlers.PlusMinusListener;

public class Nanobot extends JPanel implements ExpositoryConstant {
	public static int totalBot = 0;
	private int maxFreeCapacity = 5;
	private int currFreeCapacity = maxFreeCapacity;
	private Button fuel;
	private Button health;
	private Button upgrade;
	private PlusMinusBtn parameters;
	private JPanel controls;
	
	private boolean fueled = false;
	private boolean repaired = false;
	
	private HashMap<String, Integer> params = new HashMap<String, Integer>();
	private EventListenerList listenerList = new EventListenerList();	
	
	public Nanobot (String name) {
		this.setLayout(new BorderLayout());
		this.setBackground(BG_COLOR);
		addNameLabel(name);
		addControls();
		Thread t = new Thread(new Runnable () {
			public void run() {
				Timer timer = new Timer(SEC_TO_MSEC, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (fueled && repaired)
							eventOccurred();
					}
				});
				timer.start();
			}
		});
		t.start();
	}
	
	private void addNameLabel(String name) {
		JLabel nameLabel = new JLabel(name);
		nameLabel.setForeground(NORMAL_COLOR);
		nameLabel.setFont(new Font (STORY_FONT, Font.BOLD, 15));
		nameLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		add(nameLabel, BorderLayout.LINE_START);
	}

	private void addControls() {
		controls = new JPanel();
		controls.setBackground(BG_COLOR);
		controls.setLayout(new FlowLayout(FlowLayout.LEADING));
		initControlsPanel(controls);
		add(controls, BorderLayout.CENTER);
	}

	private void initControlsPanel(JPanel controls) {
		fuel = new Button("Fuel", false);
		fuel.addBtn("Fuel", NANOBOT_LIFESPAN, true);
		fuel.addButtonListener(new buttonListenerForBots(JobForBots.FUEL));
		
		health = new Button("Health", false);
		health.addBtn("Repair", NANOBOT_LIFESPAN, true);
		health.addButtonListener(new buttonListenerForBots(JobForBots.REPAIR));
		
		upgrade = new Button("Capacity: " + currFreeCapacity + "/" + maxFreeCapacity, true);
		upgrade.addBtn("Upgrade", NO_WAIT, true);
		upgrade.addButtonListener( new buttonListenerForBots(JobForBots.UPGRADE));
				
		upgrade.setToolTip("Upgrade", "Cost: ");
		upgrade.setBtnCost("Upgrade", new HashMap<String, Integer> () {{
			put("Water", 10);
		}});
		
		parameters = new PlusMinusBtn("Parameters", false);
		parameters.addPlusMinus(0, "Water", true);
		params.put("Water", 0);
		parameters.addPlusMinusListener(new PlusMinusListener () {
			public boolean plusMinusClicked (String command, String param) {
				boolean canIncrement = false;
				if (command.equals(DECREASE)) {
					if (currFreeCapacity < maxFreeCapacity) {
						currFreeCapacity ++;
						params.put(param, params.get(param) - 1);
						canIncrement = true;
					}
				} else if (command.equals(INCREASE)) {
					if (currFreeCapacity > 0) {
						currFreeCapacity --;
						params.put(param, params.get(param) + 1);
						canIncrement = true;
					}
				}
				upgrade.setTitle("Capacity: " + currFreeCapacity + "/" + maxFreeCapacity);
				upgrade.repaint();
				return canIncrement;
			}
		});
		
		controls.add(fuel);
		controls.add(health);
		controls.add(parameters);
		controls.add(upgrade);
		
	}

	public void addParam (String param) {
		parameters.addPlusMinus(0, param, true);
		params.put(param, 0);
	}
	
	private void upgrade() {
		maxFreeCapacity += 5;
		currFreeCapacity += 5;
		upgrade.setTitle("Capacity: " + currFreeCapacity + "/" + maxFreeCapacity);
		upgrade.repaint();
	}
	
	private void fuelAndOrRepair(JobForBots j) {
		switch (j) {
		
		case REPAIR:
			System.out.println("repaired/");
			repaired = true;
			break;
		case FUEL:
			System.out.println("/fueled");
			fueled = true;
			break;
		default:
			break;
		}
		
		Timer validity = new Timer (NANOBOT_LIFESPAN * SEC_TO_MSEC, new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent e) {
				System.out.println("bot broken");
				switch (j) {
				case REPAIR:
					repaired = false;
					break;
				case FUEL:
					fueled = false;
					break;
				default:
					break;
				}
			}
		});
		validity.setRepeats(false);
		validity.start();
		System.out.println("timer started");
	}
	
	public void eventOccurred() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == NanobotListener.class) {
				((NanobotListener)listeners[i + 1]).nanobotEventOccurred(params);
			}
		}
	}
	
	public boolean repairOccurred(HashMap<String, Integer> costMap) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == NanobotListener.class) {
				return ((NanobotListener)listeners[i + 1]).nanobotRepairOccurred(costMap);
			}
		}
		return false;
	}
	
	public void addNanobotListener(NanobotListener nl) {
		listenerList.add(NanobotListener.class, nl);
	}
	
	public void removeNanobotListener(NanobotListener nl) {
		listenerList.remove(NanobotListener.class, nl);
	}
	
	private class buttonListenerForBots implements ButtonListener {
		private JobForBots repairJob = JobForBots.NOTHING;
		
		public buttonListenerForBots(JobForBots j) {
			repairJob = j;
		}
 
		@Override
		public void buttonPressed(ButtonEvent be) {
		}

		@Override
		public boolean buttonClickable(HashMap<String, Integer> costMap) {
			if (repairOccurred(costMap)) {
				switch (repairJob) {
				case UPGRADE:
					upgrade();
					break;
				case FUEL:
					fuelAndOrRepair(JobForBots.FUEL);
					break;
				case REPAIR:
					fuelAndOrRepair(JobForBots.REPAIR);
					break;
				default:
					break;
				}
				return true;
			}
			return false;
		}
	}
	
	private enum JobForBots {
		UPGRADE,
		REPAIR,
		FUEL,
		NOTHING;
	}
}
