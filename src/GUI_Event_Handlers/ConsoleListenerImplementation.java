package GUI_Event_Handlers;

import GameEvents.LaptopReply;

public class ConsoleListenerImplementation implements ConsoleListener {
	
	@Override
	public String receiveCommand(String command) {
		return LaptopReply.replyTo(command);
	}
}
