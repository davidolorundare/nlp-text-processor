package textProcess.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import textProcess.compute.TextAnalyzer;
import textProcess.structures.AnalyzedData;


/**
 * This class handles file processing of the input 
 * text file and further text analysis.
 * 
 * 
 * 
 * @author David Olorundare
 *
 */
public final class FileHandler 
{
	
	//============================================ PRIVATE VARIABLES =============================================================
	
	
	// Represents the filepath of a file containing the text to be analyzed. 
	private String source;
	
	// Represents the filepath of a file containing the results of the text analysis.
	private String destination;
	
	// Represents the external file to which the text-analysis data is to be stored in.
	File outputData;
	
	// Holds an instance to this class.
	private volatile static FileHandler instance;
	
	// Represents a File read object for reading-in data from a file.
	private BufferedReader read;
	
	// Represents the text processor used in the analysis of text data from a file.
	private TextAnalyzer textProcessor;
	
	// Represents the data structure used for storing all the results of the text analysis.
	private AnalyzedData processedResults = new AnalyzedData();

	
	//============================================ CONSTRUCTOR =============================================================
	
	
	/**
	 *  Private Constructor of the FileHandler class.
	 */
	private FileHandler(){	}
	
	
  /**
   * Returns a singleton instance of the FileHandler class,
   * ensuring that only one instance of the Handler is active 
   * at any single time.
   * 
   */
	public static FileHandler getInstance() 
	{
      if (instance == null)
      {
          synchronized (FileHandler.class)
          {
              if (instance == null)
              {
                  instance = new FileHandler();
              }
          }
      }
      return instance;
   }
	
	
	//============================================ PUBLIC METHODS =============================================================
	
	
	/**
	 * Helper method that sets the current filepath
	 * of the input text-file to be analyzed.
	 * 
	 * @param filePath	current filepath of the input text-file.
	 * 
	 */
	public void setInputFilePath(String filePath)
	{
		source = filePath;
	}
	
	
	/**
	 * Helper method that sets the current filepath
	 * of the output text-file used for storing text-analysis
	 * results.
	 * 
	 * @param filePath	current filepath of the output text-file.
	 * 
	 */
	public void setOutputFilePath(String filePath)
	{
		destination = filePath;
	}
	
	
	/**
	 * Loads data from a file containing text and
	 * delegates its analysis to a given text-preprocessor
	 * implementation. 
	 * 
	 * @param	analyzer	the text-preprocessor implementation used to 
	 * 						analyze the input text data from a file.
	 * 
	 * @return	a structure containing the results of the 
	 * 			input text-file analysis.
	 *
	 * @throws IOException	if an error occurs while reading the input file.
	 * @throws FileNotFoundException	if the input or output text files are empty or cannot be found.
	 * 
	 */
	public AnalyzedData loadAndCompute(TextAnalyzer analyzer) throws IOException, FileNotFoundException
	{
		textProcessor = analyzer;
		
		File inputData = new File(source);
        if (!inputData.exists()) 
        { throw new FileNotFoundException("Input File Doesn't Exist"); }
		
        // Ensure the output file exists.
        outputData = new File(destination);
        if (!outputData.exists()) 
        { throw new FileNotFoundException("Output File Doesn't Exist"); }
           
        // Input file exists so read in data.
        read = new BufferedReader(new FileReader(source)); 
         
        // check if stream is ready for reading; analyze the text.
        if (read.ready())
        {  processedResults = textProcessor.analyzeText(read); } 
        else { throw new IOException("Error Reading the Input File"); }
        
        // Return the text analysis for printing/storage into an external file. 
        return processedResults;
	}
	
	
	/**
	 * Helper method that writes some string data
	 * to the given external output file.
	 * 
	 * @param data	the string data to be written to 
	 * 				a given external file.
	 * 
	 * @throws IOException if an error occurs while reading the input file.
	 */
	public void writeToFile(String data) throws IOException
	{
		Writer textFileWriter = new FileWriter(destination);
		
		textFileWriter.write(data);
		textFileWriter.close();
	}
	
	
	//============================================ PRIVATE METHODS =============================================================
	

}
