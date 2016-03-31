import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * 
 * @author Mona Khoshoi, Elias Svensson
 * @since 31-03-16 
 * Email: khoshoimona@gmail.com, elias.svensson.1992@gmail.com
 *
 */	

public class AngloTrainer {

	private int lengthOfLongestWord = 0;
	private TreeSet<String> dictionary = new TreeSet<String>();
	private String randomLetters;
	private TreeSet<String> correctWords;
	private static Random randomGenerator = new Random();

	// constructor
	public AngloTrainer(String dictionaryFile) throws IOException {
		loadDictionary(dictionaryFile);
		
	}
	
	/**
	 * Runs the program, allowing the user to enter words.
	 * When done or the user fails, it prints all the possible words.
	 */
	public void run() {
		// print out dictionary to screen
		dumpDict();
		
		randomLetters = randomLetters(lengthOfLongestWord);
		
		// print random letters to screen
		printRandomLetters();

		randomLetters = sort(randomLetters);
		correctWords = getPossibleWords(randomLetters);
		Scanner scanner = new Scanner(System.in);
		String word;
		HashSet<String> usedWords = new HashSet<String>();
		
		do {
			// try to read a new word from user
			try {
				word = scanner.next();
			}
			// quit if e.g. <ctrl-Z> is pressed
			catch (Exception e) {
				break;
			}
			// check if that word has already been used
			if (usedWords.contains(word)) {
				System.out.println("You have already used that word.");
			}
			else {
				// check if the word only uses characters from randomLetters
				if (!includes(randomLetters, sort(word))) {
					System.out.println("Your suggestion used characters that are not found in the given random characters.");
					break;
				}
				// check if the word is correct
				if (!correctWords.contains(word)) {
					System.out.println("Your suggestion was not found in the dictionary.");
					break;
				}
				// add word to usedWords
				usedWords.add(word);
				
				System.out.println("ok!");
				
			}
		}
		while(true);
		
		scanner.close();
		
		// print all possible words to screen
		printAllWords();	
	}

	/**
	 * Prints all possible words to screen.
	 */
	private void printAllWords() {
		System.out.println("I found:");
		for(String word : correctWords) {
			System.out.println(word);
		}
	}
	
	/**
	 * Prints random letters to screen.
	 */
	private void printRandomLetters() {
		System.out.format("Random letters: %s\nEnter words using these letters!\n", randomLetters);
	}
	
	/**
	 * Prints out the dictionary at the screen.
	 */
	private void dumpDict() {
		System.out.println(dictionary.toString());
	}

	/**
	 * Reads the dictionary into a suitable container.
	 * The file is a simple text file. One word per line.
	 * @param fileName
	 */
	private void loadDictionary( String fileName ) {
		File file = new File(fileName);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String s = scanner.nextLine();
				if (lengthOfLongestWord < s.length()) {
					lengthOfLongestWord = s.length();
				}
				dictionary.add(s);
			}
			scanner.close();
		} 
		catch (IOException e) {
			System.out.println("Scanner exception.");
		}
	}
	
	private String randomLetters( int length ) {
	    String letters = "aabcdeefghiijklmnoopqrstuuvwxyyz";  
	    StringBuffer buf = new StringBuffer(length);
	    for ( int i = 0; i < length; i++ ) 
		    buf.append( letters.charAt(randomGenerator.nextInt(letters.length())));
	
	    return buf.toString();
	}
	
	/**
	 * Sorts the characters in a word by alphabetical order
	 * @param word
	 * @return sorted word
	 */
	private String sort(String word) {
		char[] chars = word.toCharArray();
		Arrays.sort(chars);
		String sortedWord = new String(chars);
		return sortedWord;
	}
	
	/**
	 * Find possible words of given letters matching dictionary words
	 * @param letters
	 * @return possible words in dictionary
	 */
	private TreeSet<String> getPossibleWords(String letters) {
		TreeSet<String> words = new TreeSet<String>();
		for (String word : dictionary) {
			if (includes(letters, sort(word)))
				words.add(word);
		}
		return words;
	}
	
	
	/* Def. includes	
	 * Let #(x,s) = the number of occurrences of the charcter x in the string s.
	 * includes(a,b) holds iff for every character x in b, #(x,b) <= #(x,a)
	 * 
	 * A neccessary precondition for includes is that both strings are sorted
	 * in ascending order.
	 */
	private boolean includes( String a, String b ) {
		if ( b == null || b.length() == 0 )
			return true;
		else if ( a == null || a.length() == 0 )
			return false;
		
		//precondition: a.length() > 0 && b.length() > 0
		int i = 0, j = 0;
		while ( j < b.length() ) {
			if (i >= a.length() || b.charAt(j) < a.charAt(i))
				return false;
			else if (b.charAt(j) == a.charAt(i)) {
				i++; j++;
			} else if (b.charAt(j) > a.charAt(i))
				i++;
		}
		//postcondition: j == b.length()
		return true;
	}
	
     // This is just for demonstration purposes.
	private void testIncludes() { 
		//                                            expected value
		System.out.println(includes("abc",""));		//t
		System.out.println(includes("","abc"));		//f
		System.out.println(includes("abc","abc"));	//t
		System.out.println(includes("abc","bcd"));	//f
		System.out.println(includes("abc","a"));	//t
		System.out.println(includes("abc","b"));	//t
		System.out.println(includes("abc","c"));	//t
		System.out.println(includes("abc","ab"));	//t
		System.out.println(includes("abc","bc"));	//t
		System.out.println(includes("abc","ac"));	//t
		System.out.println(includes("abc","abcd"));	//f
		System.out.println(includes("abc","abd"));	//f
		System.out.println(includes("abc","d"));	//f
		System.out.println(includes("",""));		//t
		System.out.println(includes("abc","ca"));	//f
		System.out.println(includes("abc","bac"));	//f
		System.out.println(includes("abc","abbc"));	//f
		System.out.println(includes("abbc","abc"));	//t
		System.out.println(includes(null,null));    //t
		System.out.println(includes(null,""));	    //t
		System.out.println(includes(null,"abc"));	//f
		System.out.println(includes("",null));		//t
		System.out.println(includes("abc",null));   //t
	}
	
    public static void main(String[] args) {
    	try {
    		AngloTrainer angloTrainer = new AngloTrainer("dictionary.txt");
    		angloTrainer.run();
    	}
    	catch (IOException e) {
    		System.out.print("Could not open file: " + e);
    	}
    }
}












