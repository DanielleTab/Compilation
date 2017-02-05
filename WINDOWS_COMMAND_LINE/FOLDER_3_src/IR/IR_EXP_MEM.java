package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.TempGenerator;

public class IR_EXP_MEM extends IR_EXP 
{
	// fields
	public IR_EXP address;
	
	// C'tor
	public IR_EXP_MEM(IR_EXP address)
	{
		this.address = address;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		CodeGen_Temp result = TempGenerator.getAndAddNewTemp();
		CodeGen_Temp t1= this.address.generateCode();
		AssemblyFilePrinter.getInstance(null).write(String.format("lw %s,0(%s)%s",result.getName(),t1.getName(),AssemblyFilePrinter.NEW_LINE_STRING));
		return result;
	}
}
