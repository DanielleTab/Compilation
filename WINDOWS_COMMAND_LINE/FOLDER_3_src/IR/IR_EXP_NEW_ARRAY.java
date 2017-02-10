package IR;

import java.io.IOException;

import AST.AST_TYPE;
import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.SymbolTable;

public class IR_EXP_NEW_ARRAY extends IR_EXP{
	public IR_EXP size;
	public IR_EXP_NEW_ARRAY(IR_EXP size)
	{
		this.size=size;
	}
	
	// initiates all the allocated array elements to null / 0. it's the same in the memory.
	// the given array size is the basic size, before add it 1 for the arrayLength element.
	// the given array has already have the arrayLength in it's first element.
		public void generateNullForArrayElements(CodeGen_Temp addressOnHeap,CodeGen_Temp arraySize,StringNLBuilder printed) throws IOException
		{
			// TODO: what if the array length = 0?
			// TODO: build loop.
			
			/*
			 * Label_num_init_array:
			 * li tempIndex,1
			 * mov tempIterator,addressOnHeap
			 * addi tempIterator,tempIterator,1
			 * Label_num_loop:
			 * beq tempIndex,arraySize,Label_num_endLoop
			 * sw zeroTemp,tempIterator
			 * addi tempIterator,tempIterator,1
			 * addi tempIndex,tempIndex,1
			 * Label_num_endLoop
			 */
			/*
			StringNLBuilder printed = new StringNLBuilder();
			CodeGen_Temp temp = TempGenerator.getAndAddNewTemp();
			CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
			printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
			printed.appendNL(String.format("mov %s,%s", temp.getName(), addressOnHeap.getName()));
			for(int i=0;i<classInfo.fields.size();i++)
			{
				printed.appendNL(String.format("addi %s,%s,%d", temp.getName(), temp.getName(),SymbolTable.ADDRESS_SIZE));
				printed.appendNL(String.format("sw %s,0(%s)", zeroTemp.getName(),temp.getName()));
			}
			AssemblyFilePrinter.getInstance(null).write(printed.toString());*/
		}
		
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp generatedSize = this.size.generateCode();
		CodeGen_Temp generatedSizePlusOne = TempGenerator.getAndAddNewTemp();
		StringNLBuilder printed = new StringNLBuilder();
		
		// add one to the generatedSize because we want to allocate (size+1) cells on the heap -
		// the first cell for the size.
		printed.appendNL(String.format("addi %s,%s,1", generatedSizePlusOne.getName(), generatedSize.getName()));
		CodeGen_Temp heapAddress = CodeGen_Utils.codeGen_malloc(printed,generatedSizePlusOne);
		// put the first element in the array to be the array size.
		printed.appendNL(String.format("sw %s,0(%s)",generatedSize.getName(),heapAddress.getName()));
		generateNullForArrayElements(heapAddress,generatedSize,printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return heapAddress;
	}
}
