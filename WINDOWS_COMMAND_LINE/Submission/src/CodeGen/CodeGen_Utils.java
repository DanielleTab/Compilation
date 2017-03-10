package CodeGen;

import java.io.IOException;

import SemanticAnalysis.TooManyTempsException;

public class CodeGen_Utils {
	

	
	/* 
	 * @param	printed - the push instructions will be appended to this string builder. 
	 *  */
	public static void codeGen_Push(StringNLBuilder printed, String pushedObject) throws IOException
	{
		printed.appendNL("addi $sp,$sp,-4");
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
	
	/* 
	 * @param	printed - the malloc instructions will be appended to this string builder. 
	 *  */
	public static CodeGen_Temp codeGen_malloc(StringNLBuilder printed,int allocationSize) throws IOException, TooManyTempsException
	{
		printed.appendNL(String.format("li $a0,%d",allocationSize));
		return common_codeGen_malloc(printed);
	}
	
	public static CodeGen_Temp codeGen_malloc(StringNLBuilder printed,CodeGen_Temp allocationSize) throws IOException, TooManyTempsException
	{
		printed.appendNL(String.format("mov $a0,%s",allocationSize.getName()));
		return common_codeGen_malloc(printed);

	}
	
	// The allocated space address is in $v0
	private static CodeGen_Temp common_codeGen_malloc(StringNLBuilder printed) throws IOException, TooManyTempsException
	{
		// $a0 is the argument to the syscall
		// $v0 = 9 is the syscall number for memory allocation on the heap.
		printed.appendNL( "li $v0,9");
		printed.appendNL("syscall");
		CodeGen_Temp resultAddress = TempGenerator.getAndAddNewTemp();
		// the address is in v0 after syscall execution.
		printed.appendNL(String.format("mov %s,$v0",resultAddress.getName()));		
		return resultAddress;
	}	
}
