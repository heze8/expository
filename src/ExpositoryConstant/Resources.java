package ExpositoryConstant;
import java.awt.CardLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import Adventure.AdventureMapBoard;
import Adventure.FloorMaps;
import Adventure.Player;
import ExpositoryConstant.ExpositoryConstant.*;
import GUI.InventoryPanel;
import GUI.LatopText;
import GUI.Nanobot;
import GUI.PlayerHUD;
import GUI.Story;

public class Resources {
	/* Variables that needs to be use across classes */
	public static FloorMaps currentFloor = new FloorMaps();
	public static Player player = new Player (13, 10);
	public static Location userLocation = Location.ROOM;
	public static ExploreBtnEvent currExploreEvent = ExploreBtnEvent.FIND_DOOR;
	public static HashMap<String, Nanobot> NanoBotDetails = new HashMap<String, Nanobot>();
	
	public static CardLayout overallContainerCL = new CardLayout();
	public static CardLayout controllerCL = new CardLayout();
	public static CardLayout dustCL = new CardLayout();
	public static JPanel mainContainer;
	public static JPanel centerContainer;
	public static JPanel buttonContainer;
	public static JPanel dustContainer;
	public static JPanel mainGUI;
	
	public static Story story;

	public static PlayerHUD location;
	public static PlayerHUD yourRoom;
	public static PlayerHUD spaceShip;
	public static PlayerHUD dust;
	public static AdventureMapBoard mapBoard;
	
	public static InventoryPanel inven;
	public static LatopText laptop = new LatopText();
}
