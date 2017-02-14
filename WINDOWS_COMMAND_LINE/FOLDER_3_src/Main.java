

import java.io.*;

import AST.*;
import CodeGen.AssemblyFilePrinter;
import CodeGen.StringCollector;
import CodeGen.TempGenerator;
import IR.IR_PROGRAM;
import SemanticAnalysis.SymbolTable;
import java_cup.runtime.Symbol;

public class Main
{
	public static final String OK_STRING = "OK";
	public static final String FAIL_STRING = "FAIL";
	
	static public void main(String argv[]) throws Exception
	{
		Lexer l;
		CUP_FILECup p;
		FileReader file_reader;
		String inputFileName = argv[0];
		String outputFileName = argv[1];
		String assemblyOutput = argv[1];
		
		// TODO: Surround with try-catch
	//	try
	//	{
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
			SymbolTable.hashTable.clear();
			Symbol temp=p.parse();
			AST_PROGRAM astNode = (AST_PROGRAM)temp.value;
			if(astNode.validate(null)!=null)
			{
				// Writing output
				BufferedWriter outputWriter = new BufferedWriter(new FileWriter(new File(outputFileName)));
				outputWriter.write(OK_STRING);
				outputWriter.close();
				
				AssemblyFilePrinter.reset();
				FileWriter printer = AssemblyFilePrinter.getInstance(assemblyOutput);
				TempGenerator.reset();
				// builds ir tree.
				SymbolTable.hashTable.clear();
				StringCollector.reset();
				IR_PROGRAM irNode = astNode.createIR();
				
				// prints the strings before use them in the generate code.
				StringCollector.printStringsToAssembly();
				
				// prints vftables
				AssemblyFilePrinter.printVirtualFunctionsTablesAndUpdateVFTAdresses();
				
				AssemblyFilePrinter.getInstance(null).write(String.format(".text%s", System.lineSeparator()));
				// codeGen
				irNode.generateCode();
				printer.close();
			}
			else
			{
			// Writing output
			BufferedWriter outputWriter = new BufferedWriter(new FileWriter(new File(outputFileName)));
			outputWriter.write(FAIL_STRING);
			outputWriter.close();
			}
			
    /*	}     
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
		*/
	}
}


