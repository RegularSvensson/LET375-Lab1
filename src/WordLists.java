// Author(s):
// Version: 
// Date:	

public class WordLists {
	private Reader in = null;

	public WordLists(String inputFileName) {
	    // ... define!
	}
	
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
	
	private String reverse(String s) {
	    // define!
	}
	
	private void computeWordFrequencies() {
          // define!
	}
	

	private void computeFrequencyMap() {
          // define!
	}
	

	private void computeBackwardsOrder() {
	    // define!
	}

	public static void main(String[] args) throws IOException {
		WordLists wl = new WordLists(args[0]);  // arg[0] contains the input file name
		wl.computeWordFrequencies();
		wl.computeFrequencyMap();
		wl.computeBackwardsOrder();
		
		System.out.println("Finished!");
	}
}



















