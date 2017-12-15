package textProcess.compute;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import textProcess.structures.AnalyzedData;


/**
 * This class performs various text analysis
 * operations on a given input text data.
 * It has methods for performing paragraph, 
 * sentence, and word analysis using the built-in 
 * Java BufferedReader readline() method, built-in
 * Java BreakIterator Sentence segementation method,
 * and Java Regex methods, respectively.
 * 
 * @author David Olorundare
 *
 */
public final class TextAnalyzer 
{
	
	//============================================ PRIVATE VARIABLES =============================================================
	
	
	// Represents an instance to this class.
	private volatile static TextAnalyzer instance;

	// Represents a buffer holding the input text data in a stream.
	private BufferedReader inputData;
	
	// Represents a list of sentences in a given paragraph.
	private List<String> sentences;
	
	// Represents a temporary store of the current text paragraph being processed.
	private String tempParagraph;
	
	// Represents the current sentence in a paragraph being segmented.
	int currSentenceIndex = 0;
	
	// Represents the previous sentence in a paragraph that was segmented.
 	int prevSentenceIndex = 0;
 	
 	// Represents the number of tokens in the analyzed text.
 	int tokenCount = 0;
 	
 	// Represents the number of paragraphs in the analyzed text.
 	int paragraphCount = 0;
 	
 	// Represents the number of sentences in the analyzed text.
 	int sentenceCount = 0; 
 	
 	// Represents a mapping between words in the analyzed text and their frequency.
 	Map<String, Integer> wordCount = new HashMap<String, Integer>();
 	//Map<String, WordCount> typeCount = new HashMap<String, WordCount>();
 	
 	// Represents a mapping between each distinct word (types) in the analyzed text and their frequency.
 	Map<String, Integer> typeCount = new HashMap<String, Integer>();
 	
 	//Represents statistics about the text analyzed in this session.
 	AnalyzedData resultStats = new AnalyzedData();
 	
 	
	//============================================ CONSTRUCTOR =============================================================
	
	
	/**
	 * Private Constructor of the TextAnalysis class.
	 * 
	 */
	private TextAnalyzer() {	}
	
	
	/**
	  * Returns a singleton instance of the TextAnalysis class,
	  * ensuring that only one instance is active 
	  * at any single time.
	  * 
	  */
	public static TextAnalyzer getInstance() 
	{
	      if (instance == null)
	      {
	          synchronized (TextAnalyzer.class)
	          {
	              if (instance == null)
	              {
	                  instance = new TextAnalyzer();
	              }
	          }
	      }
	      return instance;
	}
	
	
	
	//============================================ PUBLIC METHODS =============================================================
	
	
	/**
	 * Performs text analysis on a stream of text  data.
	 * 
	 * @param textStream	the text data to be analyzed.
	 * 
	 * @return	structure containing the results of text analysis.
	 * 
	 * @throws	IOException	if an error occurs while reading the input file.
	 * @throws	PatternSyntaxException  if the regex syntax of the pattern used is wrong.
	 * @throws	IllegalArgumentException if one of the arguments supplied to the regex methods is wrong.
	 */
	public AnalyzedData analyzeText(BufferedReader textStream) throws IOException, PatternSyntaxException, IllegalArgumentException
	{
		
		//=========================  ANALYZE THE INPUT TEXT  =======================================================
		
		inputData = textStream;

		String lineOfText;
		lineOfText = inputData.readLine();			
		
		while (true) 
		{
			 // Detect paragraphs in text.
			 if (lineOfText == null || lineOfText.trim().length() == 0) 
			 {
			     paragraphCount++; 
			     if(lineOfText == null ) break;
			 } 
			 else 
			 {	 
				// do sentence segmentation
				List<String> result = sentenceSegmementation(lineOfText);
				
				// do word-tokenization operation
				tokenizeSentence(result);
			 }
			 lineOfText = inputData.readLine();
		}
		
		inputData.close();
		
		// Populate the AnalysisData structure with Paragraph, Sentence, and Word count-info.
		resultStats.setNumberOfParagraphs(paragraphCount);
		
		// Return results of text analysis.
		return resultStats;
	}
	
	
	//=======================================================================  PRIVATE METHODS ==========================================================================================
	
	
	/**
	 * Helper method that performs sentence segmentation
	 * on a given paragraph of text, tracking the number
	 * of sentences in the paragraph.
	 * 
	 * 
	 * @param textParagraph		the text paragraph on which sentence segmentation
	 * 							is to be performed.
	 * 
	 * @return	list containing the segmented sentences.
	 * 
	 */
	 private List<String> sentenceSegmementation(String textParagraph) 
	 {
		
		tempParagraph = textParagraph;
		sentences = new ArrayList<String>();
		
		//do sentence segmentation operation.
		Locale locale = Locale.US;
		BreakIterator breaker = BreakIterator.getSentenceInstance(locale);
	 	breaker.setText(tempParagraph);
	 	
		int boundaryInd = breaker.first();
		while (boundaryInd != BreakIterator.DONE)
		{
			prevSentenceIndex = breaker.current();
			boundaryInd = breaker.next();
			currSentenceIndex = breaker.current();
						
			sentences.add(tempParagraph.substring(prevSentenceIndex, currSentenceIndex));
		}
		
		sentenceCount += (sentences.size()-1);
		
		resultStats.setNumberOfSentences(sentenceCount);
		
		return sentences;
	 }
	
	
	/**
	 * Helper method that tokenize's a list of sentences, 
	 * using Regular expressions and Java code, into words.
	 * 
	 *  Ensure each sentence is not null or empty, ignore if it is.
	 *		
	 *	For each sentence, iterate through it and use regex-patterns to do a search and replace, 
	 *  searching for pre-defined contractions and replacing them with their proper form; as separate strings.
     *  Use regex-patterns to also look for punctuation marks, separate them as separate-strings. 
	 *  Update the token-count and (distinct words) type-count.
     *  
	 * @param sentence	structure containing the list of sentences to be tokenized.
	 * 
	 * @throws	PatternSyntaxException  if the regex syntax of the pattern used is wrong.
	 * @throws	IllegalArgumentException if one of the arguments supplied to the regex methods is wrong.
	 * 
	 */
	private void tokenizeSentence(List<String> sentences) throws PatternSyntaxException, IllegalArgumentException
	{ 
	
		Map<String, String> contractions = new HashMap<String, String>();
		// Handle if the preceding word is a personal pronoun e.g. "he", "she", and "it".
		contractions.put("(^|[^a-zA-Z])([Hh]e)'s", "$2 is");
		contractions.put("(^|[^a-zA-Z])([Ss]e)'s", "$2 is");
		contractions.put("(^|[^a-zA-Z])([Ii]t)'s", "$2 is");
		
		//Handle other forms of word-contraction.
		//contractions.put("([a-zA-Z]+)('s)", "$1 $2");
		contractions.put("'s", " 's");
		contractions.put("'d", " would");
		contractions.put("'re", " are");
		contractions.put("'ll", " will");
		contractions.put("n't", " not");
		contractions.put("'nt", " not");
		contractions.put("'ve", " have");
		contractions.put("'m", " am");
		
		// Handle numbers followed by letters e.g. "80s" , "90s".
		contractions.put("([0-9]+)([a-zA-Z]+)", "$1 $2");

		// For each sentence containing words, tokenize the words using Regex.
		for (String sentence: sentences)
		{
			if (!sentence.equals(" ") || !(sentence == null) )
			{
				// First expand any word-contractions.
				for (String key : contractions.keySet())
				{
					sentence = sentence.replaceAll(key + "\\b", contractions.get(key));
				}
				
				// Next, tokenize the sentence and its punctuations; into a list of tokens.
				List<String> tokenizedSentence = splitter(sentence);

				// Finally, count all tokens and types.
				countWords(tokenizedSentence);
			}
		}
   }
	 
   
   /**
    * Helper method that iterates through an 
    * array of words, counting the number of
    * word-tokens and word-types present.
    * 
    * @param wordsList	an array containing word-tokens.
    * 
    */
   private void countWords(List<String> wordsList)
   {
	   // iterate through the array of words counts both tokens and types.
	   for (String word : wordsList)
	   {
		   // Count number of tokens 
		   // and store their word-to-frequency mapping in a list.
		   Integer num = wordCount.get(word);
	        num = (num == null) ? 1 : ++num;
	        wordCount.put(word, num);
		   
		   // Count number of types (distinct words) 
		   if ( typeCount.keySet().contains(word))
		   {
			   typeCount.put(word, (typeCount.get(word)+1) );
		   }
		   typeCount.put(word, 1);
	   }
	   
	   // Calculate the total number of tokens.
	   int tokenCount = 0;
	   for (String s: wordCount.keySet())
	   {
		   tokenCount += wordCount.get(s);
	   }
	   
	   resultStats.setNumberOfTokens(tokenCount);
	   countTypes();
	   
   }
	
	
   /**
    * Helper method that counts the number of word-types
    * in the an analyzed text.
    */
   private void countTypes()
   {
 	   // Calculate the total number of tokens.
	   int numOfTypes = 0;
	   for (String s: typeCount.keySet())
	   {
		   numOfTypes += typeCount.get(s);
	   }
	   
	   resultStats.setNumberOfTypes(numOfTypes);
	   resultStats.setWords(wordCount);
   }
   
	
   
   /**
    * Helper method that performs regular expression
    * operations to split a sentence into tokens, taking
    * note of punctuations.
    * 
    * @param sentence	the sentence to be split into tokens.
    * 
    * @return	a list of tokens.
    */
   private List<String> splitter(String sentence) 
   {
	    Pattern pattern = Pattern.compile("(\\w+)|(\\.{3})|(\\'s)|[^\\s]");
	    Matcher matcher = pattern.matcher(sentence);
	    List<String> list = new ArrayList<String>();
	    while (matcher.find()) { list.add(matcher.group()); }
	    return list;
	}
   
   	
}
