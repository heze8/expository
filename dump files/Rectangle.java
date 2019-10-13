package GUI;

import java.awt.Graphics;

import javax.swing.JComponent;

import ExpositoryConstant.ExpositoryConstant;

public class Rectangle extends JComponent implements ExpositoryConstant {
	private int x;
	private int y;
	private int btnWidth;
	private int height;
	
	public Rectangle (int x, int y , int width, int height) {
		this.x = x;
		this.y = y;
		btnWidth = width;
		this.height = height;
	}
	
	public void setSize(int width, int height) {
		btnWidth = width;
		this.height = height;
		repaint();
	}
	
	 @Override
	    public void paintComponent(Graphics g)
	    {
	        super.paintComponent(g);
	        g.setColor(NORMAL_COLOR);
	        g.drawRect(x, y, btnWidth, height);
	    }
}
