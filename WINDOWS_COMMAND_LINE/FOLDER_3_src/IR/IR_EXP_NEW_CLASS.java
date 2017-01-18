package IR;

import java.io.IOException;

import CodeGen.CodeGen_Temp;
import CodeGen.CodeGen_Utils;
import SemanticAnalysis.ClassSymbolInfo;
import SemanticAnalysis.SymbolTable;

public class IR_EXP_NEW_CLASS extends IR_EXP{
	public String newExpClassName;
	
	public IR_EXP_NEW_CLASS(String newExpClassName)
	{
		this.newExpClassName=newExpClassName;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		ClassSymbolInfo classInfo = SymbolTable.getClassSymbolInfo(newExpClassName);
		CodeGen_Temp addressOnHeap = CodeGen_Utils.codeGen_malloc(classInfo.size);
		StringBuilder printed = new StringBuilder();
		// TODO: sw of the vft address in the first cell.
		// TODO: null in each field.
		return addressOnHeap;
	}
}
