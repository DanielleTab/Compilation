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
	
	public void generateNullActionForFields(ClassSymbolInfo classInfo) throws IOException
	{
		StringNLBuilder printed = new StringNLBuilder();
		CodeGen_Temp temp = TempGenerator.getAndAddNewTemp();
		printed.appendNL(String.format("mov %s,$sp", temp.getName()));
		for(int i=0;i<classInfo.fields.size();i++)
		{
			printed.appendNL(String.format("addi %s,%s,%d", temp.getName(), temp.getName(),SymbolTable.ADDRESS_SIZE));
			printed.appendNL(String.format("li %s,0", temp.getName()));
		}
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		ClassSymbolInfo classInfo = SymbolTable.getClassSymbolInfo(newExpClassName);
		StringNLBuilder printed = new StringNLBuilder();
		CodeGen_Temp addressOnHeap = CodeGen_Utils.codeGen_malloc(printed,classInfo.size);
		CodeGen_Temp newTemp = TempGenerator.getAndAddNewTemp();
		printed.appendNL(String.format("la %s, %s", newTemp.getName(),classInfo.getVFTableLabel()));
		printed.appendNL(String.format("sw %s,%s", newTemp.getName(),addressOnHeap.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		generateNullActionForFields(classInfo);
		return addressOnHeap;
	}
}
