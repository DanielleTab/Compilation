package IR;

import AST.AST_TYPE;

public class IR_EXP_NEW_ARRAY extends IR_EXP{
	public AST_TYPE type;
	public int size;
	public IR_EXP_NEW_ARRAY(AST_TYPE type, int size)
	{
		this.size=size;
		this.type=type;
	}
}
