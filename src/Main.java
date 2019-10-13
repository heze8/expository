import javax.swing.*;
import java.awt.event.*;

public class Main {
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable () {
      public void run () {
        MainFrame frame = null;
		frame = new MainFrame("Simulcra");
		if (frame != null) {
			frame.playGame();	
		}
      }
    });
  }
}