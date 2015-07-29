import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class TermHistory {
	private ArrayList<Term> termList;
	private ArrayList<String> stopWords;
	
	public TermHistory() throws IOException{
		termList = new ArrayList<Term>();
		stopWords = new ArrayList<String>();
		getStopWords();
		readData();
	}
	/**
	 * Populates the list of stop words
	 */
	private void getStopWords(){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("stopwords.txt").getFile());
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				stopWords.add(line);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void readData() throws IOException{
		for(int i=1; i<3205;i++){
			String fileNumber = "";
			if(i<10){
				fileNumber = "000"+i;
			}else if(i<100){
				fileNumber = "00"+i;
			}else if(i<1000){
				fileNumber = "0"+i;
			}else{
				fileNumber = ""+i;
			}
			System.out.println("Page Content For: CACM-"+ fileNumber +".html");
			getFileContents("CACM-"+ fileNumber +".html");
		}
	}
	private String getFileContents(String file) throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		File f = new File(classLoader.getResource("cacm/"+file).getFile());
		StringBuilder contentBuilder = new StringBuilder();
		try (Scanner scanner = new Scanner(f)) {
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				str = filterTerms(str);
		        contentBuilder.append(str);
		        checkForUniqueTerms(str);
			}
			scanner.close();
		}
		String content = contentBuilder.toString();
		return content;
	}
	private String filterTerms(String s){
		s= s.replaceAll("\\<[^>]*>", " ");
		s= s.replaceAll("[^a-zA-Z ]", " ").toLowerCase();
		return s;
	}
	
	/**
	 * Checks term list to see if a term has been inserted.
	 */
	private boolean checkList(String term){
		if(term.equals("")){
			return false;
		}else if(isStopWord(term)){
			return false;
		}else{
			for(Term t : termList){
				if(t.getText().equals(term)){
					t.increaseCount();
					return true;
				}
			}
			//Add new word
			termList.add(new Term(term));
			return false;
		}
	}
	/**
	 * Checks to see if term is a stop word
	 */
	private boolean isStopWord(String term){
		for(Term t : termList){
			if(term.equals(t.getText())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks line for key terms and adds them to term list.
	 */
	public void checkForUniqueTerms(String line){
		for(String word : line.split(" ")){
			checkList(word);
		}
		
	}
	
	/**
	 * Returns an array list of the most frequent terms in term list.
	 */
	public ArrayList<Term> getMostFrequent(){
		Collections.sort(termList);
		System.out.println("Most Frequent Terms: \n");
		for(Term t : termList){
			System.out.println(t.getText() + ": " + t.getOccurrenceCount());
		}
		ArrayList<Term> mostFrequent = new ArrayList<Term>();
		if(termList.size()>10){
			for(int i=termList.size()-1; i>termList.size()-11;i--){
				mostFrequent.add(termList.get(i));
			}
		}
		return mostFrequent;
	}
	
	/**
	 * Returns an array list of the occurrence probability of given terms.
	 */
	public ArrayList<Term> getOccurrenceProbability(ArrayList<Term> givenTerms){
		ArrayList<Term> probabilities = new ArrayList<Term>();
		double totalTerms = 0;
		for(Term term : termList){
			totalTerms += term.getOccurrenceCount();
		}
		
		for(Term t : givenTerms){
			t.setProbability(t.getOccurrenceCount()/totalTerms);
			System.out.println(totalTerms);
			System.out.println(t.getOccurrenceCount());
			System.out.println(t.getText()+ ": " + (t.getOccurrenceCount()/totalTerms));
			probabilities.add(t);
		}
		return probabilities;
	}
}