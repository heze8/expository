import java.util.EventObject;

public class ButtonEvent extends EventObject {
	private String btnName = "";
	
	public ButtonEvent (Object e, String name) {
		super(e);
		btnName = name;
	}
	
	public String getBtnName() {
		return btnName;
	}
}
