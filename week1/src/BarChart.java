import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;


public class BarChart extends ApplicationFrame{
	private static final long serialVersionUID = 1L;
	private ArrayList<Term> mostFrequent  = new ArrayList<Term>();

	public BarChart( String applicationTitle , String chartTitle, ArrayList<Term> mostFrequent){
	      super( applicationTitle );
	      this.mostFrequent  = new ArrayList<Term>(mostFrequent);
	      JFreeChart barChart = ChartFactory.createBarChart(
	         chartTitle,         
	         "Term",            
	         "Probability of Occurrence",            
	         createDataset(),          
	         PlotOrientation.VERTICAL,           
	         true, true, false);
	         
	      ChartPanel chartPanel = new ChartPanel( barChart );        
	      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	      setContentPane( chartPanel ); 
	   }
	/**
	 * Constructs data set to be used in BarChart
	 * @return
	 */
	  private CategoryDataset createDataset( ){      
	      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      System.out.println("Creating Dataset... ");
	      for(Term p : mostFrequent){
	    	  dataset.addValue( p.getProbability() , p.getText() , "Term" ); 
	    	  System.out.println(p.getText() + ": " + p.getProbability());
	      }              

	      return dataset; 
	  }

}
