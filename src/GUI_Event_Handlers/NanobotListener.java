package GUI_Event_Handlers;
import java.util.EventListener;
import java.util.HashMap;

public interface NanobotListener extends EventListener {
	public void nanobotEventOccurred (HashMap<String, Integer> valuesForUpdating);
	public boolean nanobotRepairOccurred (HashMap<String,Integer> costMap);
}
