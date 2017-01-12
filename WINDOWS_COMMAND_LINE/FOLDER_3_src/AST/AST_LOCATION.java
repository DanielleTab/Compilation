package AST;

import IR.IR_EXP_BINOP;

public abstract class AST_LOCATION extends AST_Node
{
	public abstract IR_EXP_BINOP createIR();
}