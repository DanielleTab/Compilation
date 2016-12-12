package AST;

public class AST_STMT_ASSIGN extends AST_STMT
{
	public AST_EXP exp;
	public AST_LOCATION var;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_LOCATION var,AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}
}