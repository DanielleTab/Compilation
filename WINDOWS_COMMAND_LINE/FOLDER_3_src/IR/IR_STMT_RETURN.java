package IR;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;

public class IR_STMT_RETURN extends IR_STMT
{
	public IR_EXP returnedExpression; // might be null
	public String methodEpilogLabelName;
	public CodeGen_Temp returnTempRegister; // might be null
	

	public IR_STMT_RETURN(IR_EXP returnedExpression, String methodEpilogLabelName)
	{
		this.returnedExpression = returnedExpression;
		this.methodEpilogLabelName = methodEpilogLabelName;
	}
	
	// TODO: Maybe use the commented code?
	public void generateCode()
	{
		/*
		if (returnedExpression != null)
		{
			CodeGen_Temp returnValue = returnedExpression.generateCode();
			AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		}
		AssemblyFilePrinter.getInstance(null).write(String.format("j %s%s", methodEpilogLabelName, AssemblyFilePrinter.NEW_LINE_STRING));
		*/
		
		CodeGen_Temp returnValue = returnTempRegister.generateCode();
		AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
	}
}
