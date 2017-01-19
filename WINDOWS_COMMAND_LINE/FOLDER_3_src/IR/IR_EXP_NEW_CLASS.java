package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
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
		StringBuilder printed = new StringBuilder();
		CodeGen_Temp temp = TempGenerator.getAndAddNewTemp();
		printed.append(String.format("mov %s,$sp", temp.getName()));
		printed.append(AssemblyFilePrinter.NEW_LINE_STRING);
		for(int i=0;i<classInfo.fields.size();i++)
		{
			printed.append(String.format("addi %s,%s,%d", temp.getName(), temp.getName(),SymbolTable.ADDRESS_SIZE));
			printed.append(String.format("li %s,0", temp.getName()));
			printed.append(AssemblyFilePrinter.NEW_LINE_STRING);
		}
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		ClassSymbolInfo classInfo = SymbolTable.getClassSymbolInfo(newExpClassName);
		CodeGen_Temp addressOnHeap = CodeGen_Utils.codeGen_malloc(classInfo.size);
		CodeGen_Temp newTemp = TempGenerator.getAndAddNewTemp();
		StringBuilder printed = new StringBuilder();
		printed.append(String.format("la %s, %s", newTemp.getName(),classInfo.getVFTableLabel()));
		printed.append(AssemblyFilePrinter.NEW_LINE_STRING);
		printed.append(String.format("sw %s,%s", newTemp.getName(),addressOnHeap.getName()));
		AssemblyFilePrinter.getInstance(null).write(printed.toString());
		generateNullActionForFields(classInfo);
		return addressOnHeap;
	}
}
