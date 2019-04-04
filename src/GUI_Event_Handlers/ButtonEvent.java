package GUI_Event_Handlers;
import java.util.EventObject;

public class ButtonEvent extends EventObject {
	private String btnName = "";
	private String btnTitle = "";
	
	public ButtonEvent (Object e, String name, String title) {
		super(e);
		btnName = name;
		btnTitle = title;
	}
	
	public String getBtnName() {
		return btnName;
	}
	
	public String getTitle () {
		return btnTitle;
	}
}
