package CodeGen;

import java.io.IOException;

public class CodeGen_Utils {
	
	/* 
	 * @param	printed - the push instructions will be appended to this string builder. 
	 *  */
	public static void codeGen_Push(StringBuilder printed, String pushedObject) throws IOException
	{
		printed.append(String.format("addi $sp,$sp,-4%s", AssemblyFilePrinter.NEW_LINE_STRING));
		printed.append(String.format("sw %s,0($sp)%s", pushedObject,AssemblyFilePrinter.NEW_LINE_STRING));
	}
	
	/* 
	 * @param	printed - the pop instructions will be appended to this string builder. 
	 *  */
	public static void codeGen_Pop(StringBuilder printed, String pop) throws IOException
	{
		printed.append(String.format("lw %s,0($sp)%s",pop,AssemblyFilePrinter.NEW_LINE_STRING));
		printed.append(String.format("addi $sp,$sp,4%s", AssemblyFilePrinter.NEW_LINE_STRING));
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
		StringBuilder printed = new StringBuilder();
		// $a0 is the argument to the syscall
		// $v0 = 9 is the syscall number for memory allocation on the heap.
		printed.append( "li $v0 9"+AssemblyFilePrinter.NEW_LINE_STRING);
		printed.append("syscall"+AssemblyFilePrinter.NEW_LINE_STRING);
		CodeGen_Temp resultAddress = TempGenerator.getAndAddNewTemp();
		// the address is in v0 after syscall execution.
		printed.append(String.format("mov %s,$v0%s",resultAddress.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		
		return resultAddress;
	}	
}
