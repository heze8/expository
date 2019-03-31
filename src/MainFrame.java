														import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant{
	private Story story;
	private PlayerHUD hud;
	private InventoryPanel inven;
	
	public MainFrame(String title) {
		super(title);	
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        loadGUI();
	}
	
	private void loadGUI() {
		//Set Layout Manager
		setLayout(new BorderLayout());
		 
		//Create swing components
		hud = new PlayerHUD();
		story = new Story();
		inven = new InventoryPanel();
		
		//Add swing components to pane)
		add(story, BorderLayout.LINE_START);
		add(hud, BorderLayout.CENTER);
		add(inven, BorderLayout.LINE_END);
		
	}
	
	public void playGame() {
		story.displayText("The World Comes into Vision");
		hud.createButtons("Actions", false, new HashMap<String, Integer>() {{
		    put("Explore", 1);
		}});
		hud.addButtonControl("Actions", "Stay Still", 1);
		
		hud.addHUDEventListener(new HUDEventListener() {
			public void HUDEventOccurred(HUDEvent e) {
				   String cmd = e.getBtnName();
				   
				   if (cmd.equals("Explore")) {
					   story.displayText("You looked around the room and noticed that it was not wide. Five feet across in each direction at most."
					   		+ " Smooth grey granite covered everything save for a blacked-out slab of glass directly in front of you.");
				   } else if (cmd.equals("Stay Still")) {
					   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
					   		+ "\n");
				   } else if (cmd.equals("Browse Latop")) {
					   Laptop laptop = new Latop();
					   latop.run();
				   }
			}
		});
		
	}
}
