package textProcess.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import textProcess.compute.TextAnalyzer;
import textProcess.structures.AnalyzedData;
import textProcess.utils.FileHandler;
import textProcess.utils.OutputPrinter;


/**
 * 					CSC 594 ASSIGNMENT 1: TEXT PRE-PROCESSING
 * 
 * This is the entry main class for the text preprocessor program.
 *
 * :EXAMPLE USAGE:
 * 
 * From the command line run:
 * 
 * ./ TextPreProcessorMain.java <input_file_containing_text> <output_file_to_store_text_analysis>
 * 
 * 
 * The program returns an analysis of the text in the input file,
 * such as number of paragraphs, sentences, token-words, distinct-words,
 * and the word-frequency.
 * 
 * The implementation uses a pipe-design and code is split into: 
 * 
 * INPUT --> TEXT ANALYSIS --> OUTPUT
 * 
 * The program variables and component are first initialized,
 * then input text is taken from the file provided. The text analysis
 * is performed on each paragraph in the text, and the result
 * is outputted, and also stored in the given output file.
 *  
 * 
 * @author David Olorundare
 *
 */
public class TextPreProcessorMain 
{
	public static void main(String[] args) throws IOException
	{
		
		//==================== INITIALIZATION OF PROGRAM COMPONENTS ========================================
		
		FileHandler textData = FileHandler.getInstance();
		TextAnalyzer textComputation = TextAnalyzer.getInstance(); 
		OutputPrinter output = OutputPrinter.getInstance();
		AnalyzedData textAnalysis = new AnalyzedData();
			
		//============================ INPUT FILE HANDLING AND TEXT ANALYSIS =========================================
		
		// Take input file from the command line, run text analysis on it, and store results in the output file.
		if (args.length > 0)
		{
			// Set the input and output file locations.
			textData.setInputFilePath(args[0]);
			textData.setOutputFilePath(args[1]);
			
			try 
			{
				// Load the input text and analyze it.
				textAnalysis = textData.loadAndCompute(textComputation); 
			
		   //============================== RESULTS-PRINTING  ======================================================
				
				// Print out analysis of the given input text and stores it in an external file.
				output.printAnalysisToScreen(textAnalysis);
		        
		   //====================================================================================================
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }		
			catch (PatternSyntaxException e) { e.printStackTrace(); }
			catch (IllegalArgumentException e) { e.printStackTrace(); }
		}
		else 
		{
			// Show the user some Usage-info.
			System.out.println(":Usage: ./TextPreProcessorMain.java <input_file_containing_text> <output_file_to_store_text_analysis>");
			return;
		}
	}
}
