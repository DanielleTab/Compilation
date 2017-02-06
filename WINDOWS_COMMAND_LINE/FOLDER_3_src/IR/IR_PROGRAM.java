package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.SymbolTable;

public class IR_PROGRAM extends IR_Node {

	private static final String MAIN_WRAPPER_LABEL = "Label_main_wrapper";
	// fields
	public IR_CLASS_DECL_LIST classDeclList;
	
	// C'tor
	public IR_PROGRAM(IR_CLASS_DECL_LIST classDeclList)
	{
		this.classDeclList = classDeclList;
	}

	/*
	 * The main wrapper pushes this = null to the stack and then jmp to the real main function.
	 */
	public void writeMainWrapper(StringNLBuilder printed) throws IOException
	{
		printed.appendNL(String.format("%s:", MAIN_WRAPPER_LABEL));
		CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
		CodeGen_Utils.codeGen_Push(printed,zeroTemp.getName());
		printed.appendNL(String.format("jmp %s", SymbolTable.mainFunctionLabel));
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
		printed.appendNL(String.format("jmp %s", END_LABEL_NAME));
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
	
	public void generateCode() throws IOException 
	{
		StringNLBuilder printed = new StringNLBuilder();
		printed.appendNL(String.format("jmp %s", MAIN_WRAPPER_LABEL));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		this.classDeclList.generateCode();
		printed = new StringNLBuilder();
		writeMainWrapper(printed);
		writeErrorTreatment(printed);
		writeProgramTermination(printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
}
