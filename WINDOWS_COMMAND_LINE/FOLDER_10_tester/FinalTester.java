import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class FinalTester 
{
	public static final String INPUT_FILE_PREFIX = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//";
	public static final String OUTPUT_FILE_NAME = "output.pmips"; // pmips = pseudo MIPS
	public static final String TESTER_OUTPUT_FILE_NAME = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//tester_output.txt";
	public static final String OK_STRING = "OK";
	public static final String FAIL_STRING = "FAIL";
	
	private static int passedNum = 0;
	private static int failedNum = 0;
	private static PrintStream outputWriter;
	
	private static final String PSEUDO_MIPS_FILE_NAME = "tester_pseudoMips.txt";
	private static final String C_FILE_NAME = "tester_c.c";
	private static final String C_EXE_FILE_NAME = "tester_C_EXE.exe"; 
	private static final String EXE_OUTPUT_NAME = "tester_EXE_output.txt";
	
	// TODO: Implement.
	/**
	 * @brief	Compiles the specified IC file to pseudo-MIPS, 
	 * 			using our main program.
	 * 
	 * @param 	icFilePath
	 */
	static private void compileICToPseudoMips(String icFilePath)
	{
	}
	
	// TODO: Implement.
	/**
	 * @brief	Converts the pseudo MIPS file to a C file,
	 * 			using the simulator (which runs only in Linux).
	 */
	static private void convertPseudoMipsToC()
	{
	}
	
	// TODO: Implement.
	/**
	 * @brief	Compiles the C file, using Visual-Studio compiler.
	 */
	static private void compileC()
	{
	}
	
	// TODO: Implement.
	/**
	 * @brief	Runs the executable file, which creates an output file.
	 */
	static private void runExe()
	{
	}
	
	static public void compareToExpectedOutput(String expectedOutput) throws IOException
	{	
		BufferedReader outputFile = new BufferedReader(new FileReader(new File(OUTPUT_FILE_NAME)));
		String output = outputFile.readLine();
		outputFile.close();
		
		if (expectedOutput.equals(output))
		{
			System.out.println("Passed.\n");
			passedNum++;
		}
		else
		{
			System.out.println("Failed:\nExpected: " + expectedOutput + "\nFound:    " + output + "\n");
			failedNum++;
		}
	}
	
	static public void testSpecificFile(String inputFilePath, String expectedOutput) throws Exception
	{
		System.out.println("Running " + inputFilePath + " :");
		String[] args = new String[3];
		args[0] = INPUT_FILE_PREFIX + inputFilePath;
		args[1] = OUTPUT_FILE_NAME;
		Main.main(args);	
		compareToExpectedOutput(expectedOutput);
	}
	
	static public void processFile(String inputFilePath) throws Exception
	{
		if (inputFilePath.endsWith("method_multipleStringArguments_OK"))
		{
			System.out.println("now!");
		}
	//	try
	//	{
			if (inputFilePath.endsWith(OK_STRING))
			{
				testSpecificFile(inputFilePath, OK_STRING);
			}
			else if (inputFilePath.endsWith(FAIL_STRING))
			{
				testSpecificFile(inputFilePath, FAIL_STRING);	
			}	
		//}
		/*catch (Exception e) 
		{
			System.setOut(outputWriter);
			System.out.println("Failed: Caught an exception: ");
			e.printStackTrace(outputWriter);
			//e.printStackTrace();
			System.out.println();
			failedNum++;
		}*/
	}
	
	static public void main(String argv[]) throws Exception 
	{
		outputWriter = new PrintStream(new FileOutputStream(TESTER_OUTPUT_FILE_NAME));
		System.setOut(outputWriter);
		
		File folder = new File("WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//");
		File[] folderFiles = folder.listFiles();

	    for (int i = 0; i < folderFiles.length; i++) 
	    {
	    	if (folderFiles[i].isFile()) 
	    	{
	    		processFile(folderFiles[i].getName());
	    	} 
	    }
		    
		System.out.println("Total passed: " + passedNum);
		System.out.println("Total failed: " + failedNum); 
	}
	
}
