
import javax.swing.*;


public class MarketGUI {
	
	public static void main(String[] args) {
		
		/* Creating instance of stockViewer class which extends JFrame */
		StockViewer Frame = new StockViewer();
		
		/* Setting Frame exit operation */
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/* Making the frame visible */
		Frame.setVisible(true);
		Frame.setResizable(false);
	}
	
}
