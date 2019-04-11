package GameEvents;
import java.awt.*;
import java.util.*;

import javax.swing.*;

import ExpositoryConstant.ExpositoryConstant;
import GUI.LatopText; 

public class StoryText implements ExpositoryConstant {
	private int numTimesExplore = -1;
	
	public String explore() {
		ArrayList<String> exploreStory = new ArrayList<String>() {{
			add("The floor here you're on is cool and solid. Titanium.");
			add("A tank of water lay in one corner"
					+ "Four walls greet you with a featureless dull gray stare.");
			add( "Directly opposite, in the left corner, a computing unit sits on the floor...");
			add( "A piece of paper lay crumpled next it the computing unit. Amelia2604, it says.");
			add( "A seam can be seen on the right wall");
			add( "There seems to be nothing else worth exploring here...");
		}};
		if (numTimesExplore != 5) {
			return exploreStory.get(++numTimesExplore);	
		}
		return exploreStory.get(numTimesExplore);
	}
	
//	+ "Your body refuses to listen to your commands to get up."
//	+ " It's as if you've been in a slumber for too long."
//	+ " Your shirt is stiff and hard."
//	+ " An outline of white tracing a large napkin-like shape noticeable on the black fabric."
//	+ " In you right hand, a paper. \"Amelia2604\" in black ink.");
//	"A bionic camel, trudging across the horiontal plane."
//	+ "Each step brings a jerk in its rear left leg"
//	+ ""
	
	public int getnumTimesExplore () {
		return numTimesExplore;
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