package IR;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;

public class IR_STMT_RETURN extends IR_STMT{
	public IR_EXP returnedExpression;
	public CodeGen_Temp returnTempRegister; // might be null

	public IR_STMT_RETURN(IR_EXP returnedExpression)
	{
		this.returnedExpression=returnedExpression;
	}
	
	public void generateCode()
	{
		CodeGen_Temp returnValue = returnTempRegister.generateCode();
		AssemblyFilePrinter.getInstance(null).write(String.format("mov $v0,%s%s",returnValue.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
	}
}
