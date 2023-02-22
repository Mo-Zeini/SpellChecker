package SpellChecker;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.swing.JFileChooser;

/*
 * This program uses two of the classes in the Java Collection Framework: HashSet and TreeSet to implement a spell checker.
 * English words are listed in the file words.txt, one word per line.
 * To verify that the words on this list are spelled correctly, the software will look them up.
 * The words are also kept in a set.
 */

public class SpellChecker {
	
	public static void main(String[] args) throws FileNotFoundException {

		try {
			// Create the Scanner and read the text file.
			Scanner filein = new Scanner(new File("/Users/mohamedelzeini/cs1103/words.txt"));
			// Create a new hash set object.
			HashSet<String> hset = new HashSet<String>();
			// Add words from the file into the dictionary.
			while (filein.hasNext()) {
				String tk = filein.next();
				hset.add(tk.toLowerCase());
			}
			// Print out the number of words in the dictionary.
			System.out.println("The size of the set is: " + hset.size());
			System.out.println();
			System.out.println("suggestions:");
			// Create the Scanner to read the words from the selected file and store them into a variable.
			Scanner userFile = new Scanner(getInputFileNameFromUser());
			userFile.useDelimiter("[^a-zA-Z]+"); // Skip over any non-letter characters in the file.

			while (userFile.hasNext()){
				String secWord = userFile.next();
				String secWord1 = secWord.toLowerCase(); // convert it to lower case.

				if(!hset.contains(secWord1)){
					// Print out corrections for words.
					System.out.println(secWord1 + ":" + corrections(secWord1, hset));
				}

			}
		}

		catch (IOException e) {
			System.out.println("File not found!");
		}

	} // end of main()

/**
 * This method stores the variations in a tree set that are contained in the dictionary.
 * It returns suggested correct spellings of the misspelled words in alphabetical order with no duplicates.
 * It does this as follows:
 * It deletes any one of the letters from the misspelled word.
 * It changes any letter in the misspelled word to any other letter.
 * It inserts any letter at any point in the misspelled word.
 * It swaps any two neighboring characters in the misspelled word.
 * It inserts a space at any point in the misspelled word (and checks that both of the words that are produced are in the dictionary)
 * @param badWord: variations
 * @param dictionary: the set of words
 * @return: suggested correct spellings of the misspelled words in alphabetical order with no duplicates.
 */
	static TreeSet<String> corrections(String badWord, HashSet<String> dictionary){
		TreeSet<String> treeSet = new TreeSet<String>();
		// Deletes any one of the letters from the misspelled word.
		for (int i = 0; i < badWord.length(); i++){
			String str = badWord.substring(0, i) + badWord.substring(i + 1);
			// Check if it is already in the dictionary.
			if(dictionary.contains(str)) {
				treeSet.add(str);
			}
		}
		// Change any letter in the misspelled word to any other letter.
		for (int i = 0; i < badWord.length(); i++){
			for (char ch = 'a'; ch <= 'z'; ch++) {
				String str = badWord.substring(0, i) + ch + badWord.substring(i + 1);
				
				if(dictionary.contains(str)){
					treeSet.add(str);
				}
			}
		}
		// Insert any letter at any point in the misspelled word.
		for (int i = 0; i <= badWord.length(); i++){
			for (char ch = 'a'; ch <= 'z'; ch++) {
				String str = badWord.substring(0, i) + ch + badWord.substring(i);
				
				if(dictionary.contains(str)){
					treeSet.add(str);
				}
			}
		}
		// Swap any two neighboring characters in the misspelled word.
		for(int i = 0; i < badWord.length() - 1; i++){
			String str = badWord.substring(0, i)+ badWord.substring(i + 1, i + 2) + badWord.substring(i, i + 1)+ badWord.substring(i + 2);
			
			if(dictionary.contains(str)){
				treeSet.add(str);
			}
		}
		// inserts a space at any point in the misspelled word 
		// (and checks that both of the words that are produced are in the dictionary)
		for (int i = 1; i < badWord.length(); i++){
			String stringInput = badWord.substring(0, i) + " " + badWord.substring(i);
			String tempString = "";
			StringTokenizer tempWords = new StringTokenizer(stringInput);
			// Traverse the set of words.
			while(tempWords.hasMoreTokens()){
				String stringWord1 = tempWords.nextToken();
				String stringWord2 = tempWords.nextToken();
				// Check if the temporary words are in the dictionary.
				if(dictionary.contains(stringWord1) && dictionary.contains(stringWord2)){
					tempString = stringWord1 + " " + stringWord2;
				}
				// Otherwise, break.
				else
					break;
				
				treeSet.add(tempString);
			}
		}
		// Check if there is no suggestions for the checked words.
		if(treeSet.isEmpty()){
			treeSet.add("no suggestions");
		}
		// Return the tree set.
		return treeSet;
	}
	
	/**
	 * Lets the user select an input file using a standard file
	 * selection dialog box.  If the user cancels the dialog
	 * without selecting a file, the return value is null.
	 */
	static File getInputFileNameFromUser() {
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setDialogTitle("Select File for Input");
		int option = fileDialog.showOpenDialog(null);
		if (option != JFileChooser.APPROVE_OPTION)
			return null;
		else
			return fileDialog.getSelectedFile();
	}

} // end of class SpellChecker.
