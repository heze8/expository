package GameEvents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import ExpositoryConstant.ExpositoryConstant;
import ExpositoryConstant.Resources;

public class RandomEvent implements ExpositoryConstant {
	
	/** 
	 * Creates a new thread which contains a timer firing once every RANDOM_OCCURRENCE_DELAY (as of writing, 10sec)
	 * Timer will then activate a random event depending on the Users Location
	 */
	public static void beginRandomEvents() {
		
		Thread randomEvent = new Thread (new Runnable() {
			
			@Override
			public void run() {
				Timer randomEventOccurred = new Timer (RANDOM_OCCURRENCE_DELAY, null);
				randomEventOccurred.addActionListener(new ActionListener () {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						switch(Resources.userLocation) {
						case ROOM:
							RandomRoomEvents.getEvent();
							break;
						case DUST:
							break;
						case LAPTOP:
							break;
						case SPACESHIP:
							break;
						default:
							break;
						}
					}
				});
				randomEventOccurred.start();
			}
		});
		randomEvent.start();
	}
	
}
