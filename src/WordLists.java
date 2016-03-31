import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

// Author(s):
// Version: 
// Date:	

public class WordLists {
	private Reader in = null;
	private Map<String, Integer> words  = new TreeMap<String, Integer>();

	// constructor
	public WordLists(String inputFileName) throws IOException {
	    // ... define!
		// read input file
		Scanner scanner = new Scanner(new File(inputFileName));
		
		// add words in file to words TreeMap
		while (scanner.hasNext()) {
			String word = scanner.next();
		    Integer count = words.get(word);
		    count = (count == null ? 1 : count + 1);
		    words.put(word, count);
		}
		scanner.close();
	}
	
	/* Prints words TreeMap
	public void printTreeMap() {
		for (String word : words.keySet()) {
			System.out.println(word);
		}
	}
	*/
	/* Writes TreeMap to a file.
	public void writeTreeMap() {
		String s = "";
		for (String word : words.keySet()) {
			s += word + " ";
		}
		try {
			writeToFile("test.txt", s);
		}
		catch (IOException e) {
			System.out.println("caught you!");
		}
		
	}
	*/
	
	private boolean isPunctuationChar(char c) {
	    final String punctChars = ",.:;?!";
	    return punctChars.indexOf(c) != -1;
	}
	
	private String getWord() throws IOException {
		int state = 0;
		int i;
		String word = "";
		while ( true ) {
			i = in.read();
			char c = (char)i;
			switch ( state ) {
			case 0:
				if ( i == -1 )
					return null;
				if ( Character.isLetter( c ) ) {
					word += Character.toLowerCase( c );
					state = 1;
				}
				break;
			case 1:
				if ( i == -1 || isPunctuationChar( c ) || Character.isWhitespace( c ) )
					return word;
				else if ( Character.isLetter( c ) ) 
					word += Character.toLowerCase( c );
				else {
					word = "";
					state = 0;
				}
			}
		}
	}
	
	/**
	 * Writes output to a file with selected fileName.
	 * @param fileName
	 * @param output
	 * @throws IOException
	 */
	private void writeToFile(String fileName, String output) throws IOException {
		Writer writer = new FileWriter(fileName);
		writer.write(output);
		writer.close();
	}
	
	/**
	 * Reverses a string.
	 * @param string
	 * @return reversed string
	 */
	private String reverse(String s) {
	    // define!
		String reverse = new StringBuffer(s).reverse().toString();
		return reverse;
	}
	
	/**
	 * Computes the frequency of words used in a files.
	 * Writes the words and their frequency to a file by order of frequency.
	 * @throws IOException
	 */
	private void computeWordFrequencies() throws IOException {
          // define!
		String output = "";
		for (String key : words.keySet()) {
			output += String.format("%s\t%d\n", key, words.get(key));
		}
		writeToFile("alfaSorted.txt", output);
	}
	

	private void computeFrequencyMap() {
          // define!
	}
	
	/**
	 * Computes the alphabetical order of words flipped backwards.
	 * Writes the words to a file in this order.
	 * @throws IOException
	 */
	private void computeBackwardsOrder() throws IOException {
	    // define!
		Set<String> wordSet = new TreeSet<String>();
		for (String word : words.keySet()) {
			wordSet.add(reverse(word));
		}
		
		String output = "";
		for (String word : wordSet) {
			output += String.format("%s\n", reverse(word));
		}
		
		writeToFile("backwardsSorted.txt", output);
	}

	public static void main(String[] args) throws IOException {
		WordLists wl = new WordLists("provtext.txt");  // arg[0] contains the input file name
		wl.computeWordFrequencies();
		wl.computeFrequencyMap();
		wl.computeBackwardsOrder();
		// wl.printTreeMap();
		// wl.writeTreeMap();
		
		System.out.println("Finished!");
	}
}



















