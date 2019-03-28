import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class ButtonClickedListener implements MouseListener {
    	
	private int cooldown;
		
	public ButtonClickedListener (int cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();		
		if (pressed.getForeground() != Color.RED) {
			pressed.setForeground(Color.LIGHT_GRAY);
			pressed.setBackground(Color.LIGHT_GRAY);	
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();
		if (pressed.getForeground() != Color.RED) {
			pressed.setForeground(Color.BLACK);
			pressed.setBackground(Color.BLACK);	
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JLabel pressed = (JLabel)arg0.getSource();
		if (pressed.getForeground() != Color.RED) {
			// Changes button color to show that button is not clickable
			pressed.setForeground(Color.RED);
			pressed.setBackground(Color.RED);
			
			// Changes the button color back to original after delay ms has passed
			// Allows the button to be clicked again
    		Timer timer = new Timer(cooldown, new ActionListener(){
    		  @Override
    		  public void actionPerformed( ActionEvent e ){
    		    pressed.setForeground( Color.BLACK);
    		    pressed.setBackground(Color.BLACK);
    		  }
    		} );
    		timer.setRepeats( false );
    		timer.start();
    		
    		fireHUDEvent(new HUDEvent(pressed));
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

}
