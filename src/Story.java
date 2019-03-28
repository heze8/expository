import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Story extends JTextArea implements ExpositoryConstant {
	public Story () {
		setEditable(false);
		setLineWrap(true);
		Font font = new Font("Helvatica", Font.PLAIN, 20);
		setFont(font);
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
	}
	
	public void displayText (String text) {
		Timer timer = new Timer(TEXT_DISP_DELAY, null);
		timer.addActionListener(new ActionListener() {
			int currPos = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
            	append(String.valueOf(text.charAt(currPos++)));
            	if (currPos >= text.length()) {
                    timer.stop();
                }
            }
		});
		timer.start();
	}
	

	
}
