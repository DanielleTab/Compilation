package IR;

import java.io.IOException;

import AST.AST_TYPE;
import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.SemanticAnalysisException;
import SemanticAnalysis.SymbolTable;
import SemanticAnalysis.TooManyTempsException;

public class IR_EXP_NEW_ARRAY extends IR_EXP{
	public IR_EXP size;
	public IR_EXP_NEW_ARRAY(IR_EXP size)
	{
		this.size=size;
	}
	
	// initiates all the allocated array elements to null / 0. it's the same in the memory.
	// the given array size is the basic size, before add it 1 for the arrayLength element.
	// the given array has already have the arrayLength in it's first element.
		public void generateNullForArrayElements(CodeGen_Temp addressOnHeap,CodeGen_Temp arraySize,StringNLBuilder printed) throws IOException, TooManyTempsException
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
			 * goto Label_num_loop
			 * Label_num_endLoop
			 */
			
			CodeGen_Temp currIndex = TempGenerator.getAndAddNewTemp();
			CodeGen_Temp tempIterator = TempGenerator.getAndAddNewTemp();
			CodeGen_Temp zeroTemp = TempGenerator.getAndAddNewTemp();
			String initArrayLabel = String.format("Label_%d_init_array", AssemblyFilePrinter.addLabelIndex());
			String startLoopLabel = String.format("Label_%d_start_loop", AssemblyFilePrinter.addLabelIndex());
			String endLoopLabel =  String.format("Label_%d_end_loop", AssemblyFilePrinter.addLabelIndex());
			printed.appendNL(initArrayLabel+":");
			printed.appendNL(String.format("li %s,0", zeroTemp.getName()));
			printed.appendNL(String.format("li %s,1", currIndex.getName()));
			printed.appendNL(String.format("mov %s,%s", tempIterator.getName(),addressOnHeap.getName()));
			printed.appendNL(String.format("addi %s,%s,%d", tempIterator.getName(),tempIterator.getName(),SymbolTable.ADDRESS_SIZE));
			printed.appendNL(startLoopLabel+":");
			printed.appendNL(String.format("bgt %s,%s,%s",currIndex.getName(),arraySize.getName(),endLoopLabel));
			printed.appendNL(String.format("sw %s,0(%s)", zeroTemp.getName(),tempIterator.getName()));
			printed.appendNL(String.format("addi %s,%s,%d", tempIterator.getName(),tempIterator.getName(),SymbolTable.ADDRESS_SIZE));
			printed.appendNL(String.format("addi %s,%s,1", currIndex.getName(), currIndex.getName()));
			printed.appendNL(String.format("j %s", startLoopLabel));
			printed.appendNL(endLoopLabel+":");
			
		}
		
	@Override
	public CodeGen_Temp generateCode() throws IOException, SemanticAnalysisException
	{
		CodeGen_Temp generatedNumOfElements = this.size.generateCode();
		CodeGen_Temp generatedNumOfElementsPlusOne = TempGenerator.getAndAddNewTemp();
		StringNLBuilder printed = new StringNLBuilder();
		CodeGen_Temp fourTemp = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp oneTemp = TempGenerator.getAndAddNewTemp();
		printed.appendNL(String.format("li %s,1",oneTemp.getName()));
		
		// Add test of array size >=1. if <1 then throw an error. 
		printed.appendNL(String.format("blt %s,%s,%s", generatedNumOfElements.getName(),oneTemp.getName(),IR_Node.ERROR_LABEL_NAME));
		
		printed.appendNL(String.format("li %s,4",fourTemp.getName()));
		// add one to the generatedSize because we want to allocate (size+1) cells on the heap -
		// the first cell for the size.
		printed.appendNL(String.format("addi %s,%s,1", generatedNumOfElementsPlusOne.getName(), generatedNumOfElements.getName()));
		CodeGen_Temp generatedSize = TempGenerator.getAndAddNewTemp();
		printed.appendNL(String.format("mul %s,%s,%s", generatedSize.getName(),generatedNumOfElementsPlusOne.getName(),fourTemp.getName()));
		CodeGen_Temp heapAddress = CodeGen_Utils.codeGen_malloc(printed,generatedSize);
		// put the first element in the array to be the array size.
		printed.appendNL(String.format("sw %s,0(%s)",generatedNumOfElements.getName(),heapAddress.getName()));
		generateNullForArrayElements(heapAddress,generatedNumOfElements,printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		return heapAddress;
	}
}
