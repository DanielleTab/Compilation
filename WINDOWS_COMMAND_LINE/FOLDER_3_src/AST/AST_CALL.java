package AST;

public class AST_CALL extends AST_Node 
{
	public AST_EXP exp; // might be null
	public String funcName;
	public AST_EXPS_LIST args; // might be null
	
	public AST_CALL(AST_EXP exp, String funcName, AST_EXPS_LIST args)
	{
		this.exp = exp;
		this.funcName = funcName;
		this.args = args;
	}
	
}
