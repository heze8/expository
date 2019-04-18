package GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

import ExpositoryConstant.ExpositoryConstant;

public class Story extends JPanel implements ExpositoryConstant {
	Font font = new Font(STORY_FONT, Font.PLAIN, 20);
	protected ArrayList<JLabel> messages = new ArrayList<JLabel>();
	private boolean fadeIn = true;
	
	public Story () {
		this.setFont(font);
		this.setBackground(BG_COLOR);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	@Override
	public void setPreferredSize(Dimension preferredSize) {
	    super.setPreferredSize(new Dimension(STORY_WIDTH_PREFF, STORY_HEIGHT_PREFF));
	}
	
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(STORY_WIDTH_PREFF, STORY_HEIGHT_PREFF);
	}
	
	public void setFade (boolean b) {
		fadeIn = b;
	}
	
	public void displayText (String text) {
		this.removeAll();
		JLabel toDisplay = createLabel(text);
        messages.add(toDisplay);
        if (fadeIn) {
        	fadeInText();        	
        }
        else {
        	displayMsg();
        }
	}
	

	private JLabel createLabel(String text) {
		JLabel toDisplay = new JLabel();
		toDisplay.setText("<html>" + text + "</html>");
		toDisplay.setFont(font);
		toDisplay.setForeground(new Color( 0, 0, 0));
		Border margin = new EmptyBorder(STORY_MARGIN, STORY_MARGIN, STORY_MARGIN, STORY_MARGIN);
        toDisplay.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(), margin));
        return toDisplay;
	}
	
	private void fadeInText() {
		if (messages.size() > MAX_MSG) {
			messages.remove(0);
		}
		
		Thread newTextFadeIn = new Thread (new Runnable () {
			public void run() {
				JLabel newestMsg = messages.get(messages.size() - 1);
				add(newestMsg);
		        Timer timer = new Timer (TEXT_FADE_UPDATE, null);
				timer.addActionListener(new ActionListener () {
					int visibilty = INVINSIBLE;
					@Override
					public void actionPerformed(ActionEvent arg0) {
						visibilty += TEXT_FADE_DELAY;
						if (visibilty < VISIBLE) {
							newestMsg.setForeground(new Color(visibilty, visibilty, visibilty));
							repaint();
						} else {
							timer.stop();
						}
					}
				});
		        timer.start();
			}
		});
		newTextFadeIn.start();
		Thread reColoringOldText = new Thread (new Runnable () {
			public void run() {
				int visibility = VISIBLE;
				for (int i = messages.size() - 2; i >= 0; i --) {
					visibility -= COLOR_FADE_INCREMENT;
					messages.get(i).setForeground(new Color (visibility, visibility, visibility));
					add(messages.get(i));
				}
				repaint();
			}
		});
		reColoringOldText.start();
	}
	
	private void displayMsg() {
		if (messages.size() > MAX_MSG) {
			messages.remove(0);
		}
		JLabel newestMsg = messages.get(messages.size() - 1);
		newestMsg.setForeground(NORMAL_COLOR);
		for (int i = 0; i <=  messages.size() - 2; i ++) {
			messages.get(i).setForeground(NORMAL_COLOR);
			add(messages.get(i));
		}
		add(newestMsg);
		repaint();
		revalidate();
	}
}
