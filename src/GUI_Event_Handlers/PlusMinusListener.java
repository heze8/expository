package GUI_Event_Handlers;

import java.util.EventListener;

public interface PlusMinusListener extends EventListener {
	public boolean plusMinusClicked (String command, String param);
}
