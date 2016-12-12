package AST;

public class AST_STMT_VAR_DECL extends AST_STMT 
{
	public AST_TYPE varType;
	public String varName;
	public AST_EXP exp;
	
	public AST_STMT_VAR_DECL(AST_TYPE varType, String varName)
	{
		this.varType = varType;
		this.varName = varName;
		this.exp = null;
	}
	
	public AST_STMT_VAR_DECL(AST_TYPE varType, String varName, AST_EXP exp)
	{
		this.varType = varType;
		this.varName = varName;
		this.exp = exp;
	}
}
