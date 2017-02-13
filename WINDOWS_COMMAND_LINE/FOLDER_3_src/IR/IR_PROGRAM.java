package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.TooManyTempsException;

public class IR_PROGRAM extends IR_Node {

	private static final String MAIN_WRAPPER_LABEL =String.format("Label_%d_main_wrapper",AssemblyFilePrinter.addLabelIndex());
	// fields
	public IR_CLASS_DECL_LIST classDeclList;
	
	// C'tor
	public IR_PROGRAM(IR_CLASS_DECL_LIST classDeclList)
	{
		this.classDeclList = classDeclList;
	}

	/*
	 * The main wrapper pushes this = null to the stack, call the real main function
	 * and then jump to the end label.
	 */
	public void writeMainWrapper(StringNLBuilder printed) throws IOException, TooManyTempsException
	{
		printed.appendNL(String.format("%s:", MAIN_WRAPPER_LABEL));
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
		CodeGen_Utils.codeGen_Push(printed,zeroTemp.getName());
		printed.appendNL(String.format("jal %s", SymbolTable.mainFunctionLabel));
		printed.appendNL(String.format("j %s", END_LABEL_NAME));
	}
	
	/*
	 * prints 666 as Oren requires and terminates the program.
	 */
	public void writeErrorTreatment(StringNLBuilder printed)
	{
		printed.appendNL(String.format("%s:", ERROR_LABEL_NAME));
		printed.appendNL("li $a0,666");
		printed.appendNL("li $v0,1"); // the syscall number for printing.
		printed.appendNL("syscall");
		printed.appendNL(String.format("j %s", END_LABEL_NAME));
	}
	
	/*
	 * terminates the program appropriately with syscall 10.
	 */
	public void writeProgramTermination(StringNLBuilder printed)
	{
		printed.appendNL(String.format("%s:", END_LABEL_NAME));
		printed.appendNL("li $v0,10"); // the syscall number for exit.
		printed.appendNL("syscall");
	}
	
	// TODO: Implement.
	private void writeStaticStrlen(StringNLBuilder printed)
	{	
	}
	
	// TODO: Implement.
	private void writeStaticUtilityFunctions(StringNLBuilder printed)
	{
		writeStaticStrlen(printed);
	}
	
	public void generateCode() throws IOException, SemanticAnalysisException 
	{
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("j %s", MAIN_WRAPPER_LABEL));
		writeStaticUtilityFunctions(printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		this.classDeclList.generateCode();
		
		printed = new StringNLBuilder();
		writeMainWrapper(printed);
		writeErrorTreatment(printed);
		writeProgramTermination(printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
}
