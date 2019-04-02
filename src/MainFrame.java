														import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI.InventoryPanel;
import GUI.PlayerHUD;
import GUI.Story;
import GUI_Event_Handlers.HUDEvent;
import GUI_Event_Handlers.HUDEventListener;

import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant{
	private CardLayout cl = new CardLayout();
	private JPanel container;
	private JPanel mainGUI;
	private Story story;
	private PlayerHUD hud;
	private InventoryPanel inven;
	private Laptop laptop = new Laptop();
	
	public MainFrame(String title) {
		this.setTitle(title);	
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        loadGUI();
	}
	
	private void loadGUI() {
		
		//Creating the main GUI where most of the game will happen
		mainGUI = new JPanel(new BorderLayout());
		//Create swing components for mainGUI
		hud = new PlayerHUD();
		story = new Story();
		inven = new InventoryPanel();
		//Add swing components to mainGUI JPanel
		mainGUI.add(story, BorderLayout.LINE_START);
		mainGUI.add(hud, BorderLayout.CENTER);
		mainGUI.add(inven, BorderLayout.LINE_END);
		
		//Creating the container to hold the various other screens. Initialized to CardLayout
		container = new JPanel(cl);
		container.add(mainGUI, MAIN_GUI);
		container.add(laptop, LAPTOP);
		
		this.add(container);
	}
	
	public void playGame() {
		story.displayText("The World Comes into Vision");
		hud.createButtons("Actions", false, new HashMap<String, Integer>() {{
		    put("Explore", 1);
		}});
		hud.addButtonControl("Actions", "Stay Still", 1);
		hud.addButtonControl("Actions", "Browse Laptop", 5);
		
		
		hud.addHUDEventListener(new HUDEventListener() {
			public void HUDEventOccurred(HUDEvent e) {
				   String cmd = e.getBtnName();
				   
				   if (cmd.equals("Explore")) {
					   story.displayText("You looked around the room and noticed that it was not wide. Five feet across in each direction at most."
					   		+ " Smooth grey granite covered everything save for a blacked-out slab of glass directly in front of you.");
				   } else if (cmd.equals("Stay Still")) {
					   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
					   		+ "\n");
				   } else if (cmd.equals("Browse Laptop")) {
					   cl.show(container, LAPTOP);
					   laptop.addConsoleListener(new ConsoleListener() {
							@Override
							public String receiveCommand(String command) {
								if (command.equals("ls")) {
									String toReturn = "- Simulcra.exe <br>- Hacks and cheats";
									System.out.println(toReturn);
									return toReturn;
								}
								return "";
							}
						});
					   laptop.runLaptop();
					   laptop.print("Welcome");
				   }
			}
		});
		
		
		
	}
}
