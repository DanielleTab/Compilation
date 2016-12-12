package AST;

public class AST_EXP_BINOP extends AST_EXP
{
	public AST_BINOP op;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,AST_BINOP op)
	{
		this.left = left;
		this.right = right;
		this.op = op;
	}
}