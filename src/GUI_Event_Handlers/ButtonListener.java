package GUI_Event_Handlers;
import java.util.EventListener;
import java.util.HashMap;

public interface ButtonListener extends EventListener {
	public void buttonPressed (ButtonEvent be);

	public boolean buttonClickable(HashMap<String, Integer> costMap);
}
