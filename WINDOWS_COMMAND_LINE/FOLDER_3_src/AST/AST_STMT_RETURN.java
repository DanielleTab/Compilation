package AST;

public class AST_STMT_RETURN extends AST_STMT 
{
	public AST_EXP exp;
	
	public AST_STMT_RETURN(AST_EXP exp)
	{
		this.exp = exp;
	}
}
