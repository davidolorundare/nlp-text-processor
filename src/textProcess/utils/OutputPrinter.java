package textProcess.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import textProcess.structures.AnalyzedData;


/**
 * Printing Utility class that outputs information
 * about the text analysis and also stores it 
 * in a given output-file.
 * 
 * @author David Olorundare
 *
 */
public final class OutputPrinter 
{
	
	//============================================ PRIVATE VARIABLES =============================================================
	
	
	// Holds an instance to this class
	private volatile static OutputPrinter instance;
	
	// Represents the output analysis information 
	// to be displayed and stored in an external file.
	StringBuilder output;
	
	
	//============================================ CONSTRUCTOR =============================================================
	
	
	/**
	 * Private Constructor of the PrintOutput class.
	 * 
	 */
	private OutputPrinter(){	}
	
	
	/**
	  * Returns a singleton instance of the PrintOut class,
	  * ensuring that only one instance of the class is active 
	  * at any single time.
	  * 
	  */
	public static OutputPrinter getInstance() 
	{
		if (instance == null)
	      {
	          synchronized (OutputPrinter.class)
	          {
	              if (instance == null)
	              {
	                  instance = new OutputPrinter();
	              }
	          }
	      }
	      return instance;
	}
	
	
	//============================================ PUBLIC METHODS =============================================================
	
	
	/**
	 * Helper method that prints text analysis information.
	 * 
	 * @param data	the text analysis data to print out.
	 * 
	 */
	public void printAnalysisToScreen(AnalyzedData data) throws IOException
	{
		output = new StringBuilder();
		output.append("# of paragraphs = " + data.getNumberOfParagraphs() + "\n");
		output.append("# of sentences = " + data.getNumberOfSentences() +"\n");
		output.append("# of tokens = " + data.getNumberOfTokens() + "\n");
		output.append("# of types = " + data.getNumberOfTypes() + "\n");
		
		output.append("\n================================");
		
		// Words are ordered by frequency (in the descending order), and words which 
		// have the same frequency count are ordered by lexicographical order (in the ascending order)
		List<Entry<String, Integer>> sortedEntries = data.getWords().entrySet().stream().sorted(Entry.<String, Integer>comparingByValue().reversed().thenComparing(Entry::getKey)).collect(Collectors.toList());
		
		// Display the analysis results.
		System.out.println(output.toString());
		
		output.append("\n");
		for (Entry<String, Integer> s : sortedEntries)
		{
			output.append(s.toString().replace('=', ' ' ) + "\n");
			System.out.println(s.toString().replace('=', ' ' ) );
		}
		
		// Save the analysis results to an external file.
		printAnalysisToFile(output.toString());
	}
		
	
	//============================================ PRIVATE METHODS =============================================================
	
	
	/**
	 * Helper method that prints some given
	 * text analysis data to an external file.
	 * 
	 * @param data	the text analysis information to store in an external file.
	 * 
	 * @throws IOException	if an error occurs while reading the input file.
	 */
	private void printAnalysisToFile(String data) throws IOException
	{
		FileHandler saveToFile = FileHandler.getInstance();
		
		saveToFile.writeToFile(data);
		
	}

	
}
