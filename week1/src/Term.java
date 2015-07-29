
public class Term implements Comparable<Term>{
	private String text;
	private int count;
	private double probability;
	public Term(String text){
		this.text = text;
		count++;
	}
	public int compareTo(Term p){
		return (count - p.getOccurrenceCount());
	}
	public String getText(){
		return text;
	}
	public void increaseCount(){
		count++;
	}
	public int getOccurrenceCount(){
		return count;
	}
	public void setProbability(double value){
		probability = value;
	}
	public double getProbability(){
		return probability;
	}
}
