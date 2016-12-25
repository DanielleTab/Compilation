import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Tester 
{
	public static final String INPUT_FILE_PREFIX = "WINDOWS_COMMAND_LINE//FOLDER_10_tester//";
	public static final String OUTPUT_FILE_NAME = "output.txt";
	public static final String OK_STRING = "OK";
	public static final String FAIL_STRING = "FAIL";
	
	private static int passedNum = 0;
	private static int failedNum = 0;
	
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
	
	static public void processFile(String inputFilePath)
	{
		try
		{
			if (inputFilePath.endsWith(OK_STRING))
			{
				testSpecificFile(inputFilePath, OK_STRING);
			}
			else if (inputFilePath.endsWith(FAIL_STRING))
			{
				testSpecificFile(inputFilePath, FAIL_STRING);	
			}	
		}
		catch (Exception e) 
		{
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			System.out.println("Failed: Caught an exception: ");
			e.printStackTrace();
			System.out.println();
			failedNum++;
		}
	}
	
	static public void main(String argv[]) 
	{
		File folder = new File("WINDOWS_COMMAND_LINE//FOLDER_10_tester");
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
