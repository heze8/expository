import javax.swing.*;
import java.awt.event.*;

public class Main {
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable () {
      public void run () {
        MainFrame frame = null;
		try {
			frame = new MainFrame("Expository... Will you write me?");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (frame != null) {
			frame.playGame();	
		}
      }
    });
  }
}