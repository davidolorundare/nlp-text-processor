package textProcess.structures;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the statistics
 * on the text data analyzed.
 * 
 * @author David Olorundare
 *
 */
public class AnalyzedData
{
	
	//============================================ PRIVATE VARIABLES =============================================================
	
	
	// Represents the number of paragraphs in the text.
	private int numberOfParagraphs;
	
	// Represents the number of sentences in the text.
	private int numberOfSentences;
	
	// Represents the number of word tokens in the text.
	private int numberOfTokens;
	
	// Represents the number of word types in the text.
	private int numberOfTypes;
	
	// Represents the mapping between word-types in the analyzed text and their frequency.
	private Map<String, Integer> types = new HashMap<String, Integer>();
	
	
	//============================================ CONSTRUCTOR =============================================================
	
	
	/**
	 * Constructor of the class.
	 * 
	 */
	public AnalyzedData(){	}
	
	
	//============================================ PUBLIC METHODS =============================================================
	
	
	/**
	 * Helper method that sets the word-
	 * to-frequency mapping in an analyzed text.
	 * 
	 * @param types the word-to-frequency mapping to set.
	 */
	public void setWords(Map<String, Integer> value) 
	{ types = value; }
	
	
	/**
	 * Helper method that sets the number
	 * of paragraphs in the analyzed text.
	 * 
	 * @param value number of paragraphs in the text.
	 */
	public void setNumberOfParagraphs(int value) 
	{ numberOfParagraphs = value; }

	
	/**
	 * Helper method that sets the number
	 * of sentences in the analyzed text.
	 * 
	 * @param value number of sentences in the text.
	 */
	public void setNumberOfSentences(int value) 
	{ numberOfSentences = value; }
	
	
	/**
	 * Helper method that sets the number
	 * of words in the analyzed text.
	 * 
	 * @param value number of word in the text.
	 */
	public void setNumberOfTokens(int value) 
	{ numberOfTokens = value; }
	

	/**
	 * Helper method that sets the number 
	 * of distinct words in the analyzed text.
	 * 
	 * @param value number of distinct words in the text.
	 */
	public void setNumberOfTypes(int value) 
	{ numberOfTypes = value; }
		
	
	/**
	 * Helper method that returns the number
	 * of sentences in the analyzed text.
	 * 
	 * @return the number of sentences in the text.
	 */
	public int getNumberOfSentences() 
	{ return numberOfSentences; }

	
	/**
	 * Helper method that returns the number
	 * of paragraphs in the analyzed text.
	 * 
	 * @return the number of paragraphs in the text.
	 */
	public int getNumberOfParagraphs() 
	{ return numberOfParagraphs; }


	/**
	 * Helper method that returns the number
	 * of words in the analyzed text.
	 * 
	 * @return the number of word-tokens in the text.
	 */
	public int getNumberOfTokens() 
	{ return numberOfTokens; }


	/**
	 * Helper method that returns the number
	 * of distinct words in the analyzed text.
	 * 
	 * @return the number of word-types in the text.
	 */
	public int getNumberOfTypes()
	{ return numberOfTypes; }


	/**
	 * Helper method that returns the word-
	 * to-frequency mapping in an analyzed text.
	 * 
	 * @return the word-to-frequency mapping.
	 */
	public Map<String, Integer> getWords() 
	{ return types; }

		
	//============================================ PRIVATE METHODS =============================================================
	
	// No Private Methods.
	
}
