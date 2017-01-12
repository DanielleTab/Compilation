package IR;

import AST.AST_TYPE;

public class IR_EXP_NEW_ARRAY extends IR_EXP{
	public IR_EXP size;
	public IR_EXP_NEW_ARRAY( IR_EXP size)
	{
		this.size=size;
	}
}
