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
	public static final String MEMCPY_FUNCTION_LABEL = AST_METHOD.METHOD_LABEL_PREFIX + "memcpy";
	public static final String STRCAT_FUNCTION_LABEL = AST_METHOD.METHOD_LABEL_PREFIX + "strcat";
	public static final String WHILE_START_LABEL_SUFFIX = "_while_start";
	public static final String WHILE_END_LABEL_SUFFIX = "_while_end";
	
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
	private void writeStrlen(StringNLBuilder printed) throws TooManyTempsException, IOException
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
		String whileStartLabel = STRLEN_FUNCTION_LABEL + WHILE_START_LABEL_SUFFIX;
		String whileEndLabel = STRLEN_FUNCTION_LABEL + WHILE_END_LABEL_SUFFIX;
		printed.appendNL(String.format("%s:", whileStartLabel));
		printed.appendNL(String.format("beq %s, %s, %s",
									    strByte.getName(),
									    zero.getName(),
									    whileEndLabel));
		printed.appendNL(String.format("addi %s, %s, 1", index.getName(), index.getName()));
		printed.appendNL(String.format("add %s, %s, %s", 
									   strByteAddress.getName(),
									   strAddress.getName(),
									   index.getName()));
		printed.appendNL(String.format("lb %s, (%s)", strByte.getName(), strByteAddress.getName()));
		printed.appendNL(String.format("j %s", whileStartLabel));
		printed.appendNL(String.format("%s:", whileEndLabel));
		
		// Storing length in v0 and returning
		printed.appendNL(String.format("mov $v0, %s", index.getName()));
		IR_METHOD.appendEpilog(printed);
	}
	
	/**
	 * @brief	Writes the first part of memcpy, 
	 * 			which loads the arguments into registers.
	 */
	private void writeMemcpyArgsLoading(StringNLBuilder printed,
										CodeGen_Temp dstAddress,
										CodeGen_Temp srcAddress,
										CodeGen_Temp bytesToCopyNum) throws TooManyTempsException
	{
		CodeGen_Temp argAddress = TempGenerator.getAndAddNewTemp();
		
		// first argument - dstAddress
		printed.appendNL(String.format("addi %s, $fp, %d",
									   argAddress.getName(),
									   STATIC_FUNC_FIRST_ARG_OFFSET));
		printed.appendNL(String.format("lw %s, 0(%s)", 
									   dstAddress.getName(),
									   argAddress.getName()));
		
		// second argument - srcAddress
		printed.appendNL(String.format("addi %s, %s, %d", 
									   argAddress.getName(),
									   argAddress.getName(),
									   SymbolTable.ADDRESS_SIZE));
		printed.appendNL(String.format("lw %s, 0(%s)", 
				   					   srcAddress.getName(),
				   					   argAddress.getName()));
		
		// third argument - bytesToCopyNum
		printed.appendNL(String.format("addi %s, %s, %d", 
				   					   argAddress.getName(),
				   					   argAddress.getName(),
				   					   SymbolTable.ADDRESS_SIZE));
		printed.appendNL(String.format("lw %s, 0(%s)", 
				   					   bytesToCopyNum.getName(),
				   					   argAddress.getName()));
	}
	
	/**
	 * @brief	Writes the main loop of memcpy, which copies bytesToCopyNum
	 * 			bytes from srcAddress to dstAddress.
	 */
	private void writeMemcpyLoop(StringNLBuilder printed,
								 CodeGen_Temp dstAddress,
								 CodeGen_Temp srcAddress,
								 CodeGen_Temp bytesToCopyNum) throws TooManyTempsException
	{
		CodeGen_Temp index = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp srcByteAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp dstByteAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp srcByte = TempGenerator.getAndAddNewTemp();
		
		String whileStartLabel = MEMCPY_FUNCTION_LABEL + WHILE_START_LABEL_SUFFIX;
		String whileEndLabel = MEMCPY_FUNCTION_LABEL + WHILE_END_LABEL_SUFFIX;
		
		// begin loop
		printed.appendNL(String.format("li %s, 0", index.getName()));	
		printed.appendNL(String.format("%s:", whileStartLabel));
		
		// while condition: index == bytesToCopyNum
		printed.appendNL(String.format("beq %s, %s, %s", 
									   index.getName(),
									   bytesToCopyNum.getName(),
									   whileEndLabel));
		
		// updating the pointers
		printed.appendNL(String.format("add %s, %s, %s", 
				   					   srcByteAddress.getName(),
				   					   srcAddress.getName(),
				   					   index.getName()));
		printed.appendNL(String.format("add %s, %s, %s", 
				   					   dstByteAddress.getName(),
				   					   dstAddress.getName(),
				   					   index.getName()));
		
		// copying
		printed.appendNL(String.format("lb %s, (%s)", 
				   					   srcByte.getName(),
				   					   srcByteAddress.getName()));
		printed.appendNL(String.format("sb %s, (%s)", 
				   					   srcByte.getName(),
				   					   dstByteAddress.getName()));
		
		// promoting the index
		printed.appendNL(String.format("addi %s, %s, 1", 
				   					   index.getName(),
				   					   index.getName()));
		
		// end loop
		printed.appendNL(String.format("j %s", whileStartLabel));
		printed.appendNL(String.format("%s:", whileEndLabel));
	}
	
	/**
	 * @brief	Writes the code of the static function memcpy.
	 * 			This memcpy behaves like the standard memcpy, except for the fact that
	 * 			it returns 0 (and not the destination address). It has the following signature:
	 * 			int memcpy(void * dst, const void * src, size_t n);
	 * 			It gets a destination address, and copies to it n bytes from the source address. 
	 * 
	 * @param 	printed - a string builder, to which the memcpy's code is appended.
	 *  
	 * @throws 	TooManyTempsException
	 * @throws 	IOException 
	 */
	private void writeMemcpy(StringNLBuilder printed) throws TooManyTempsException, IOException
	{
		CodeGen_Temp dstAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp srcAddress = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp bytesToCopyNum = TempGenerator.getAndAddNewTemp();
		
		printed.appendNL(String.format("%s:", MEMCPY_FUNCTION_LABEL));
		IR_METHOD.appendProlog(printed, 0);
		writeMemcpyArgsLoading(printed, dstAddress, srcAddress, bytesToCopyNum);
		writeMemcpyLoop(printed, dstAddress, srcAddress, bytesToCopyNum);
		printed.appendNL("li $v0, 0"); // always returning 0.
		IR_METHOD.appendEpilog(printed);
	}
	
	/**
	 * Writes the first part of strcat, which loads the arguments into registers.
	 */
	private void writeStrcatArgsLoading(StringNLBuilder printed, 
										CodeGen_Temp str1Address, 
										CodeGen_Temp str2Address) throws TooManyTempsException
	{
		CodeGen_Temp argAddress = TempGenerator.getAndAddNewTemp();
		
		// first argument - str1Address
		printed.appendNL(String.format("addi %s, $fp, %d",
									   argAddress.getName(),
									   STATIC_FUNC_FIRST_ARG_OFFSET));
		printed.appendNL(String.format("lw %s, 0(%s)", 
									   str1Address.getName(),
									   argAddress.getName()));
		
		// second argument - str2Address
		printed.appendNL(String.format("addi %s, %s, %d", 
									   argAddress.getName(),
									   argAddress.getName(),
									   SymbolTable.ADDRESS_SIZE));
		printed.appendNL(String.format("lw %s, 0(%s)", 
									   str2Address.getName(),
				   					   argAddress.getName()));
	}
	
	/**
	 * Writes code which calculates the string's length by calling strlen.
	 */
	private void writeStrLengthCalculation(StringNLBuilder printed,
										   CodeGen_Temp strAddress,
										   CodeGen_Temp strLength) throws IOException
	{
		CodeGen_Utils.codeGen_Push(printed, strAddress.getName());
		printed.appendNL(String.format("jal %s", STRLEN_FUNCTION_LABEL));
		printed.appendNL(String.format("addi $sp, $sp, %d", SymbolTable.ADDRESS_SIZE));
		printed.appendNL(String.format("mov %s, $v0", strLength.getName()));
	}
	
	/**
	 * Writes code which allocates memory for the concatenation string.
	 * Returns the temp which holds the new allocated address.
	 */
	private CodeGen_Temp writeAllocationForConcat(StringNLBuilder printed,
										  CodeGen_Temp str1Length,
										  CodeGen_Temp str2Length) throws TooManyTempsException, IOException
	{
		CodeGen_Temp concatLength = TempGenerator.getAndAddNewTemp();
		
		printed.appendNL(String.format("add %s, %s, %s", 
									   concatLength.getName(),
									   str1Length.getName(),
									   str2Length.getName()));
		
		// Saving an extra byte for the null terminator
		printed.appendNL(String.format("addi %s, %s, 1", 
									   concatLength.getName(), 
									   concatLength.getName()));
		
		return CodeGen_Utils.codeGen_malloc(printed, concatLength);
	}
	
	/**
	 * Writes code which copies the src string to the dst pointer
	 * and sets the dst pointer to point after the copied string.
	 */
	private void writeStrCopyAndPointerPromotion(StringNLBuilder printed,
												 CodeGen_Temp dstStrPtr,
												 CodeGen_Temp srcStrAddress,
												 CodeGen_Temp srcStrLength) throws IOException
	{
		// Pushing the arguments from last to first
		CodeGen_Utils.codeGen_Push(printed, srcStrLength.getName());
		CodeGen_Utils.codeGen_Push(printed, srcStrAddress.getName());
		CodeGen_Utils.codeGen_Push(printed, dstStrPtr.getName());
		
		// Calling memcpy and removing the arguments from the stack
		printed.appendNL(String.format("jal %s", MEMCPY_FUNCTION_LABEL));
		printed.appendNL(String.format("addi $sp, $sp, %d", 3 * SymbolTable.ADDRESS_SIZE));

		// Setting the destination pointer to point after the copied string
		printed.appendNL(String.format("add %s, %s, %s", 
									   dstStrPtr.getName(),
									   dstStrPtr.getName(),
									   srcStrLength.getName()));
	}
	
	/**
	 * Writes code which adds a null-terminator in the given address.
	 */
	private void writeNullTerminatorAddition(StringNLBuilder printed,
											 CodeGen_Temp nullTerminatorIndex) throws TooManyTempsException
	{
		CodeGen_Temp nullTerminator = TempGenerator.getAndAddNewTemp();
		
		printed.appendNL(String.format("li %s, 0", nullTerminator.getName()));
		printed.appendNL(String.format("sb %s, (%s)", 
									   nullTerminator.getName(), 
									   nullTerminatorIndex.getName()));	
	}
	
	/**
	 * @brief	Writes the code of the static function strcat.
	 * 			This strcat is similar to the standard memcpy, but instead of
	 * 			appending the second string to the first one - it copies both strings
	 * 			to a new allocated address. The return value is the address of the
	 * 			new concatenation string.
	 *  
	 * @param 	printed - a string builder, to which the memcpy's code is appended.
	 *  
	 * @throws 	TooManyTempsException
	 * @throws 	IOException 
	 */
	private void writeStrcat(StringNLBuilder printed) throws TooManyTempsException, IOException
	{
		CodeGen_Temp str1Address = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp str2Address = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp str1Length = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp str2Length = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp concatStr = null;
		CodeGen_Temp concatPtr = TempGenerator.getAndAddNewTemp();
		
		// Label and prolog
		printed.appendNL(String.format("%s:", STRCAT_FUNCTION_LABEL));
		IR_METHOD.appendProlog(printed, 0); // frame size 0, no local variables
		
		// Calculating lengths
		writeStrcatArgsLoading(printed, str1Address, str2Address);
		writeStrLengthCalculation(printed, str1Address, str1Length);
		writeStrLengthCalculation(printed, str2Address, str2Length);
		
		// Creating the concatenation string
		concatStr = writeAllocationForConcat(printed, str1Length, str2Length);
		printed.appendNL(String.format("mov %s, %s", concatPtr.getName(), concatStr.getName()));
		writeStrCopyAndPointerPromotion(printed, concatPtr, str1Address, str1Length);
		writeStrCopyAndPointerPromotion(printed, concatPtr, str2Address, str2Length);
		writeNullTerminatorAddition(printed, concatPtr);
		
		// Setting return value and epilog
		printed.appendNL(String.format("mov $v0, %s", concatStr.getName())); 
		IR_METHOD.appendEpilog(printed);
	}
	
	/**
	 * @brief	Writes the code of a several static utility functions, such as strlen, memcpy, strcat.
	 * 			The first two should only be used by strcat, while strcat is used in order
	 * 			to concatenating strings (when there's "str1 + str2" in the IC code).
	 * 
	 * @param 	printed
	 * @throws 	TooManyTempsException
	 * @throws 	IOException
	 */
	private void writeStaticUtilityFunctions(StringNLBuilder printed) throws TooManyTempsException, IOException
	{
		writeStrlen(printed);
		writeMemcpy(printed);
		writeStrcat(printed);
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
