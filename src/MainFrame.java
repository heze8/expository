														import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainFrame extends JFrame implements ExpositoryConstant{
	private Art visual;
	private JTextArea story = new JTextArea();
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
		story.setEditable(false);
		story.setLineWrap(true);
		Font font = new Font("Helvatica", Font.PLAIN, 20);
		story.setFont(font);
		story.setBackground(Color.BLACK);
		story.setForeground(Color.WHITE);
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
		hud.addHUDEventListener(new HUDEventListener() {
			public void HUDEventOccurred(HUDEvent e) {
				   story.append(e.toString() + "\n");
			}
		});
		
		story.append("You are sitting in a room... On a stool" + "\n");
		hud.createButtons("Actions", false, new HashMap<String, Integer>() {{
		    put("Explore", 1);
		}});
		hud.addButtonControl("Actions", "Stay Still", 4);
	}
}
