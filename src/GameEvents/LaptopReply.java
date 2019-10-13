package GameEvents;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;

public class LaptopReply implements ExpositoryConstant {

	
	public static String replyTo(String command) {
		
		// Checks to see if the user is trying to log in
		if (!Resources.laptop.isLoggedIn() && Resources.laptop.isLoggingIn()) {
			if (Resources.laptop.hash(command) == PASSWORD) {
				Resources.laptop.setLoginStatus(true);
				Resources.laptop.setLoggingIn(false);
				return  "Login Success\n"
					+ "\n"
					+ "Desktop Apps:\n"
					+ "> :Simulcra.exe\n"
					+ "> :exit\n"
					+ USERNAME;
			} else if(command.equals(":exit")) {
				Resources.laptop.setLoggingIn(false);
				Resources.overallContainerCL.show(Resources.mainContainer, MAIN_GUI);
			} else {
				return "Incorrect Password\n"
					+ "User Password > ";
			}
		}
		
		// checks to see if the user is trying to exit the sim
		if (Resources.laptop.isLoggedIn() && Resources.laptop.isExiting()) {
			if (Resources.laptop.hash(command) == AUTHORISATION_CODE) {
				return "Simulation exited..."
					+ "\n" + USERNAME;
			}
			else {
				Resources.laptop.setExiting(false);
				if (Resources.laptop.isSimulatingReality()) {
					return "\n Wrong Code"
						+ "\nUser ID: Hj72669.@234"
						+ "\nCurrent state: Simulating Reality"
						+ "\n"
						+ "\n> :switch sim"
						+ "\n> :exit sim"
						+ "\n> :LeaveApp"
						+ "\n\n" + USERNAME;	
				}
				else {
					return 	"\n Wrong Code"
						+ "\nUser ID: Hj72669.@234"
						+ "\nCurrent state: In simulation"
						+ "\n"
						+ "\n> :switch sim"
						+ "\n> :exit sim"
						+ "\n> :LeaveApp"
						+ "\n\n" + USERNAME;	
				}
			
			}
		}
		
		if(Resources.laptop.isInSpace()) {
			switch(command) {
			case ":logoff":
				Timer kickOutSpaceStation = new Timer (2 * SEC_TO_MSEC, null);
				kickOutSpaceStation.addActionListener(new ActionListener () {
					@Override
					public void actionPerformed(ActionEvent e) {
						Resources.overallContainerCL.show(Resources.mainContainer, MAIN_GUI);
					}
				});
				kickOutSpaceStation.setRepeats(false);
				kickOutSpaceStation.start();
				return "Good-Bye";	
			case":Browse Database":
				return "\nAll Systems Running"
				+ "\nDays till next scheduled maintenance: 11 days"
				+ "\n"
				+ "\nFor spcific database checkup, please enter user ID"
				+ "\n"
				+ "\n> ";
			case"Hj72669.234":
				return "\nUser State: In Simulation. Simulating Reality."
						+ "\nDays in Simulation: 63"
						+ "\nBody Disposal state: Pending"
						+ "\nAuthorisation Code for User: H9h329fn"
						+ "\n"
						+ "\n> ";
			}
		}
		
		else {
			switch (command) {
			case ":login":
				// Already Logged in
				if(Resources.laptop.isLoggedIn()) 
					return "Already Logged In.\n"
						+ "\n"
						+ "Desktop Apps:\n"
						+ "> :Simulcra.exe\n"
						+ "> :exit\n"
						+ USERNAME;
				
				// Not Logged In
				else {
					Resources.laptop.setLoggingIn(true); 
					return "User Password > ";
				}
			case ":exit":
				Resources.overallContainerCL.show(Resources.mainContainer, MAIN_GUI);
				Resources.userLocation = Location.ROOM;
				
				if (Resources.laptop.isLoggedIn()) {
					return USERNAME;
				}
				else {
					return "> ";
				}
			case "":
				return USERNAME;
			case ":Simulcra.exe":
				if (Resources.laptop.isSimulatingReality()) {
					return "\nWelcome Back"
						+ "\nUser ID: Hj72669.234"
						+ "\nCurrent state: Simulating Reality"
						+ "\n"
						+ "\n> :switch sim"
						+ "\n> :exit sim"
						+ "\n> :LeaveApp"
						+ "\n\n" + USERNAME;
				}
				else {
					return "\nWelcome Back"
						+ "\nUser ID: Hj72669.234"
						+ "\nCurrent state: In simulation"
						+ "\n"
						+ "\n> :switch sim"
						+ "\n> :exit sim"
						+ "\n> :LeaveApp"
						+ "\n\n" + USERNAME;	
				}
			case ":switch sim":
				if (Resources.laptop.isSimulatingReality()) {
					return "No available options"
							+ "\n" +USERNAME;
				}
				else {
					return "\n> :/real\n"
						+ "\n" 
						+ USERNAME;
				}
			case ":exit sim":
				Resources.laptop.setExiting(true);
				return "Authorized access required.\n" 
					+ "Enter authentication code: ";
			
			case ":LeaveApp":
				return "Desktop Apps:\n"
					+ "> :Simulcra.exe\n"
					+ "> :exit\n"
					+ "\n" + USERNAME;
			
			case ":/real":
				return "You are about to simulate the present reality. "
					+ "Please be aware that this is a hard simulation, all effects are real and have repercussions. "
					+ "No protocols have been set in place.\n" + 
					"Continue... Y/N."
					+ "\n" + USERNAME;
				
			case "y":
			case "Y":
			case "Yes":
			case "yes":
				Resources.story.displayText("one of the walls now spot a rectangular protrusion");
				Resources.currExploreEvent = ExploreBtnEvent.FIND_DOOR;
				Resources.laptop.setSimulatingReality(true);
				return "Reality Simulated..."
					+ "\n" + USERNAME;
			case "N":
			case "n":
			case "No":
			case "no":
				return "Perhaps an ultimately wise choice..."
					+ "\n" + USERNAME;
			default: 
				break;
			}
		}
		
		if (Resources.laptop.isLoggedIn() && !Resources.laptop.isInSpace()) {
			return "Invalid Command\n" + USERNAME;
		}
		else {
			return "Invalid Command\n> ";
		}
	}
}
