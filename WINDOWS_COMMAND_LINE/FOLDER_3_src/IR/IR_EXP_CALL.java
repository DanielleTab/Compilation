package IR;

import java.io.IOException;

import CodeGen.CodeGen_Temp;

public class IR_EXP_CALL extends IR_EXP{
	public IR_CALL call;
	public IR_EXP_CALL(IR_CALL call)
	{
		this.call=call;
	}
	
	public CodeGen_Temp generateCode() throws IOException
	{
		this.call.generateCode();
		// TODO: decide what we should return in this generateCode.
		return null;
	}
}
