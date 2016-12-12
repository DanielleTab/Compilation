   
import java.io.*;
import java.io.PrintWriter;
import AST.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		CUP_FILECup p;
		FileReader file_reader;
		//PrintWriter file_writer;
		//String inputFilename = argv[0];
		//String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader("C:\\Users\\danielle\\Downloads\\EX3_without_some_things\\EX3\\WINDOWS_COMMAND_LINE\\FOLDER_6_Input\\test1_ok.ic");

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			//file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new CUP_FILECup(l);

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			p.parse();
			System.out.println("ok");
			
			
			
			/**************************/
			/* [10] Close output file */
			/**************************/
			//file_writer.close();
    	}
			     
		catch (Exception e)
		{
			System.out.println("bad");
			e.printStackTrace();
		}
	}
}


