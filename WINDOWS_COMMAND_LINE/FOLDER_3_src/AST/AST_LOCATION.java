package AST;

import IR.IR_EXP_MEM;

public abstract class AST_LOCATION extends AST_Node
{
	public abstract IR_EXP_MEM createIR();
}