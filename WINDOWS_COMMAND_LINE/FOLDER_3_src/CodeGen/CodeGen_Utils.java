package CodeGen;

import java.io.IOException;

public class CodeGen_Utils {
	
	/* 
	 * @param	printed - the push instructions will be appended to this string builder. 
	 *  */
	public static void codeGen_Push(StringNLBuilder printed, String pushedObject) throws IOException
	{
		printed.appendNL("addi $sp,$sp,-4%s");
		printed.appendNL(String.format("sw %s,0($sp)", pushedObject));
	}
	
	/* 
	 * @param	printed - the pop instructions will be appended to this string builder. 
	 *  */
	public static void codeGen_Pop(StringNLBuilder printed, String pop) throws IOException
	{
		printed.appendNL(String.format("lw %s,0($sp)",pop));
		printed.appendNL("addi $sp,$sp,4");
	}
	
	public static CodeGen_Temp codeGen_malloc(int allocationSize) throws IOException
	{
		AssemblyFilePrinter.getInstance(null).write(String.format("li $a0 %d%s",allocationSize, AssemblyFilePrinter.NEW_LINE_STRING));
		return common_codeGen_malloc();
	}
	
	public static CodeGen_Temp codeGen_malloc(CodeGen_Temp allocationSize) throws IOException
	{
		AssemblyFilePrinter.getInstance(null).write(String.format("mov $a0 %s%s",allocationSize.getName(), AssemblyFilePrinter.NEW_LINE_STRING));
		return common_codeGen_malloc();

	}
	
	// The allocated space address is in $v0
	private static CodeGen_Temp common_codeGen_malloc() throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();
		// $a0 is the argument to the syscall
		// $v0 = 9 is the syscall number for memory allocation on the heap.
		printed.appendNL( "li $v0 9");
		printed.appendNL("syscall");
		CodeGen_Temp resultAddress = TempGenerator.getAndAddNewTemp();
		// the address is in v0 after syscall execution.
		printed.appendNL(String.format("mov %s,$v0",resultAddress.getName()));
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		return resultAddress;
	}	
}
