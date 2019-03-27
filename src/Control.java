import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;

public class Control extends JPanel implements ExpositoryConstant {
	
	/* Instance Variables */
	Map<String, JLabel> buttonMap = new HashMap<String, JLabel>();
	
	public Control() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public void addBtn(String text) {
		JLabel label1 = createLabel(text);
		add(label1);
		System.out.println(label1);
		buttonMap.put(text,	label1);
		label1.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel label1 = (JLabel)e.getSource();
				label1.setForeground(Color.LIGHT_GRAY);
				int delay = 2000;
				Timer timer = new Timer( delay, new ActionListener(){
				  @Override
				  public void actionPerformed( ActionEvent e ){
				    label1.setForeground( Color.BLACK);
				  }
				} );
				timer.setRepeats( false );
				timer.start();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
		});
	}
	
	private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        int marginLeftRight = (CONTROL_LABEL_LENGTH - lbl.getPreferredSize().width) / 2;
        Border margin = new EmptyBorder(MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight
        		, MARGIN_TOP_BOTTOM_CONTROL
        		, marginLeftRight);
        lbl.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), margin));
        return lbl;
    }
}
