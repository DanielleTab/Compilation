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
	private static final String IC_FILES_DIR = "WINDOWS_COMMAND_LINE\\FOLDER_10_tester\\final_tests\\IC\\";
	private static final String EXPECTED_OUTPUTS_DIR = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests//ExpectedOutput//";
	private static final String EXPECTED_OUTPUT_SUFFIX = "_EO.txt";
	private static final String PSEUDO_MIPS_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_pseudoMips.pmips";
	private static final String PMIPS_TO_EXE_SCRIPT_PATH = "WINDOWS_COMMAND_LINE\\FOLDER_10_tester\\testerUtils\\convert_pseudoMips_to_exe_and_run.bat";
	private static final String IC_TO_EXE_SCRIPT_PATH = "WINDOWS_COMMAND_LINE\\FOLDER_10_tester\\testerUtils\\convert_ic_to_exe_and_run.bat";
	private static final String EXE_OUTPUT_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_exe_output.txt";
	private static final String DO_NOT_RUN_SUFFIX = "DoNotRun";
	
	private static final String ENCRYPTION_TEST_NAME = "Encryption";
	private static final String QUICKSORT_TEST_NAME = "Quicksort";
	private static final String RANDOM_GENERATOR_SCRIPT_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//generateRandomArrayInitCodeAndEO.py";
	
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
	
	static private void runBatchScript(String batchScriptPath, String scriptArgument, CmdMode cmdMode) throws IOException, InterruptedException
	{
		Process cmdProcess = null;
		
		switch (cmdMode)
		{
		
		case MANUALY_CLOSE:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait %s %s", batchScriptPath, scriptArgument));
			break;
			
		case AUTO_CLOSE:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait cmd.exe /C %s %s", batchScriptPath, scriptArgument));
			break;
			
		case AUTO_CLOSE_MINIMIZE_CMD:
			cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait /min cmd.exe /C %s %s", batchScriptPath, scriptArgument));
			break;
			
		}
		
		cmdProcess.waitFor();
	}
	
	/**
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @brief	Converts the pseudo MIPS file to an EXE file and runs that EXE,
	 * 			using a batch script.
	 */
	static private void convertPseudoMipsToExeAndRun(String icFileName, CmdMode cmdMode) throws IOException, InterruptedException
	{
		System.out.println("Converting the pseudo mips to EXE and running. This might take a few seconds.");
		System.out.println("Reminder: running " + icFileName);
		runBatchScript(PMIPS_TO_EXE_SCRIPT_PATH, "", cmdMode);
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
	
	static private void handleRandomTests(String icFileName) throws IOException, InterruptedException
	{
		if (icFileName.equals(ENCRYPTION_TEST_NAME) ||
			icFileName.equals(QUICKSORT_TEST_NAME))
		{
			System.out.println(String.format("Running random-generator script for the %s test...",
											 icFileName));
			Process cmdProcess = Runtime.getRuntime().exec(String.format("python %s %s", 
																		 RANDOM_GENERATOR_SCRIPT_PATH, 
																		 icFileName));
			cmdProcess.waitFor();
		}
	}
	
	static private void convertIcToExeAndRun(String icFileName, CmdMode cmdMode) throws IOException, InterruptedException
	{
		System.out.println("Converting the IC to EXE and running. This might take a few seconds.");
		System.out.println("Reminder: running " + icFileName);
		String icFilePath = IC_FILES_DIR + icFileName;
		runBatchScript(IC_TO_EXE_SCRIPT_PATH, icFilePath, cmdMode);
	}
	
	static public void runSpecificTest(String icFileName, CmdMode cmdMode, CompilationMode compilationMode) throws Exception
	{
		System.out.println("Running " + icFileName + ":");
		handleRandomTests(icFileName);
		switch (compilationMode)
		{
		
		case COMPILE_IN_ECLIPSE:
		{
			compileICToPseudoMips(IC_FILES_DIR + icFileName);
			if (isPseudoMipsValid())
			{
				convertPseudoMipsToExeAndRun(icFileName, cmdMode);
			}
			else
			{
				System.out.println(icFileName + " result:");
				System.out.println("Failed.\nError in compiling to pseudo-MIPS.");
				failedNum++;
				return;
			}
		}
		break;
		
		case COMPILE_IN_NOVA:
		{
			convertIcToExeAndRun(icFileName, cmdMode);
		}
		break;
		
		}
		
		compareToExpectedOutput(icFileName);
	}
	
	static private void deleteAutoGeneratedTests()
	{
		File encryptionICFile = new File(IC_FILES_DIR + FinalTester.ENCRYPTION_TEST_NAME);
		encryptionICFile.delete();
		
		File quicksortICFile = new File(IC_FILES_DIR + FinalTester.QUICKSORT_TEST_NAME);
		quicksortICFile.delete();
	}
	
	static private void runRandomTests(CmdMode cmdMode, CompilationMode compilationMode) throws Exception
	{
		runSpecificTest(FinalTester.ENCRYPTION_TEST_NAME, cmdMode, compilationMode);
		runSpecificTest(FinalTester.QUICKSORT_TEST_NAME, cmdMode, compilationMode);
	}
	
	static private void runAllTests(CmdMode cmdMode, CompilationMode compilationMode) throws Exception
	{
		deleteAutoGeneratedTests();
		
		File folder = new File(IC_FILES_DIR);
		File[] folderFiles = folder.listFiles();

	    for (int i = 0; i < folderFiles.length; i++) 
	    {
	    	if (folderFiles[i].isFile()) 
	    	{
	    		String icFileName = folderFiles[i].getName();
	    		if (!icFileName.endsWith(DO_NOT_RUN_SUFFIX))
	    		{
	    			runSpecificTest(icFileName, cmdMode, compilationMode);	
	    		}
	    	} 
	    }
	    runRandomTests(cmdMode, compilationMode);
	    
		    
	    System.out.println();
		System.out.println("Total passed: " + passedNum);
		System.out.println("Total failed: " + failedNum); 
	}
	
	enum CompilationMode
	{
		COMPILE_IN_ECLIPSE,
		COMPILE_IN_NOVA
	}
	
	enum CmdMode
	{
		MANUALY_CLOSE,
		AUTO_CLOSE,
		AUTO_CLOSE_MINIMIZE_CMD,
	}
	
	static public void main(String argv[]) throws Exception 
	{
		outputWriter = new PrintStream(new FileOutputStream(TESTER_OUTPUT_FILE_NAME));
		//System.setOut(outputWriter);
		
		//runSpecificTest("printPrimes", CmdMode.AUTO_CLOSE_MINIMIZE_CMD, CompilationMode.COMPILE_IN_NOVA);
	    runAllTests(CmdMode.AUTO_CLOSE_MINIMIZE_CMD, CompilationMode.COMPILE_IN_NOVA);
	}
	
}
