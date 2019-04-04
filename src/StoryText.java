import java.awt.*;
import java.util.*;

import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant; 

public class StoryText implements ExpositoryConstant {
	private int exploreState = -1;
	
	public String explore() {
		ArrayList<String> exploreStory = new ArrayList<String>() {{
			add("The floor here you're on is cool and solid. Titanium.");
			add("Your body refuses to listen to your commands to get up."
					+ " It's as if you've been in a slumber for too long."
					+ " Your shirt is stiff and hard."
					+ " An outline of white tracing a large napkin-like shape noticeable on the black fabric."
					+ " In you right hand, a paper. \"Amelia2604\" in black ink.");
			add("As you turn your attention outwards, the four walls greeted you with a featureless dull gray stare. "
					+ " Directly opposite, in the left corner, you noticed a rather conspicuous computing unit sitting on the floor");
			add("The screen of the computers flickers ever so slightly, almost as if sending some hidden message, inviting you in...");
			add("~~~~~~~Using the only glowing object, you noticed a rectangular seam in wall to your right");
			add("");
			add("");
			add("A bionic camel, trudging across the horiontal plane."
					+ "Each step brings a jerk in its rear left leg"
					+ "");
		}};
		if (exploreState != 6) {
			return exploreStory.get(++exploreState);	
		}
		return exploreStory.get(exploreState);
	}
	
	public int getExploreState() {
		return exploreState;
	}
	
	public String laptopReply(String command, LatopText laptop, CardLayout overallContainerCL, JPanel mainContainer) {
		String toReturn = "Invalid Command\nHj72669.@234:~$> ";
		if (command.equals(":Login")) {
			if(laptop.getLoginStatus()) {
				toReturn = "Already Logged In.\n"
						+ USERNAME;
			} else {
				toReturn = "User Password > ";
				laptop.setLoggingIn(true); 
			}
		} else if (command.equals(":exit")) {
			overallContainerCL.show(mainContainer, MAIN_GUI);
			if (laptop.getLoginStatus()) {
				toReturn = USERNAME;
			} else {
				toReturn = "> ";
			}
		} else if (!laptop.loggingIn()) {
			toReturn = "Invalid Command\n> ";
		} else if (!laptop.getLoginStatus()) {
			if (laptop.hash(command) == PASSWORD) {
				laptop.setLoginStatus(true);
				toReturn = "Login Success\n"
						+ "\n"
						+ "Desktop Apps:\n"
						+ "> :Simulcra.exe\n"
						+ "> :exit\n"
						+ USERNAME;
			} else {
				toReturn = "Incorrect Password\n"
						+ "User Password > ";
			}
		} else if (command.equals("")) {
			toReturn = USERNAME; 
		}else if (command.equals(":Simulcra.exe")) {
			toReturn = "\nWelcome Back"
					+ "\nUser ID: Hj72669.@234"
					+ "\nCurrent state: In simulation"
					+ "\n"
					+ "\n> :switch sim"
					+ "\n> :exit sim"
					+ "\n> :LeaveApp"
					+ "\n\n" + USERNAME;	
		} else if (command.equals(":switch sim")) {
			toReturn = "\n> :/real\n"
					+ "\n" + USERNAME;
		} else if (command.equals(":exit sim")) {
			toReturn = "Authorized access required.\n" 
					+ "Enter authentication code: ";
		} else if (command.equals("> :LeaveApp")) {
			toReturn = "Desktop Apps:\n"
					+ "> :Simulcra.exe\n"
					+ "> :exit\n"
					+ "\n" + USERNAME;
		} else if (command.equals(":/real")) {
			toReturn = "You are about to simulate the present reality. "
					+ "Please be aware that this is a hard simulation, all effects are real and have repercussions. "
					+ "No protocols have been set in place.\n" + 
					"Continue... Y/N."
					+ "\n" + USERNAME;
		} else if (command.equals("Y") || command.equals("y") || command.equals("Yes") || command.equals("yes")) {
			toReturn = "Reality Simulated..."
					+ "\n" + USERNAME;
		} else if (command.equals("N") || command.equals("n") || command.equals("No") || command.equals("no")) {
			toReturn = "Perhaps an ultimately wise choice..."
					+ "\n" + USERNAME;
		} else if (laptop.hash(command) == AUTHORISATION_CODE) {
			toReturn = "Simulation exited..."
					+ "\n" + USERNAME;
		}
		return toReturn;
	}
}