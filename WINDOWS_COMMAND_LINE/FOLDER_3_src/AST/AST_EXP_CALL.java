package AST;

public class AST_EXP_CALL extends AST_EXP 
{
	public AST_CALL call;
	
	public AST_EXP_CALL(AST_CALL call)
	{
		this.call = call;
	}
}
