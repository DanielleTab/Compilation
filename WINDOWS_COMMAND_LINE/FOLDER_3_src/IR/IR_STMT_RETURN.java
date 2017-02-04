package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;

public class IR_STMT_RETURN extends IR_STMT
{
	public IR_EXP returnedExpression; // might be null
	public String methodEpilogLabelName;
	
	// TODO: If this is unnecessary, delete it.
	// public CodeGen_Temp returnTempRegister; // might be null
	

	public IR_STMT_RETURN(IR_EXP returnedExpression, String methodEpilogLabelName)
	{
		this.returnedExpression = returnedExpression;
		this.methodEpilogLabelName = methodEpilogLabelName;
	}
	
	// 
	public void generateCode() throws IOException
	{
		if (returnedExpression != null)
		{
			CodeGen_Temp returnValue = returnedExpression.generateCode();
			AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		}
		AssemblyFilePrinter.getInstance(null).write(String.format("j %s%s", methodEpilogLabelName, AssemblyFilePrinter.NEW_LINE_STRING));
		
		// TODO: If this is unnecessary, delete it.
		// The original code (with returnTempRegister), no check of null
		/*
		CodeGen_Temp returnValue = returnTempRegister.generateCode();
		AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		*/
	}
}
