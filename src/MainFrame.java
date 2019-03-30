														import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant{
	private Story story;
	private PlayerHUD hud;
	
	
	public MainFrame(String title) throws IllegalAccessException {
		super(title);	
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);  
        this.setBackground(Color.BLACK);
        this.setForeground(Color.BLACK);
        loadGUI();
	}
	
	private void loadGUI() throws IllegalAccessException {
		//Set Layout Manager
		setLayout(new BorderLayout());
		 
		//Create swing components
		hud = new PlayerHUD();
		story = new Story();
		
		//Add swing components to pane
		add(story, BorderLayout.LINE_START);
		add(hud, BorderLayout.CENTER);
		
	}
	
	public void playGame() {
		
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
					   System.out.println("WOrking");
				   } else if (cmd.equals("Stay Still")) {
					   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
					   		+ "\n");
				   }
			}
		});
		
	}
}
