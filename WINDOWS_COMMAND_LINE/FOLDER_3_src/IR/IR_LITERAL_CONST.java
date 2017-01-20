package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.TempGenerator;

public class IR_LITERAL_CONST extends IR_LITERAL{
	int constInteger;
	public IR_LITERAL_CONST(int i)
	{
		this.constInteger=i;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp register = TempGenerator.getAndAddNewTemp();
		AssemblyFilePrinter.getInstance(null).append(String.format("li %s,%d%s", 
				register.getName(),constInteger,AssemblyFilePrinter.NEW_LINE_STRING));
		return register;
		
	}

}
