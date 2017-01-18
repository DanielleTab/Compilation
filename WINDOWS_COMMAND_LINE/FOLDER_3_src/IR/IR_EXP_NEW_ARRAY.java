package IR;

import java.io.IOException;

import AST.AST_TYPE;
import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.TempGenerator;

public class IR_EXP_NEW_ARRAY extends IR_EXP{
	public IR_EXP size;
	public IR_EXP_NEW_ARRAY(IR_EXP size)
	{
		this.size=size;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp generatedSize = this.size.generateCode();
		CodeGen_Temp heapAddress = CodeGen_Utils.codeGen_malloc(generatedSize);
		StringBuilder printed = new StringBuilder();
		// put the first element in the array to be the array size.
		printed.append(String.format("sw %s,%s%s",generatedSize.getName(),heapAddress.getName(), AssemblyFilePrinter.NEW_LINE_STRING));
		return heapAddress;
	}
}
