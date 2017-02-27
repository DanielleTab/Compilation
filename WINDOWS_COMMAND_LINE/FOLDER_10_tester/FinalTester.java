import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FinalTester 
{
	private static int passedNum = 0;
	private static int failedNum = 0;
	public static String TESTER_OUTPUT_FILE_NAME = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tester_output.txt";
	private static PrintStream outputWriter;
	
	private static final String SEMANTIC_ERROR_OUTPUT = "FAIL";
	private static final String IC_FILES_DIR = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//IC//";
	private static final String EXPECTED_OUTPUTS_DIR = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//ExpectedOutput//";
	private static final String EXPECTED_OUTPUT_SUFFIX = "_EO.txt";
	private static final String PSEUDO_MIPS_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_pseudoMips.pmips";
	private static final String BATCH_SCRIPT_PATH = "WINDOWS_COMMAND_LINE\\FOLDER_10_tester\\testerUtils\\convert_pseudoMips_to_exe_and_run.bat";
	private static final String EXE_OUTPUT_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_exe_output.txt";
	private static final String DO_NOT_RUN_SUFFIX = "DoNotRun";
	
	/**
	 * @brief	Compiles the specified IC file to pseudo-MIPS, 
	 * 			using our main program.
	 * 
	 * @param 	icFilePath
	 * @throws Exception 
	 */
	static private void compileICToPseudoMips(String icFilePath) throws Exception
	{
		// TODO:
		// Deleting the old pseudo-MIPS file
		
		String[] args = new String[3];
		args[0] = icFilePath;
		args[1] = PSEUDO_MIPS_PATH;
		Main.main(args);
		
		
	}
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @brief	Converts the pseudo MIPS file to an EXE file and runs that EXE,
	 * 			using a batch script.
	 */
	static private void convertPseudoMipsToExeAndRun(String icFileName, TesterMode mode) throws IOException, InterruptedException
	{
		Process cmdProcess = null;
		
		System.out.println("Converting the pseudo mips to EXE and running. This might take a few seconds.");
		System.out.println("Reminder: running " + icFileName);
		
		switch (mode)
		{
		
		case MANUALY_CLOSE:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait %s", BATCH_SCRIPT_PATH));
			break;
			
		case AUTO_CLOSE:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait cmd.exe /C %s", BATCH_SCRIPT_PATH));
			break;
			
		case AUTO_CLOSE_MINIMIZE_CMD:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait /min cmd.exe /C %s", BATCH_SCRIPT_PATH));
			break;
			
		}
		
		cmdProcess.waitFor();
	}
	
	static public void compareToExpectedOutput(String icFileName) throws IOException
	{	
		String expectedOutputFilePath = EXPECTED_OUTPUTS_DIR + icFileName + EXPECTED_OUTPUT_SUFFIX;
		byte[] outputBytes = Files.readAllBytes(Paths.get(EXE_OUTPUT_PATH));
		byte[] expectedOutputBytes = Files.readAllBytes(Paths.get(expectedOutputFilePath));
		
		System.out.println(icFileName + " result:");
		if (Arrays.equals(outputBytes, expectedOutputBytes))
		{
			System.out.println("Passed.\n");
			passedNum++;
		}
		else
		{
			System.out.println("Failed.\nExpected:");
			System.out.println(new String(expectedOutputBytes));
			System.out.println("Got:");
			System.out.println(new String(outputBytes));
			System.out.println("\n");
			failedNum++;
		}
	}
	
	/**
	 * Checks if the compilation of the IC file created a valid pseudo-MIPS file
	 * by checking it doesn't begin with "FAIL". 
	 * @throws IOException 
	 */
	static private boolean isPseudoMipsValid() throws IOException
	{
		BufferedReader pseudoMipsFile = new BufferedReader(new FileReader(new File(PSEUDO_MIPS_PATH)));
		String pseudoMipsFirstLine = pseudoMipsFile.readLine();
		pseudoMipsFile.close();
		
		return (!pseudoMipsFirstLine.equals(SEMANTIC_ERROR_OUTPUT));
	}
	
	static public void runSpecificTest(String icFileName, TesterMode mode) throws Exception
	{
		System.out.println("Running " + icFileName + ":");
		compileICToPseudoMips(IC_FILES_DIR + icFileName);
		if (isPseudoMipsValid())
		{
			convertPseudoMipsToExeAndRun(icFileName, mode);
			compareToExpectedOutput(icFileName);	
		}
		else
		{
			System.out.println(icFileName + " result:");
			System.out.println("Failed.\nError in compiling to pseudo-MIPS.");
			failedNum++;
		}	
	}
	
	static private void runAllTests(TesterMode mode) throws Exception
	{
		File folder = new File(IC_FILES_DIR);
		File[] folderFiles = folder.listFiles();

	    for (int i = 0; i < folderFiles.length; i++) 
	    {
	    	if (folderFiles[i].isFile()) 
	    	{
	    		String icFileName = folderFiles[i].getName();
	    		if (!icFileName.endsWith(DO_NOT_RUN_SUFFIX))
	    		{
	    			runSpecificTest(icFileName, mode);	
	    		}
	    	} 
	    }
		    
	    System.out.println();
		System.out.println("Total passed: " + passedNum);
		System.out.println("Total failed: " + failedNum); 
	}
	
	enum TesterMode
	{
		MANUALY_CLOSE,
		AUTO_CLOSE,
		AUTO_CLOSE_MINIMIZE_CMD,
	}
	
	static public void main(String argv[]) throws Exception 
	{
		outputWriter = new PrintStream(new FileOutputStream(TESTER_OUTPUT_FILE_NAME));
		//System.setOut(outputWriter);
		
		runSpecificTest("funcArgsEvaluationOrder", TesterMode.AUTO_CLOSE);
	    //runAllTests(TesterMode.AUTO_CLOSE_MINIMIZE_CMD);
	}
	
}
