package AST;

public class AST_STMT_WHILE extends AST_STMT_COND
{

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond, AST_STMT body)
	{
		super(cond, body);
	}
	
}