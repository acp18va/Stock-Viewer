
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;  
import javafx.scene.chart.NumberAxis;  
import javafx.scene.chart.XYChart;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;  
	
public class DisplayGraph extends Application  
{  
	/* Declaring Array lists and Flow pane */
	
	ArrayList<String> Date = new ArrayList<String>();
	ArrayList<String> Open = new ArrayList<String>();
	ArrayList<String> High = new ArrayList<String>();
	ArrayList<String> Low = new ArrayList<String>();
	ArrayList<String> Close = new ArrayList<String>();
	ArrayList<String> Volume = new ArrayList<String>();
	ArrayList<Double> DoubleOpen = new ArrayList<Double>();
	ArrayList<Double> DoubleHigh = new ArrayList<Double>();
	ArrayList<Double> DoubleLow = new ArrayList<Double>();
	ArrayList<Double> DoubleClose = new ArrayList<Double>();
	ArrayList<Double> DoubleVolume = new ArrayList<Double>();
    FlowPane root = new FlowPane();

    @Override  
	public void start(Stage primaryStage) throws Exception {  
    	// TODO Auto-generated method stub  
	    String csvFile = "historical-prices.csv";
	    BufferedReader br = null;
	    String line = "";
	    String cvsSplitBy = ",";

	    try
	    {
	    	br = new BufferedReader(new FileReader(csvFile));
	        
	    	while ((line = br.readLine()) != null)
	    	{
	    		/* use comma as separator to split line and make lists of strings */
	            String[] temp = line.split(cvsSplitBy);
	            Date.add(temp[0]);
	            Open.add(temp[1]);
	            High.add(temp[2]);
	            Low.add(temp[3]);
	            Close.add(temp[4]);
	            Volume.add(temp[5]);
	            }
	    	
	    	/* Calling DataMaker method to prepare data for plotting graphs */
	    	Date.remove(0);
	    	DoubleOpen = DataMaker(Open, DoubleOpen);
	    	DoubleHigh = DataMaker(High, DoubleHigh);
	    	DoubleLow = DataMaker(Low, DoubleLow);
	    	DoubleClose = DataMaker(Close, DoubleClose);
	    	DoubleVolume = DataMaker(Volume, DoubleVolume);
            }
	    
	    catch(FileNotFoundException e)
	    {
	    	e.printStackTrace();
	        }
	    
	    catch(IOException e)
	    {
	        e.printStackTrace();
	        }
	    
	    finally
	    {
	        if (br != null)
	        {
	            try
	            {
	            	br.close();
	                } 
	            catch(IOException e)
	            {
	                e.printStackTrace();
	                }
	            }
	        }
	    
	    /* Calling CreateGraph Method */
	    
	    CreateGraph(Date, DoubleOpen, "Opening Price of Stock", root);
	    CreateGraph(Date, DoubleHigh, "Highest Price of Stock", root);
	    CreateGraph(Date, DoubleLow, "Lowest Price of Stock", root);
	    CreateGraph(Date, DoubleClose, "Closing Price of Stock", root);
	    CreateGraph(Date, DoubleVolume, "Volume of Stock Traded", root);
	    CreateGraph(Date, DoubleHigh, DoubleLow, "Highest Stock Price", 
	    		"Lowest Stock Price", root);

	    /* Adding flow pane to scene and Displaying graphs */
	   
	    Scene scene = new Scene(root,1500,800);  
	    primaryStage.setScene(scene);  
	    primaryStage.setTitle("Stock Market Data Analysis");
		
	    System.out.println("Displaying Graphs!");

	    primaryStage.show();
	          
	    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              Platform.exit();
	              System.exit(0);
	          }
	      });
		 
	   }
    
    /* CreateGraph function to create and add line chart to flow pane */
    
    private FlowPane CreateGraph(ArrayList<String> StrTest, 
    		ArrayList<Double> DoubTest, String ylabel, FlowPane root) {
		
    	/* Defining x axis/y axis */
	    final CategoryAxis xaxis = new CategoryAxis();  
	    final NumberAxis yaxis = new NumberAxis();  
	          
	    /* Defining Label for both axis */  
	    xaxis.setLabel("Date");  
	    yaxis.setLabel(ylabel);  
	          
	    /* Creating the instance of line chart with the specified axis */  
	    LineChart<String, Number> linechart = new LineChart<String, Number>(xaxis,yaxis);  
	    
	    /* Creating the series */
	    XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();  
	          
	    /* Setting name of the series */
	    series.setName("Date vs "+ylabel);
	        
	    /* Adding data to the Series */
	    for(int i = 0; i< DoubTest.size(); i++)
	    {
	    	series.getData().add(new XYChart.Data<String, Number>(StrTest.get(i), 
	    			DoubTest.get(i)));
	        }
	          
	          
	    /* Adding series to the line chart */   
	    linechart.getData().add(series);  
	    
	    /* Adding line chart to flow pane */
	    root.getChildren().add(linechart);
	   
	    /* returning flow pane */
	    return root;
	}
    
	  /* CreateGraph function to create and add line charts 
	   * with multiple series to flow pane 
	   */
    
    private FlowPane CreateGraph(ArrayList<String> StrTest, 
    		ArrayList<Double> DoubTest1, ArrayList<Double> DoubTest2, 
    		String ylabel1, String ylabel2, FlowPane root) {
    	
    	/* Defining x axis/y axis */
	    final CategoryAxis xaxis = new CategoryAxis();  
	    final NumberAxis yaxis = new NumberAxis();  
	          
	    /* Defining Label for both axis */
	    xaxis.setLabel("Date");  
	    yaxis.setLabel(ylabel1+"-"+ylabel2);  
	          
	    /* Creating the instance of line chart with the specified axis */ 
	    LineChart<String, Number> linechart = new LineChart<String, Number>(xaxis,yaxis);  
	          
	    /* Creating both series */   
	    XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>(); 
	    XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();
	          
	    /* Setting name of both the  series */  
	    series1.setName(ylabel1);
	    series2.setName(ylabel2);
	    
	    /* Adding data to the Series */
	    for(int i = 0; i< DoubTest1.size(); i++)
	    {
	    	series1.getData().add(new XYChart.Data<String, 
	    			Number>(StrTest.get(i), DoubTest1.get(i)));
	    	series2.getData().add(new XYChart.Data<String, 
	    			Number>(StrTest.get(i), DoubTest2.get(i)));
	    	
	        }
	        
	    /* Adding both series to the line chart */   
	    linechart.getData().add(series1);  
	    linechart.getData().add(series2);  
	    
	    /* Adding line chart to flow pane */
	    root.getChildren().add(linechart);
	    /* returning flow pane */
	    return root;
	}
    
	    
	/* DataMaker function to create array List to plot */
	private ArrayList<Double> DataMaker(ArrayList<String> StrTest, 
			ArrayList<Double> DoubTest) {
    	StrTest.remove(0);
    	
    	for (int i = 0; i < StrTest.size(); i++)
    	{ 
    		StrTest.get(i).trim();
    		DoubTest.add(Double.parseDouble(StrTest.get(i)));
    		}
    	
    	return DoubTest;
    }
}

	
