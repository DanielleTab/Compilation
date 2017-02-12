package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.SymbolTable;

public class IR_EXP_NEW_CLASS extends IR_EXP{
	public String newExpClassName;
	
	public IR_EXP_NEW_CLASS(String newExpClassName)
	{
		this.newExpClassName=newExpClassName;
	}
	
	// initiates all fields to null.
	public void generateNullActionForFields(ClassSymbolInfo classInfo,CodeGen_Temp addressOnHeap) throws IOException
	{
		if(classInfo.fields==null)
		{
			return;
		}
		
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
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	/**
	 * @brief	Generates code for storing the VFTable address at the beginning of the object.
	 * 			If the object's class doesn't have a VFTable (if it doesn't have any methods),
	 * 			generates code for storing null instead.	
	 */
	private void generateCodeForStoringVFTableAddress(ClassSymbolInfo classInfo, 
													  CodeGen_Temp addressOnHeap, 
													  StringNLBuilder printed)
	{
		CodeGen_Temp newTemp = TempGenerator.getAndAddNewTemp();
		
		if (classInfo.virtualFunctionsOrder.size() > 0)
		{
			// Using the VFTable address
			printed.appendNL(String.format("la %s, %s", newTemp.getName(),classInfo.getVFTableLabel()));	
		}
		else
		{
			// There isn't a VFTable, therefore using null
			printed.appendNL(String.format("li %s, 0", newTemp.getName()));
		}
		
		printed.appendNL(String.format("sw %s,0(%s)", newTemp.getName(),addressOnHeap.getName()));
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		ClassSymbolInfo classInfo = SymbolTable.getClassSymbolInfo(newExpClassName);
		StringNLBuilder printed = new StringNLBuilder();
		CodeGen_Temp addressOnHeap = CodeGen_Utils.codeGen_malloc(printed,classInfo.size);
		generateCodeForStoringVFTableAddress(classInfo, addressOnHeap, printed);
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		generateNullActionForFields(classInfo,addressOnHeap);
		return addressOnHeap;
	}
}
