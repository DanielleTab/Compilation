package IR;

import java.io.IOException;

import CodeGen.CodeGen_Temp;

public abstract class IR_EXP extends IR_Node
{
	 public abstract CodeGen_Temp generateCode() throws IOException;
}
