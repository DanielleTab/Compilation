package IR;

import java.io.IOException;

import AST.AST_TYPE;
import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
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
		CodeGen_Temp generatedSizePlusOne = TempGenerator.getAndAddNewTemp();
		StringNLBuilder printed = new StringNLBuilder();
		
		// add one to the generatedSize because we want to allocate (size+1) cells on the heap -
		// the first cell for the size.
		printed.appendNL(String.format("addi %s,%s,1", generatedSizePlusOne, generatedSize));
		CodeGen_Temp heapAddress = CodeGen_Utils.codeGen_malloc(printed,generatedSizePlusOne);
		// put the first element in the array to be the array size.
		printed.appendNL(String.format("sw %s,0(%s)",generatedSize.getName(),heapAddress.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return heapAddress;
	}
}
