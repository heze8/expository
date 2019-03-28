														import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant{
	private Art visual;
	private Story story = new Story();
	private JScrollPane storyPane;
	private PlayerHUD hud;
	
	
	public MainFrame(String title) throws IllegalAccessException {
		super(title);	
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);  
        loadGUI();
	}
	
	private void loadGUI() throws IllegalAccessException {
		//Set Layout Manager
		setLayout(new GridBagLayout());
		 
		//Create swing components
		visual = new Art();
		storyPane = new JScrollPane(story);
		hud = new PlayerHUD();
		
		//Add swing components to pane
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		
		gc.gridx = 0; 
		gc.gridy = 0;
		add(visual, gc);
		
		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridx = 1; 
		gc.gridy = 1;
		add(hud, gc);
		
		gc.weightx = 1;
		gc.weighty = 2;
		gc.gridx = 0; 
		gc.gridy = 1;
		add(storyPane, gc);
	}
	
	public void playGame() {
		story.append("You are sitting in a room... On a stool" + "\n");
		hud.createButtons("Actions", false, new HashMap<String, Integer>() {{
		    put("Explore", 5);
		}});
		hud.addButtonControl("Actions", "Stay Still", 5);
		
		hud.addHUDEventListener(new HUDEventListener() {
			public void HUDEventOccurred(HUDEvent e) {
				   String cmd = e.getBtnName();
				   
				   if (cmd.equals("Explore")) {
					   story.displayText("You looked around the room and noticed that it was not wide. Five feet across in each direction at most."
					   		+ " Smooth grey granite covered everything save for a blacked-out slab of glass directly in front of you."
					   		+ "\n");
				   } else if (cmd.equals("Stay Still")) {
					   story.displayText("You carried on sitting on the stool you sat on, no recollection of the past..."
					   		+ "\n");
				   }
			}
		});
		
	}
}
