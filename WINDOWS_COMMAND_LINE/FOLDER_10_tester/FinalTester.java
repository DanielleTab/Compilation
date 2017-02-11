import java.io.File;
import java.io.FileOutputStream;
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
	
	private static final String IC_FILES_DIR = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//IC//";
	private static final String EXPECTED_OUTPUTS_DIR = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//final_tests_ok//ExpectedOutput//";
	private static final String EXPECTED_OUTPUT_SUFFIX = "_EO.txt";
	private static final String PSEUDO_MIPS_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_pseudoMips.pmips";
	private static final String BATCH_SCRIPT_PATH = "WINDOWS_COMMAND_LINE\\FOLDER_10_tester\\testerUtils\\convert_pseudoMips_to_exe_and_run.bat";
	private static final String EXE_OUTPUT_PATH = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//testerUtils//tester_exe_output.txt";
	
	/**
	 * @brief	Compiles the specified IC file to pseudo-MIPS, 
	 * 			using our main program.
	 * 
	 * @param 	icFilePath
	 * @throws Exception 
	 */
	static private void compileICToPseudoMips(String icFilePath) throws Exception
	{
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
	static private void convertPseudoMipsToExeAndRun() throws IOException, InterruptedException
	{
		System.out.println("Converting the pseudo mips to EXE and running. This might take a few seconds.");
		Process cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait cmd.exe /C %s", BATCH_SCRIPT_PATH));
		// Use this line if you don't want the CMD to close automatically.
		//Process cmdProcess = Runtime.getRuntime().exec(String.format("cmd.exe /C start /wait %s", BATCH_SCRIPT_PATH));
		cmdProcess.waitFor();
	}
	
	static public void compareToExpectedOutput(String expectedOutputFilePath) throws IOException
	{	
		byte[] outputBytes = Files.readAllBytes(Paths.get(EXE_OUTPUT_PATH));
		byte[] expectedOutputBytes = Files.readAllBytes(Paths.get(expectedOutputFilePath));
		
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
	
	static public void testSpecificFile(String icFileName) throws Exception
	{
		System.out.println("Running " + icFileName + " :");
		compileICToPseudoMips(IC_FILES_DIR + icFileName);
		convertPseudoMipsToExeAndRun();
		compareToExpectedOutput(EXPECTED_OUTPUTS_DIR + icFileName + EXPECTED_OUTPUT_SUFFIX);
	}
	
	static public void main(String argv[]) throws Exception 
	{
		outputWriter = new PrintStream(new FileOutputStream(TESTER_OUTPUT_FILE_NAME));
		//System.setOut(outputWriter);
		
		File folder = new File(IC_FILES_DIR);
		File[] folderFiles = folder.listFiles();

	    for (int i = 0; i < folderFiles.length; i++) 
	    {
	    	if (folderFiles[i].isFile()) 
	    	{
	    		testSpecificFile(folderFiles[i].getName());
	    	} 
	    }
		    
		System.out.println("Total passed: " + passedNum);
		System.out.println("Total failed: " + failedNum); 
	}
	
}
