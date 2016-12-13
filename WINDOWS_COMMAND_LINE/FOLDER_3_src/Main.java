

import java.io.*;

import AST.*;

public class Main
{
	public static final String OK_STRING = "OK";
	public static final String FAIL_STRING = "FAIL";
	
	static public void main(String argv[])
	{
		Lexer l;
		CUP_FILECup p;
		FileReader file_reader;
		String inputFileName = argv[0];
		String outputFileName = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFileName);

			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new CUP_FILECup(l);

			p.parse();
			
			// Writing output
			BufferedWriter outputWriter = new BufferedWriter(new FileWriter(new File(outputFileName)));
			outputWriter.write(OK_STRING);
			outputWriter.close();
			
    	}
			     
		catch (Exception e)
		{
			try
			{
				BufferedWriter outputWriter = new BufferedWriter(new FileWriter(new File(outputFileName)));
				outputWriter.write(FAIL_STRING);
				outputWriter.close();
			}
			catch (Exception e2)
			{
			}
		}
	}
}


