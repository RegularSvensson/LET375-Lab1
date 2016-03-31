import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private Map<String, Integer> words = new TreeMap<String, Integer>();
	private String word = null;
	private Map<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>(
		new Comparator<Integer>() {
			public int compare(Integer first, Integer second) {
				return - 1 * first.compareTo(second);
			}
	});
	private Set<String> wordSet = new TreeSet<String>();

	// constructor
	public WordLists(String inputFileName) throws IOException {
		// read input file
		in = new BufferedReader(new FileReader(inputFileName));
		// call getWord() to add words and their count to words TreeMap
		while ((word = getWord()) != null) {
			// check if first time word used
			if (!words.containsKey(word)) {
				Integer i = 1;
				words.put(word, i);
			}
			// else, add one to count
			else {
				words.put(word, words.get(word) + 1);
			}
		}
	}
	
	/**
	 * Checks if a char is a punctuation.
	 * @param c
	 * @return true or false
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
		String reverse = new StringBuffer(s).reverse().toString();
		return reverse;
	}
	
	/**
	 * Computes the frequency of words used in a files.
	 * Writes the words and their frequency to a file.
	 * @throws IOException
	 */
	private void computeWordFrequencies() throws IOException {
		String output = "";
		// add words and their count to output string
		for (String key : words.keySet()) {
			output += String.format("%s\t%d\n", key, words.get(key));
		}
		writeToFile("alfaSorted.txt", output);
	}
	

	private void computeFrequencyMap() throws IOException {
        // Add count and words to map
		for(Map.Entry<String, Integer> entry : words.entrySet()) {
			if(!map.containsKey(entry.getValue())) {
				TreeSet<String> set = new TreeSet<String>();
				set.add(entry.getKey());
				map.put(entry.getValue(), set);
			}
			else {
				map.get(entry.getValue()).add(entry.getKey());
			}
		}
		String output = "";
		// add count to output string
		for(Integer n : map.keySet()) {
			output += String.format("%d:\n", n);
			// add words to output string
			for (String word : map.get(n)) {
				output += String.format("\t%s\n", word);
			}
		}
		writeToFile("frequencySorted.txt", output);
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
		
		System.out.println("Finished!");
	}
}



















