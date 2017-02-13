package IR;

import java.io.IOException;

import AST.AST_METHOD;
import AST.AST_Node;
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
	
	public static final String STRLEN_FUNCTION_LABEL = AST_METHOD.METHOD_LABEL_PREFIX + "strlen";
	public static final String STRLEN_WHILE_START_LABEL = STRLEN_FUNCTION_LABEL + "_while_start";
	public static final String STRLEN_WHILE_END_LABEL = STRLEN_FUNCTION_LABEL + "_while_end";
	
	// A static function doesn't have 'this' argument, therefore its first argument has the
	// same offset as 'this' argument in non-static functions.
	public static final int STATIC_FUNC_FIRST_ARG_OFFSET = AST_Node.FRAME_OFFSET_OF_THE_THIS_ARGUMENT;
	
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
	
	/**
	 * @brief	Writes the code of the static function strlen (which returns
	 * 			the length of the given string).
	 */
	private void writeStaticStrlen(StringNLBuilder printed) throws TooManyTempsException, IOException
	{
		CodeGen_Temp zero = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp argAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp strAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp strByte = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp index = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp strByteAddress = TempGenerator.getAndAddNewTemp();
		
		// Printing function label and prolog
		printed.appendNL(String.format("%s:", STRLEN_FUNCTION_LABEL));
		IR_METHOD.appendProlog(printed, 0); // no local variables - frame size is 0
		
		/*
		 * initialization code
		 * 
		 * li zero, 0
		 * addi argAddress, $fp, FIRST_ARG_FRAME_OFFSET
		 * lw strAddress, 0(argAddress)
		 * 
		 * lb strByte, (strAddress)
		 * li index, 0
		 */
		printed.appendNL(String.format("li %s, 0", zero.getName()));
		printed.appendNL(String.format("addi %s, $fp, %d", 
									   argAddress.getName(), 
									   STATIC_FUNC_FIRST_ARG_OFFSET));
		printed.appendNL(String.format("lw %s, 0(%s)", 
									   strAddress.getName(),
									   argAddress.getName()));
		printed.appendNL(String.format("lb %s, (%s)", 
									   strByte.getName(), 
									   strAddress.getName()));
		
		/*
		 * while code
		 * 
		 * Label_0_strlen_while_start:
		 * 		beq strByte, zero, label_0_strlen_while_end // while condition
		 * 		addi index, index, 1
		 * 		add strByteAddress, strAddress, index
		 * 		lb strByte, (strByteAddress)
		 * 		j Label_0_strlen_while_start
		 * Label_0_strlen_while_end:
		 */
		printed.appendNL(String.format("%s:", STRLEN_WHILE_START_LABEL));
		printed.appendNL(String.format("beq %s, %s, %s",
									    strByte.getName(),
									    zero.getName(),
									    STRLEN_WHILE_END_LABEL));
		printed.appendNL(String.format("addi %s, %s, 1", index.getName(), index.getName()));
		printed.appendNL(String.format("add %s, %s, %s", 
									   strByteAddress.getName(),
									   strAddress.getName(),
									   index.getName()));
		printed.appendNL(String.format("lb %s, (%s)", strByte.getName(), strByteAddress.getName()));
		printed.appendNL(String.format("j %s", STRLEN_WHILE_START_LABEL));
		printed.appendNL(String.format("%s:", STRLEN_WHILE_END_LABEL));
		
		// Storing length in v0 and returning
		printed.appendNL(String.format("mov $v0, %s", index.getName()));
		IR_METHOD.appendEpilog(printed);
	}
	
	// TODO: Implement.
	private void writeStaticUtilityFunctions(StringNLBuilder printed) throws TooManyTempsException, IOException
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
