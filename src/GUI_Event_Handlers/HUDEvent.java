package GUI_Event_Handlers;
import java.util.EventObject;

public class HUDEvent extends EventObject {
	private String btnName = "";
	
	public HUDEvent (Object source, String btnName) {
		super (source);
		this.btnName = btnName;
	}
	
	public String getBtnName () {
		return btnName;
	}
}
 