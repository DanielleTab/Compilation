package IR;

import java.io.IOException;

import CodeGen.AssemblyFilePrinter;
import CodeGen.CodeGen_Temp;
import CodeGen.StringNLBuilder;
import CodeGen.TempGenerator;

public class IR_EXP_CALL extends IR_EXP{
	public IR_CALL call;
	public IR_EXP_CALL(IR_CALL call)
	{
		this.call=call;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		this.call.generateCode();
		// TODO: Move the result in v0 to a new temp and return the temp. 
		return null;
	}
}
