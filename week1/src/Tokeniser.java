

import java.io.IOException;

import org.jfree.ui.RefineryUtilities;


public class Tokeniser {
	static TermHistory history;
	
	public static void main(String[] args) {
		try {
			history = new TermHistory();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BarChart chart = new BarChart("Frequency Counter", "Most frequently occurring words.", history.getOccurrenceProbability(history.getMostFrequent()));
		chart.pack( );  
		RefineryUtilities.centerFrameOnScreen(chart);        
		chart.setVisible( true ); 
	}

}
