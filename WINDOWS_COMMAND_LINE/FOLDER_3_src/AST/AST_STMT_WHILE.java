package AST;

import IR.IR_STMT_WHILE;

public class AST_STMT_WHILE extends AST_STMT_COND
{

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond, AST_STMT body)
	{
		super(cond, body);
	}
	
	//  TODO: implement this.
	public IR_STMT_WHILE createIR()
	{
		return null;
	}
	
}