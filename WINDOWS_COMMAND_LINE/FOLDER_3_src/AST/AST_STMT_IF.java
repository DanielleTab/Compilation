package AST;

import IR.IR_STMT_IF;

public class AST_STMT_IF extends AST_STMT_COND
{

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond, AST_STMT body)
	{
		super(cond, body);
	}
	
	// TODO: implement
	public IR_STMT_IF createIR()
	{
		return null;
	}
	
}