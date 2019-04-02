
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

public class StockViewer extends JFrame{
	
	JLabel tickerSymbol, startDate, endDate, background;
	JComboBox<String> tickerSymbolList;
	JSpinner dateStartSpin, dateStopSpin;
	JButton display;
	
	/* Class Constructor to create frame and add components on it */
	public StockViewer(){
		Font font = new Font("Helvetica", Font.BOLD, 14);
	
		/* Defining the size of the frame */ 
		this.setSize(500, 200);
		
		/* Set Image as Frame Background */
		ImageIcon img = new ImageIcon("stock.jpg");
		background = new JLabel("", img, JLabel.CENTER);
		background.setLayout(new GridBagLayout());
		background.setBounds(0, 0, 500, 200);
		this.add(background);
		
	
		/* Toolkit is the super class for the Abstract Window Toolkit */
		Toolkit tk = Toolkit.getDefaultToolkit();
					
		Dimension dim = tk.getScreenSize();
		
		/* dim.width returns the width of the screen
		 * this.getWidth returns the width of the frame you are making */
		
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
					 
		/* defining the x, y position of the frame */
		this.setLocation(xPos, yPos);
	
		/* Setting Frame title */
		this.setTitle("Stock Market Data Viewer");
		this.setFont(font);
	
//************Ticker Symbol**************//
		
		JPanel TickerPanel = new JPanel();
	
		/* First Label */
		tickerSymbol = new JLabel("  Ticker Symbol:");
		tickerSymbol.setFont(font);
		tickerSymbol.setForeground(Color.WHITE);
		TickerPanel.add(tickerSymbol);

		/* Creating a combo box that will hold the ticker Symbols */
		String[] tickerList = {"AAPL", "MSFT", "GOOGL", "FB", "AMZN"};
		tickerSymbolList = new JComboBox<String>(tickerList);
		tickerSymbolList.setFont(font);
		TickerPanel.add(tickerSymbolList);
		TickerPanel.setOpaque(false);

		/* Adding components to background */
		addComp(background, TickerPanel, 0, 0, 1, 1, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE);
	
	
//*****************DATE******************//
		JPanel datePanel = new JPanel();
	
		/* START DATE */
		startDate = new JLabel("Start Date");
		startDate.setForeground(Color.WHITE);

		startDate.setFont(font);
		datePanel.add(startDate); 
	
		/* Getting todays date */
		Date todaysDate = new Date();	
			
		/* Creating a date spinner & setting default to today, no minimum or max
		 *  Increment the days on button press
		 *  Can also increment YEAR, MONTH, or DAY_OF_MONTH */
		
		dateStartSpin = new JSpinner(new SpinnerDateModel(todaysDate, null, null,
			   Calendar.DAY_OF_MONTH));
	
		/* DateEditor is an editor that handles displaying & editing the dates */
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateStartSpin, 
				"MM/dd/yyyy");
		dateStartSpin.setEditor(dateEditor);
		dateStartSpin.setFont(font);		
		datePanel.add(dateStartSpin);
		
		/* STOP DATE */ 
		endDate = new JLabel("End Date");
		endDate.setForeground(Color.WHITE);

		endDate.setFont(font);
		datePanel.add(endDate);
	
			
		dateStopSpin = new JSpinner(new SpinnerDateModel(todaysDate, null, null,
				   Calendar.DAY_OF_MONTH));
								
		JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(dateStopSpin, 
				"MM/dd/yyyy");
		dateStopSpin.setEditor(dateEditor2);
		dateStopSpin.setFont(font);		
		datePanel.add(dateStopSpin);
		datePanel.setOpaque(false);

		addComp(background, datePanel, 0, 2, 1, 1, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE);
		
//***********Button***************//
		
		JPanel buttonPanel = new JPanel();
		
		/* Formatting Button */
		display = new JButton("ENTER");
		display.setBorderPainted(true);
		display.setBorder(null);
		display.setOpaque(false);
		
		display.setMargin(new Insets(0, 0, 0, 0));
		display.setContentAreaFilled(true);
		
		/* Setting Image as button */
		Icon butIcon = new ImageIcon("./submit.png");
		display.setIcon(butIcon);
		
		/* Adding Button Listener */
		ListeningForButton listenForButton = new ListeningForButton();
		display.addActionListener(listenForButton);
		
		display.setFont(font);
		buttonPanel.add(display);
		buttonPanel.setOpaque(false);

		addComp(background, buttonPanel, 0, 4, 1, 1, GridBagConstraints.CENTER, 
				GridBagConstraints.NONE);

	}

	/* Method to set Grid Bag Constraints for each component 
	 * and adding them to background
	 */
	private void addComp(JLabel background, JComponent comp, int xPos, int yPos, 
			int compWidth, int compHeight, int place, int stretch){
	
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.gridwidth = compWidth;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = 100;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5,5,5,5);
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;
		
		background.add(comp, gridConstraints);
	}

	/* A class that listens for the action performed on the Button */
	private class ListeningForButton implements ActionListener{
	
		/* This method is called when an event occurs */
	
		public void actionPerformed(ActionEvent e){
		
		/* Check if the source of the event was the button */
		
			if(e.getSource() == display){
				CheckCondition();
			}
		}
		
		/*Method to check various conditions so that the dates 
		 * entered will return appropriate data
		 */
		private void CheckCondition() {
			
			String Start, end, tickSym;
			Date currentDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			String todayDateString = formatter.format(currentDate);
			
			try {
				currentDate = formatter.parse(todayDateString);
			} 
			catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			try{
				tickSym = (String) tickerSymbolList.getSelectedItem();
				System.out.println(tickSym);
			
				Date startValue = (Date)dateStartSpin.getValue();
				Date endValue = (Date)dateStopSpin.getValue();
				
			
				if(startValue.after(endValue)){
					Error();
				} 
				else if(startValue.equals(endValue)){
					Error();
				}
				else if(startValue.after(currentDate)){
					Error();
				}
				else if((endValue.after(currentDate)) && (startValue.after(currentDate))){
					Error();
				}
				
				else if((endValue.after(currentDate)) && (startValue.equals(currentDate))){
					Error();
				}
				
				else{
					Start = new SimpleDateFormat("MM/dd/yyyy").format(startValue);
					System.out.println(Start);
					
					end = new SimpleDateFormat("MM/dd/yyyy").format(endValue);
					System.out.println(end);
					
					/* When we retrieve required inputs we call 
					 * the LinkMaker method to create Link
					 */
					LinkMaker(tickSym, Start, end);
				}
			}
			
			catch(Exception excep){
				System.out.println(excep.getStackTrace());		
			}
		}
		
		/* Method to display error when invalid dates are 
		 * entered which will return empty files
		 */
		private void Error() {
			JOptionPane.showMessageDialog(StockViewer.this, "Please Enter valid Period of time.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/* Method for making the link to download the data */
		public void LinkMaker(String tickSym,String Start,String end){
			String Link;
			Link = "http://quotes.wsj.com/"+tickSym
					+"/historical-prices/download?MOD_VIEW=page&num_rows=300&startDate="
					+Start+"&endDate="+end;
				
			/* After creating the link we call DownloadFile class */
			File out = new File("historical-prices.csv");
			Thread r = new Thread(new DownloadFile(Link, out));
			r.start();
		}
			
}
